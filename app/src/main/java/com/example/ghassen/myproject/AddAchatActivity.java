package com.example.ghassen.myproject;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddAchatActivity extends AppCompatActivity {
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    EditText nameTxt, timeTxt;
    EditText place;
    List<Achat> achats = new ArrayList<>();
    ListView achatListView;
    DatabaseHandler1 dbHandler;
    ArrayAdapter<Achat> AchatAdapter;
    private Button btn_date;
    private Button btn_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achat);

        nameTxt = (EditText) findViewById(R.id.TxtName);

        timeTxt = (EditText) findViewById(R.id.TxtTime);
        Spinner s=(Spinner)findViewById(R.id.Place);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AddAchatActivity.this,parent.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        achatListView = (ListView) findViewById(R.id.list);

        btn_date = (Button) findViewById(R.id.setDate);
        btn_time = (Button) findViewById(R.id.setTime);
        place=(EditText)findViewById(R.id.Place1);
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
        dbHandler = new DatabaseHandler1(getApplicationContext());

        final Button addBtn = (Button) findViewById(R.id.addAchat);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Achat achat = new Achat(dbHandler.getAchatCount(), String.valueOf(nameTxt.getText()), String.valueOf(timeTxt.getText()), String.valueOf(place.getText()));
                if (!achatExists(achat)) {
                    dbHandler.createACHAT(achat);
                    achats.add(achat);

                    Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + " has been added", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),nameTxt.getText().toString()+" is already exists",Toast.LENGTH_SHORT).show();
            }
        });
        Button buttonList = (Button) findViewById(R.id.listAchat);
        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddAchatActivity.this, AchatActivity.class);
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
        List<Achat> addableAchat = dbHandler.getAllAchat();
        int achatCount = dbHandler.getAchatCount();
        for (int i = 0; i < achatCount; i++) {
            achats.add(addableAchat.get(i));
        }
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

    private boolean achatExists(Achat achat){

        String name = achat.getName();
        int AchatCount= achats.size();
        for (int i=0;i<AchatCount;i++){
            if(name.compareToIgnoreCase(achats.get(i).getName())==0)
                return true;
        }
        return false;
    }





}
