package com.example.ghassen.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ReclamationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button TakePicture=(Button) findViewById (R.id.TakePictureButton);
        Button GetPosition=(Button) findViewById(R.id.GetThePositionButton);
        Button Send=(Button) findViewById (R.id.SendButton);
        TakePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent email = new Intent(ReclamationActivity.this, CameraActivity.class);
                startActivity(email);
            }
        });
        GetPosition.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent email = new Intent(ReclamationActivity.this, GPSActivity.class);
                startActivity(email);
            }
        });
    }

}
