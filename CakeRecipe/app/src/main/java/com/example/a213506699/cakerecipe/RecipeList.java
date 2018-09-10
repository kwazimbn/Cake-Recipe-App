package com.example.a213506699.cakerecipe;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecipeList extends AppCompatActivity {
    ConnectionClass connClass;// =new ConnectionClass();
    Spinner sp;// = findViewById(R.id.recipeSpinner);
    TextView txtViewer;//= (TextView) findViewById(R.id.txtRecipeList);
    //Button btnView;//= (Button) findViewById(R.id.btnRecipeList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        connClass = new ConnectionClass();
        sp = findViewById(R.id.recipeSpinner);
        txtViewer = (TextView) findViewById(R.id.txtRecipeList);
        txtViewer.setMovementMethod(new ScrollingMovementMethod());
        txtViewer.setTextAppearance(RecipeList.this, android.R.style.TextAppearance_Medium);
        View someView = findViewById(R.id.lytRecipeList);
        // Find the root view
        View root = someView.getRootView();
        // Set the color
        root.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        //Instantiating class with processing done on the Background
        GetRecipe gRec = new GetRecipe();
        gRec.execute("");   //
    }

    public class GetRecipe extends AsyncTask<String, String, String> {

        //Declaration of Variables
        String message;
        TextView txt;
        ArrayList<Cake> cakeList;
        List<String> titles;// = new ArrayList<>();

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            //Loading cake names data into array to be populated in a spinner
            txt = findViewById(R.id.txtRecipeList);
            titles = new ArrayList<>();
            for (int i = 0; i < cakeList.size(); i++) {
                titles.add(cakeList.get(i).getTitle());
            }

            //Feeding an arrayList data into a Spinner (DropdownList)
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(RecipeList.this, android.R.layout.simple_list_item_1, titles);
            sp.setAdapter(adapter);
            try
             {
                sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 0; i < cakeList.size(); i++)
                        {
                            //Comparing the list of cake names with what is selected on the Spinner (DropdownList)
                            if (cakeList.get(i).getTitle() == sp.getSelectedItem().toString())
                            {
                                //Populating the recipe textView with corresponding cake name selected on the Spinner (DropdownList)
                                txt.setText(cakeList.get(i).recipeToString());
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
            }
            //Displaying cake recipe into TextViewer
            txtViewer.setText(cakeList.get(0).recipeToString());
            Toast.makeText(RecipeList.this, s, Toast.LENGTH_LONG);
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                Connection conn = connClass.CONN();
                if (conn == null)
                {
                    message = "Error in Connection with SQL Server";
                }
                else
                    {
                        //Reading cakes from Database using Stored Procedure
                        PreparedStatement getProc = conn.prepareCall("EXEC GetCakes");
                        ResultSet rs = getProc.executeQuery();
                        cakeList = new ArrayList<>();
                        while (rs.next())
                        {
                            Cake c = new Cake(rs.getString(2));
                            c.setRecipe(rs.getString(3));
                            c.setCakeID(Integer.parseInt(rs.getString(1)));
                            message = "This was found\n" + rs.getString(3);
                            cakeList.add(c);
                        }


                    /*
                        //Reading cakes from Database using SQL Command on the Client device
                        String query = "Select cr.f_id,cr.title, cr.recipe from Pre_Cake_Recipe cr";
                        Statement sm = conn.createStatement();
                        ResultSet rs = sm.executeQuery(query);
                        ck = new ArrayList<>();
                        while (rs.next())
                    {
                        Cake c = new Cake(rs.getString(2));
                        c.setRecipe(rs.getString(3));
                        c.setCakeID(Integer.parseInt(rs.getString(1)));
                        message = "This was found\n" + rs.getString(3);
                        ck.add(c);
                    }
                    */
                }

            } catch (Exception ex) {
                Log.e("New Error", ex.getMessage());
            }
            return message;
        }
    }
}
