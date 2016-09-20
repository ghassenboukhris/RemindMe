package com.example.ghassen.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
       Button login = (Button) findViewById(R.id.login);



       EditText password = (EditText) findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String mUsername ="g";
                String mPassword = "f";


                Intent email = new Intent(AllActivity.this,WelcomeActivity.class);
                startActivity(email);
            }
        });
    }
    protected void tryLogin(String mUsername, String mPassword)
    {
        HttpURLConnection connection;
        OutputStreamWriter request = null;

        URL url = null;
        String response = null;
        String parameters = "var1="+mUsername+"&var2="+mPassword;

        try
        {
            url = new URL("http://app.swclients.ch/ghassen/test.php");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");

            request = new OutputStreamWriter(connection.getOutputStream());
            request.write(parameters);
            request.flush();
            request.close();
            String line = "";
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();


            isr.close();
            reader.close();

        }
        catch(IOException e)
        {
            // Error
        }
    }
}
