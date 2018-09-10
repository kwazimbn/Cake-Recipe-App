package com.example.a213506699.cakerecipe;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;

public class AddRecipe extends AppCompatActivity 
{

    //Declaration of the GUI controls
    ConnectionClass conClass;
    EditText txtHeaderTitle, txtRec;
    Button btnSaveRec;
    ProgressBar pbbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);


        setContentView(R.layout.activity_add_recipe);

        // Now get a handle to any View contained
        // within the main layout you are using
        View someView = findViewById(R.id.relativeLayout);
        // Find the root view
        View root = someView.getRootView();
        // Set the color
        root.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));



        //Initialization of the GUI controls
        conClass = new  ConnectionClass();
        txtHeaderTitle = (EditText) findViewById(R.id.txtCakeName);
        txtRec = (EditText) findViewById(R.id.txtRecipe);
        btnSaveRec = (Button) findViewById(R.id.btnAddRecipe);
        pbbar = (ProgressBar) findViewById(R.id.progressBar);
        pbbar.setVisibility(View.GONE);

        //Save button with listener event.
        btnSaveRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Writting Cake information into Database
                WriteToDB rw = new WriteToDB();
                rw.execute("");
            }
        });
    }

    //Class to
    public class WriteToDB extends AsyncTask<String,String,String>
    {
        String message="";
        Boolean isSuccess=false;

        String cakeName = txtHeaderTitle.getText().toString();
        String cakeRecipe =txtRec.getText().toString();

        //
        protected  void onPreExecute(){pbbar.setVisibility((View.VISIBLE));}

        //
        protected void onPostExecute(String r)
        {
            //Hiding progress Bar after execution completed in the background
            pbbar.setVisibility(View.GONE);
            //Pop message display unit for Toast Method
            Toast.makeText(AddRecipe.this,r,Toast.LENGTH_LONG).show();
            txtHeaderTitle.setText("");
            txtRec.setText("");
            txtHeaderTitle.setFocusable(true);
        }

        @Override
        protected String doInBackground(String... strings)
        {
            if (cakeName.trim().equals("") || cakeRecipe.trim().equals(""))
                message = "Please enter Cake Name and Its Recipe";
            else
            {
                try
                {
                    //Connecting to DB
                    Connection conn = conClass.CONN();
                    CallableStatement callStatement = null;

                    if (conn== null)
                    {
                        message = "Error in connection!";
                    }
                    else
                    {
                        //The Stored Procedure to Save into DB
                        String insertSPQuery = "{call insert_Pre_Cake(?,?)}";
                        callStatement = conn.prepareCall(insertSPQuery);   //For more info on Prepared statement https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html
                        callStatement.setString(1,cakeName.trim());
                        callStatement.setString(2,cakeRecipe.trim());
                        callStatement.executeUpdate();
                        isSuccess= true;
                        message = "Successfully Saved";
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    Log.e("Error Found",ex.getMessage());
                    message = "Exception";
                }
            }
            return message;
        }
    }

}
