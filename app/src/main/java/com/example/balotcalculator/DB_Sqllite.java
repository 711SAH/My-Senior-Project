package com.example.balotcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB_Sqllite extends SQLiteOpenHelper {

    public static final String DBname = "data.db";

    // constructor method
    public DB_Sqllite(@Nullable Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: create table = "myTable" and fields = id & content
        db.execSQL("create table myTable (id INTEGER PRIMARY KEY AUTOINCREMENT , content TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: if the table not exists then create it.
        db.execSQL("DROP TABLE IF EXISTS myTable");
        onCreate(db);
    }
     // this method is used in MainActivity
    //TODO: this method to insert item in db
    public boolean insertData(String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);

        long result = db.insert("myTable", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }
    // the last tow methods used in MainActivity4
    //TODO: this method to return data into arraylist
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from myTable", null);
        return res;
    }


    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM myTable"); //delete all rows in a table
        db.close();
    }
}
