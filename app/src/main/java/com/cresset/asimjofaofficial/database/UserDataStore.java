package com.cresset.asimjofaofficial.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by cresset on 12/06/2017.
 */

public class UserDataStore extends SQLiteOpenHelper {

    private static final String TAG = UserDataStore.class.getSimpleName();
    //db name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user_info";
    private static final String TABLE_USER = "user";

    //login table columns

    private static final String KEY_ID = "id";
    private static final String KEY_F_NAME = "fName";
    private static final String KEY_L_NAME = "lName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    //create constructor
    public UserDataStore (Context context){
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    //create user database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE"+ TABLE_USER +"(" + KEY_ID + "INTEGER PRIMARY KEY," + KEY_F_NAME + "TEXT,"
                + KEY_L_NAME + "TEXT," + KEY_EMAIL + "TEXT UNIQUE," + KEY_UID + "TEXT," + KEY_CREATED_AT + "TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG , "User data created");

    }

    //upgrading database

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);
        //create table
        onCreate(db);
    }

    //sorting user detail in database
    public void addUser(String fname, String lname, String email, String uid, String created_at ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_F_NAME, fname);
        contentValues.put(KEY_L_NAME,lname);
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_UID, uid);
        contentValues.put(KEY_CREATED_AT, created_at);

        //inserting row
        long id = sqLiteDatabase.insert(TABLE_USER ,null, contentValues);
        sqLiteDatabase.close();
        Log.d(TAG, "New user inserted into app: " + id);

    }
    //getting user data from database
    public HashMap<String, String > getUserDetails(){
        HashMap<String, String > user = new HashMap<>();
        String selectQuery = "SELECT * FROM" + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //move to first row
        if (cursor.getCount() > 0){
            user.put("fname", cursor.getString(1));
            user.put("lname", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("uid", cursor.getString(4));
            user.put("created_at", cursor.getString(5));
        }
        cursor.close();
        //return user
        db.close();
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return user;
    }

    //delete user

    public void deleteUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        //delete all rows
        db.delete(TABLE_USER, null, null);
        db.close();
        Log.d(TAG, "delete all user info from database");
    }
}
