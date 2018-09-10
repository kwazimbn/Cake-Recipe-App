package com.example.a213506699.cakerecipe;

import android.annotation.SuppressLint;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnectionClass
{
    //Declaration of connection parameters
    String ip = "xxx.xxx.xxx.xx";
    String db = "xxxxxx";
    String un = "xxxxxx";
    String password = "xxxxxxxxxxxx";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try
        {
            //Instantiating JDBC Drivers
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + ip + "/" +";db="  +db+ ";user=" + un + ";password=" + password + ";"; //Connection String

            conn = DriverManager.getConnection(ConnURL);

        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage()); //Log for debugging
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage()); //Log for debugging
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage()); //Log for debugging
        }
        return conn;
    }
}



