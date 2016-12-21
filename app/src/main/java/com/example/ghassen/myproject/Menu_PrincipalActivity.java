package com.example.ghassen.myproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Menu_PrincipalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__principal);
    }
    /** Called when the user clicks the Send button */
    public void sendBusiness(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, BusinessActivity.class);
        startActivity(intent);
    }
    public void sendAchats(View view) {
        Intent intent1 = new Intent(this, AchatActivity.class);
        startActivity(intent1);
    }
    public void sendFamille(View view) {
        Intent intent2 = new Intent(this, FamilleActivity.class);
        startActivity(intent2);
    }
}

