package com.example.fbiprojet;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Form.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + DBContract.Form.TABLE_NAME_USER + " (" +
                DBContract.Form.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Form.USER_COLUMN_NAME + " TEXT," +
                DBContract.Form.USER_COLUMN_PASSWORD + " TEXT)";
        db.execSQL(query);

        query =  "CREATE TABLE " + DBContract.Form.TABLE_NAME_LIKED + " (" +
                DBContract.Form.LIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Form.LIKED_COLUMN_UID + " TEXT," +
                DBContract.Form.LIKED_COLUMN_USER + " INTEGER,"
                + " FOREIGN KEY ("+DBContract.Form.LIKED_COLUMN_USER+") REFERENCES "+DBContract.Form.TABLE_NAME_USER+"("+DBContract.Form.USER_ID+"))";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME_USER;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME_LIKED;
        db.execSQL(query);
        onCreate(db);
    }

    public long addUser(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.Form.USER_COLUMN_NAME,name);
        row.put(DBContract.Form.USER_COLUMN_PASSWORD,password);
            // return row primary key
        return db.insert(DBContract.Form.TABLE_NAME_USER,null,row);
    }

    public user getUser(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                DBContract.Form.USER_ID,
                DBContract.Form.USER_COLUMN_NAME,
                DBContract.Form.USER_COLUMN_PASSWORD
        };
        String selection = DBContract.Form.USER_COLUMN_NAME + " >= ?";
        String[] selectionArgs = {name};
        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


        while (cursor.moveToNext()) {
            @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex(DBContract.Form.USER_ID));
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(DBContract.Form.USER_COLUMN_NAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(DBContract.Form.USER_COLUMN_PASSWORD));
            cursor.close();
            return new user(id,username,password);
        }
        cursor.close();
        return null;
    }

    public long addlike(int id, String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.Form.LIKED_COLUMN_UID,uid);
        row.put(DBContract.Form.LIKED_COLUMN_USER,id);
        // return row primary key
        return db.insert(DBContract.Form.TABLE_NAME_LIKED,null,row);
    }

    public void deletelike (int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = DBContract.Form.LIKE_ID +" LIKE ? ";

        String[] selectionArgs = {String.valueOf(id)};
        int count = db.delete(DBContract.Form.TABLE_NAME_LIKED,selection,selectionArgs);
    }


}
