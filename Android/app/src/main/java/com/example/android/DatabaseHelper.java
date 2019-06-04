package com.example.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Register.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(email VARCHAR(30) PRIMARY KEY, fname VARCHAR(20) NOT NULL, lname VARCHAR(20)" +
                " NOT NULL, phone INT(10), password VARCHAR NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }

    //Inserting data into database
    public boolean insert(String email, String fname, String lname, int phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("fname", fname);
        contentValues.put("lname", lname);
        contentValues.put("phone", phone);
        contentValues.put("password", password);

        long ins = db.insert("user", null, contentValues);

        if(ins == -1) return false;
        else return true;
    }

    //Check if email already exists
    public boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email=?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    //Checking if the email and password exist
    public boolean checkLogin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email=? and password=?", new String[]{email, password});
        if (cursor.getCount()>0) return true;
        else return false;
    }
}
