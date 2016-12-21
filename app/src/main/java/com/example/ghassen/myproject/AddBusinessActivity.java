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

public class AddBusinessActivity extends AppCompatActivity {
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    EditText nameTxt, descriptionTxt, timeTxt;
    EditText place;
    List<Business> businesses = new ArrayList<>();
    ListView businessListView;
    DatabaseHandler dbHandler;
    ArrayAdapter<Business> BusinessAdapter;
    private Button btn_date;
    private Button btn_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        Button SetPlaceButton=(Button) findViewById(R.id.SetPlaceButton);
        nameTxt = (EditText) findViewById(R.id.TxtName);
        descriptionTxt = (EditText) findViewById(R.id.TxtDescription);
        timeTxt = (EditText) findViewById(R.id.TxtTime);
        place = (EditText) findViewById(R.id.Place);
        businessListView = (ListView) findViewById(R.id.list);

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
        dbHandler = new DatabaseHandler(getApplicationContext());

        final Button addBtn = (Button) findViewById(R.id.addBusiness);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Business business = new Business(dbHandler.getBusinessCount(), String.valueOf(nameTxt.getText()), String.valueOf(descriptionTxt.getText()), String.valueOf(timeTxt.getText()), String.valueOf(place.getText()));
                if (!businessExists(business)) {
                    dbHandler.createBusiness(business);
                    businesses.add(business);

                    Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + " has been added", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),nameTxt.getText().toString()+" is already exists",Toast.LENGTH_SHORT).show();
            }
        });
        Button buttonList = (Button) findViewById(R.id.listBusiness);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddBusinessActivity.this, BusinessActivity.class);
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
        List<Business> addableBusiness = dbHandler.getAllBusinesses();
        int businessCount = dbHandler.getBusinessCount();
        for (int i = 0; i < businessCount; i++) {
            businesses.add(addableBusiness.get(i));
        }
        SetPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapActivity = new Intent(AddBusinessActivity.this,MapActivity.class);
                startActivity(toMapActivity);
            }
        });
        String adress = getIntent().getStringExtra("Adress");
        place.setText(adress);

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

    private boolean businessExists(Business business){

        String name = business.getName();
        int BusinessCount= businesses.size();
        for (int i=0;i<BusinessCount;i++){
            if(name.compareToIgnoreCase(businesses.get(i).getName())==0)
                return true;
        }
        return false;
    }
}
