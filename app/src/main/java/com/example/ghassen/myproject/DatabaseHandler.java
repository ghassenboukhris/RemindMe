package com.example.ghassen.myproject;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.net.Uri;

        import java.sql.SQLException;
        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Toshiba on 13/12/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "businessManager",
            TABLE_BUSINESS = "business",
            KEY_ID= "id",
            KEY_NAME="name",
            KEY_DESCRIPTION="description",
            KEY_TIME="time",
            KEY_PLACE="place";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + TABLE_BUSINESS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_TIME + " TEXT," + KEY_PLACE + " TEXT);");

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TABLE_BUSINESS);
        onCreate(db);
    }

    public void  createBusiness(Business business){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, business.getName());
        values.put(KEY_DESCRIPTION,business.getDescription());
        values.put(KEY_TIME,business.getTime());
        values.put(KEY_PLACE, business.getPlace());

        db.insert(TABLE_BUSINESS, null, values);
        db.close();
    }


    public Business getBusiness (int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_BUSINESS,new String[]{KEY_ID,KEY_NAME,KEY_DESCRIPTION,KEY_TIME,KEY_PLACE},KEY_ID+"=?",new  String[]{ String.valueOf(id)},null,null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        Business business = new Business(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        db.close();
        cursor.close();
        return business;

    }

    public void deleteBusiness(Business business){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUSINESS, KEY_ID + "=?", new String[]{String.valueOf(business.getId())});
        db.close();
    }

    public int getBusinessCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUSINESS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }


    public  int updateBusiness(Business business){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, business.getName());
        values.put(KEY_DESCRIPTION,business.getDescription());
        values.put(KEY_TIME, business.getTime());
        values.put(KEY_PLACE, business.getPlace());


        int rowsAffected= db.update(TABLE_BUSINESS, values, KEY_ID + "=?", new String[]{String.valueOf(business.getId())});
        db.close();
        return rowsAffected;
    }


    public List<Business> getAllBusinesses(){
        List<Business> businesses = new ArrayList<Business>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BUSINESS, null);
        if(cursor.moveToFirst()){

            do{
                businesses.add(new Business(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));

            }
            while (cursor.moveToNext());
        }
        return businesses;

    }

    public void open()  {
        SQLiteDatabase db = getWritableDatabase();
    }

}

