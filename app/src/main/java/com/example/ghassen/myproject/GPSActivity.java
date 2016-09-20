package com.example.ghassen.myproject;

import android.content.Intent;
import android.location.Location;

import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.location.LocationListener;




public class GPSActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        // ...




        Button Getposition=(Button) findViewById(R.id.GetPositonButton);
         final TextView lattitude=(TextView) findViewById(R.id.lattitude);

    }





}