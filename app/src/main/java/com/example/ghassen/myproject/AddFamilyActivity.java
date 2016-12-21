package com.example.ghassen.myproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddFamilyActivity extends AppCompatActivity {
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    EditText nameTxt, descriptionTxt, timeTxt;
    EditText place;
    List<Family> families = new ArrayList<>();
    ListView FamilyListView;
    DatabaseHandler2 dbHandler;
    ArrayAdapter<Business> FamilyAdapter;
    private Button btn_date;
    private Button btn_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family);
     Button SetPlaceFamilyButton=(Button) findViewById(R.id.SetPlaceFamilyButton);
        nameTxt = (EditText) findViewById(R.id.TxtName);
        EditText PlaceFamily=(EditText)findViewById(R.id.PlaceFamily);
        timeTxt = (EditText) findViewById(R.id.TxtTime);
        place = (EditText) findViewById(R.id.Place);
        FamilyListView = (ListView) findViewById(R.id.list);

        btn_date = (Button) findViewById(R.id.setDate);
        btn_time = (Button) findViewById(R.id.setTime);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });
        updateTextLabel();
        dbHandler = new DatabaseHandler2(getApplicationContext());

        final Button addBtn = (Button) findViewById(R.id.addEvent);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Family family = new Family(dbHandler.getFamilyCount(), String.valueOf(nameTxt.getText()), String.valueOf(timeTxt.getText()), String.valueOf(place.getText()));
                if (!familyExists(family)) {
                    dbHandler.createFamily(family);
                    families.add(family);

                    Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + " has been added", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),nameTxt.getText().toString()+" is already exists",Toast.LENGTH_SHORT).show();
            }
        });
        Button buttonList = (Button) findViewById(R.id.listEvent);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddFamilyActivity.this, FamilleActivity.class);
                startActivity(intent1);
            }
        });
        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!nameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        List<Family> addableFamily = dbHandler.getAllFamily();
        int familyCount = dbHandler.getFamilyCount();
        for (int i = 0; i < familyCount; i++) {
            families.add(addableFamily.get(i));
        }
        SetPlaceFamilyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapActivity = new Intent(AddFamilyActivity.this,MapFamilyActivity.class);
                startActivity(toMapActivity);
            }
        });
        String adress = getIntent().getStringExtra("Adress");
        PlaceFamily.setText(adress);

    }

    private void  updateDate(){
        new DatePickerDialog(this,d,dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void updateTime(){
        new TimePickerDialog(this,t,dateTime.get(Calendar.HOUR_OF_DAY),dateTime.get(Calendar.MINUTE),true).show();
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            updateTextLabel();
        }
    };

    private void updateTextLabel(){

        timeTxt.setText(formatDateTime.format(dateTime.getTime()));
    }

    private boolean familyExists(Family family){

        String name = family.getName();
        int familyCount= families.size();
        for (int i=0;i<familyCount;i++){
            if(name.compareToIgnoreCase(families.get(i).getName())==0)
                return true;
        }
        return false;
    }
}
