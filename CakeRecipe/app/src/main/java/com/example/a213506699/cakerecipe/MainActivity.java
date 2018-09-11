package com.example.a213506699.cakerecipe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnAddRecipe,btnRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddRecipe = findViewById(R.id.btnAddRecipe);
        btnRecipeList = findViewById(R.id.btnRecipeList);
        /*
        Calling the Add Recipe activity
        */
        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddRecipe.class);
                startActivity(intent);
            }
        });
        /*
        Calling the Recipe List activity
        */
        btnRecipeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecipeList.class);
                startActivity(intent);
            }
        });
    /*
    Setting the background color of the current activity
    */
        View someView = findViewById(R.id.mainRelative);
        // Find the root view
        View root = someView.getRootView();
        // Set the color
        root.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }

}
