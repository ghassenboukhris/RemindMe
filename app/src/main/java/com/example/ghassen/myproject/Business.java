package com.example.ghassen.myproject;

/**
 * Created by ghassen on 14/12/2016.
 */
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Business  {

    private String _name,_description,_time;
    private  String _place;
    private  int _id;

    public  Business(int id, String name, String description,String time,String place){
        _id=id;
        _name=name;
        _description=description;
        _time=time;
        _place=place;

    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public String getTime() {
        return _time;
    }

    public String getPlace() {
        return _place;
    }
}
