package com.example.ghassen.myproject;

/**
 * Created by ghassen on 14/12/2016.
 */
public class Family {

    private String _name,_time;
    private  String _place;
    private  int _id;

    public  Family(int id, String name, String time,String place){
        _id=id;
        _name=name;

        _time=time;
        _place=place;

    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getTime() {

        return _time;
    }

    public String getPlace() {

        return _place;
    }
}
