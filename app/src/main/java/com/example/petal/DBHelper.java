package com.example.petal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Petal.db";
    public static final String USERSTB = "USERS";
    public static final String UID = "uid";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PLANTS = "PLANTS";
    public static final String ITEMID = "id";
    public static final String NAME = "name";
    public static final String PLANT_ID = "plant_id";
    public static final String MOBILE = "mobile";
    public static final String DESCRIPTION = "description";
    public static final String SIZE = "size";
    public static final String SUN_EXPOSURE = "sun_exposure";
    public static final String SOIL_TYPE = "soil_type";
    public static final String IMAGE = "image";

    public DBHelper(@Nullable Context context){
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table " + USERSTB + "(" + UID  + " INTEGER primary key AUTOINCREMENT, " + USERNAME + " VARCHAR(15), " + PASSWORD + " VARCHAR(20), " + MOBILE + " VARCHAR(10)," +" FOREIGN KEY (" + PLANT_ID + ") REFERENCES " + PLANTS + " ("+ITEMID+") );");
        sqLiteDatabase.execSQL("create Table " + PLANTS + "(" + ITEMID + " INTEGER primary key AUTOINCREMENT, " + NAME + " VARCHAR(20), " + DESCRIPTION + " VARCHAR(150), " + SIZE + " VARCHAR(20), " + SUN_EXPOSURE + " VARCHAR(20), " + SOIL_TYPE + " varchar(20), " + IMAGE + " BLOB );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean registerUser(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        long result = MyDB.insert(USERSTB, null, contentValues);
        if(result==-1) return false;
        return true;
    }

    public Boolean checkUsername(String username) {//checks if username exists in db
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + USERSTB + " where " + USERNAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0) return true;
        return false;
    }

}
