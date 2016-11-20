package com.teamsmokeweed.mylibrary.DatabaseMyLibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jongzazaal on 18/11/2559.
 */

public class DatabaseMy extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyStudent";
    private static final int DB_VERSION = 1;

    //        CREATE MEMBER
    public static final String TABLE_NAME_MEMBER = "member";
    public static final String COL_IDPERSON = "idPerson";
    public static final String COL_NAME = "name";
    public static final String COL_LASTNAME = "lastName";
    public static final String COL_TEL = "tel";
//    Type 0-> Member
//         1-> Admin
    public static final String COL_TYPE = "type";
//    CREATE USERPASS
    public static final String TABLE_NAME_USERPASS = "userpass";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
//     CREATE BOOK

    public static final String TABLE_BOOK = "book";
    public static final String COL_IDBOOK = "idBook";
    public static final String COL_NAMEBOOK = "nameBook";
    public static final String COL_NAMEWRITER = "nameWriter";


    public DatabaseMy(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
//        CREATE MEMBER
        db.execSQL("CREATE TABLE " + TABLE_NAME_MEMBER + " ("+ COL_IDPERSON +" INTEGER PRIMARY KEY, "
                + COL_NAME + " TEXT, " + COL_LASTNAME + " TEXT, "+COL_TEL+" TEXT, " + COL_TYPE + " TEXT);");
        db.execSQL("INSERT INTO " + TABLE_NAME_MEMBER + " (" + COL_IDPERSON + ", " + COL_NAME + ", " + COL_LASTNAME + ", "
                + COL_TEL+", " + COL_TYPE + ") VALUES ('1234567898765', 'Suchaj', 'JONGPRASIT', '0888888888', '1');");

//        CREATE USERPASS
        db.execSQL("CREATE TABLE " + TABLE_NAME_USERPASS + "("+COL_IDPERSON + " INTEGER PRIMARY KEY, "
                + COL_USERNAME + " TEXT, " + COL_PASSWORD + " TEXT);");
        db.execSQL("INSERT INTO " + TABLE_NAME_USERPASS + "("+COL_IDPERSON+", "+COL_USERNAME+", "+COL_PASSWORD
                +") VALUES ('1234567898765', 'admin', 'admin');");


//        CREATE BOOK
        db.execSQL("CREATE TABLE " + TABLE_BOOK + "("+COL_IDBOOK + " INTEGER PRIMARY KEY, "
                + COL_NAMEBOOK + " TEXT, " + COL_NAMEWRITER + " TEXT);");
        db.execSQL("INSERT INTO " + TABLE_BOOK + "("+COL_IDBOOK+", "+COL_NAMEBOOK+", "+COL_NAMEWRITER
                +") VALUES ('1', 'SampleBook', 'SampleWriter');");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MEMBER);
        onCreate(db);
    }
}