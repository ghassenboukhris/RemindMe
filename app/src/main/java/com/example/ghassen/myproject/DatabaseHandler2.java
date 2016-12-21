package com.example.ghassen.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ghassen on 14/12/2016.
 */
public class DatabaseHandler2 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "familyManager",

    TABLE_FAMILY= "family",
            KEY_ID_FAMILY= "id",
            KEY_NAME_FAMILY="name",
            KEY_TIME_FAMILY="time",
            KEY_PLACE_FAMILY="place";




    public DatabaseHandler2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {



        db.execSQL("CREATE TABLE " + TABLE_FAMILY + "(" + KEY_ID_FAMILY + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME_FAMILY + " TEXT, " + KEY_TIME_FAMILY + " TEXT," + KEY_PLACE_FAMILY + " TEXT);");



    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILY);
        onCreate(db);
    }


    public void  createFamily(Family family){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_FAMILY, family.getName());
        values.put(KEY_TIME_FAMILY, family.getTime());
        values.put(KEY_PLACE_FAMILY, family.getPlace());
        db.insert(TABLE_FAMILY, null, values);
        db.close();
    }


    public Family getFamily (int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_FAMILY,new String[]{KEY_ID_FAMILY,KEY_NAME_FAMILY,KEY_TIME_FAMILY,KEY_PLACE_FAMILY},KEY_ID_FAMILY+"=?",new  String[]{ String.valueOf(id)},null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();

        Family family=new Family(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        db.close();
        return  family;
    }

    public void deleteFamily(Family family){
        SQLiteDatabase db= getWritableDatabase();
        db.delete(TABLE_FAMILY, KEY_ID_FAMILY + "=?", new String[]{String.valueOf(family.getId())});
        db.close();
    }

    public int getFamilyCount(){
        SQLiteDatabase db =getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAMILY, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;

    }





    public List<Family> getAllFamily(){
        List<Family> families = new ArrayList<Family>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_FAMILY, null);
        if(cursor.moveToFirst()){
            do{
                Family family=new Family(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),
                        cursor.getString(3));
                families.add(family);
            }
            while (cursor.moveToNext());
        }
        return families;
    }
    public void open()  {
        SQLiteDatabase db = getWritableDatabase();
    }

}
