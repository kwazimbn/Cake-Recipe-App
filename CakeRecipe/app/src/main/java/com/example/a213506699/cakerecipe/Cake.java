package com.example.a213506699.cakerecipe;

import java.util.ArrayList;

public class Cake {
    public void setCakeID(int cakeID) {
        this.cakeID = cakeID;
    }

    private int cakeID;
    private String title;
    private String[] recipe;

    public int getCakeID() {
        return cakeID;
    }

    public Cake(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String[] getRecipe() {
        return recipe;
    }

    //Delimiting the string with commas
    public void setRecipe(String s)
    {
        recipe = s.split(",");
    }

    //Formatting string on the arrayList, single line break
    public String recipeToString() {
         String s ="";
         for(int i=0; i<recipe.length;i++)
         {
             s+=recipe[i].trim()+"\n";
         }
         return  s;
    }
}
