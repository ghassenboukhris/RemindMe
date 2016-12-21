package com.example.ghassen.myproject;

/**
 * Created by ghassen on 14/12/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler1 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "achatManager",

    TABLE_ACHAT= "achat",
            KEY_ID_Achat= "id",
            KEY_NAME_ACHAT="name",
            KEY_TIME_ACHAT="time",
            KEY_PLACE_ACHAT="place";




    public DatabaseHandler1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        db.execSQL("CREATE TABLE " +TABLE_ACHAT + "(" + KEY_ID_Achat+ " INTEGER PRIMARY KEY AUTOINCREMENT," +KEY_NAME_ACHAT+ " TEXT, " +KEY_TIME_ACHAT+ " TEXT," +KEY_PLACE_ACHAT+ " TEXT);");



    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_ACHAT);
        onCreate(db);
    }


    public void  createACHAT(Achat achat){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_ACHAT, achat.getName());
        values.put(KEY_TIME_ACHAT, achat.getTime());
        values.put(KEY_PLACE_ACHAT, achat.getPlace());
        db.insert(TABLE_ACHAT, null, values);
        db.close();
    }


    public Achat getAchat (int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_ACHAT,new String[]{KEY_ID_Achat,KEY_NAME_ACHAT,KEY_TIME_ACHAT,KEY_PLACE_ACHAT},KEY_ID_Achat+"=?",new  String[]{ String.valueOf(id)},null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();

        Achat achat=new Achat(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                cursor.getString(3));
        db.close();
        return  achat;
    }

    public void deleteAchat(Achat achat){
        SQLiteDatabase db= getWritableDatabase();
        db.delete(TABLE_ACHAT, KEY_ID_Achat + "=?", new String[]{String.valueOf(achat.getId())});
        db.close();
    }

    public int getAchatCount(){
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACHAT, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;

    }



    public int updateAchat(Achat achat){
        SQLiteDatabase db =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_ACHAT, achat.getName());
        values.put(KEY_TIME_ACHAT, achat.getTime());
        values.put(KEY_PLACE_ACHAT, achat.getPlace());
        return db.update(TABLE_ACHAT, values, KEY_ID_Achat + "=?", new String[]{String.valueOf(achat.getId())});
    }


    public List<Achat> getAllAchat(){
        List<Achat> achats = new ArrayList<Achat>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_ACHAT, null);
        if(cursor.moveToFirst()){
            do{
                Achat achat=new Achat(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3));
                achats.add(achat);
            }
            while (cursor.moveToNext());
        }
        return achats;
    }
    public void open()  {
        SQLiteDatabase db = getWritableDatabase();
    }

}

