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
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String PLANTS = "PLANTS";
    public static final String ITEMID = "id";
    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String DESCRIPTION = "description";
    public static final String SIZE = "size";
    public static final String SUN_EXPOSURE = "sun_exposure";
    public static final String SOIL_TYPE = "soil_type";
    public static final String IMAGE = "image";
    public static final String QUANTITY = "quantity";
    public static final String OWNER = "owner";

    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table " + USERSTB + "(" + USERNAME + " VARCHAR(20) primary key, " + PASSWORD + " VARCHAR(20), " + MOBILE + " VARCHAR(10));");
        sqLiteDatabase.execSQL("create Table " + PLANTS + "(" + ITEMID + " INTEGER primary key AUTOINCREMENT, " + NAME + " VARCHAR(20), " + DESCRIPTION + " VARCHAR(150), " + SIZE + " VARCHAR(20), " + QUANTITY + " INTEGER," + SUN_EXPOSURE + " VARCHAR(20), " + SOIL_TYPE + " VARCHAR(20), " + IMAGE + " BLOB, "+ OWNER +" VARCHAR(20), foreign key (" + OWNER + ") references " + USERSTB + "(" + USERNAME + ")  );");

        //sqLiteDatabase.execSQL("create table rental_order (tid INTEGER primary key autoincrement, QUANTITY INTEGER, foreign key (customer) references USERSTB (UID), foreign key (rented_item) references PLANTS (ITEMID)); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean registerUser(String username, String password, String mobile) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(PASSWORD, password);
        contentValues.put(MOBILE, mobile);
        long result = MyDB.insert(USERSTB, null, contentValues);
        if (result == -1) return false;
        return true;
    }

    public Boolean checkUsername(String username) {//checks if username exists in db
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + USERSTB + " where " + USERNAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0) return true;
        return false;
    }
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

}
