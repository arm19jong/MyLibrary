package com.teamsmokeweed.mylibrary.DatabaseMyLibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongzazaal on 18/11/2559.
 */

public class DBCheck {
    Context context;
    DatabaseMy mHelper;
    SQLiteDatabase mDb;
    Cursor mCursor;


    public DBCheck(Context context) {
        this.context = context;
    }

    public boolean IsAdmin(String username, String password) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT " + DatabaseMy.COL_IDPERSON +
                " FROM " + DatabaseMy.TABLE_NAME_USERPASS +
                " WHERE " + DatabaseMy.COL_USERNAME + " = " + "'" + username + "'" +
                " AND " + DatabaseMy.COL_PASSWORD + " = " + "'" + password + "'" + ";", null);

        if (mCursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public String getIdPersonAdmin(String username, String password) {
        String idPerson = "";
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT " + DatabaseMy.COL_IDPERSON +
                " FROM " + DatabaseMy.TABLE_NAME_USERPASS +
                " WHERE " + DatabaseMy.COL_USERNAME + " = " + "'" + username + "'" +
                " AND " + DatabaseMy.COL_PASSWORD + " = " + "'" + password + "'" + ";", null);
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            idPerson = mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_IDPERSON));
            mCursor.moveToNext();
        }
        return idPerson;
    }

    public String Who(String idPerson) {
        String fullName = "";
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT " + DatabaseMy.COL_NAME + ", " + DatabaseMy.COL_LASTNAME +
                " FROM " + DatabaseMy.TABLE_NAME_MEMBER +
                " WHERE " + DatabaseMy.COL_IDPERSON + " = " + "'" + idPerson + "'" + ";", null);

        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            fullName = mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_NAME));
            fullName += mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_LASTNAME));
            mCursor.moveToNext();
        }

        return fullName;

    }

    public List<ArrayList<String>> SelectMember(int type) {
        ArrayList<String> memberList = new ArrayList<>();
        ArrayList<String> idPerson = new ArrayList<>();
        List<ArrayList<String>> list = new ArrayList();
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseMy.TABLE_NAME_MEMBER + " WHERE " + DatabaseMy.COL_TYPE + " = '" + type + "';", null);
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            memberList.add(
                    mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_NAME)) + " " +
                            mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_LASTNAME))

            );
            idPerson.add(
                    mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_IDPERSON))
            );
            mCursor.moveToNext();
        }
        list.add(memberList);
        list.add(idPerson);

        return list;
    }

    public void AddMember(String idPerson, String name, String lastName, String tel, int type) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mDb.execSQL("INSERT INTO " + DatabaseMy.TABLE_NAME_MEMBER + " (" +
                DatabaseMy.COL_IDPERSON + ", " + DatabaseMy.COL_NAME + ", " +
                DatabaseMy.COL_LASTNAME + ", " + DatabaseMy.COL_TEL + ", " + DatabaseMy.COL_TYPE +

                ") VALUES ('" + idPerson + "', '" + name + "', '" + lastName + "', '" + tel + "', '" + type + "');");

    }

    public void AddUsernamePassword(String idPerson, String username, String password) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mDb.execSQL("INSERT INTO " + DatabaseMy.TABLE_NAME_USERPASS + " (" +
                DatabaseMy.COL_IDPERSON + ", " + DatabaseMy.COL_USERNAME + ", " + DatabaseMy.COL_PASSWORD +

                ") VALUES ('" + idPerson + "', '" + username + "', '" + password + "');");
    }

    public void UpdateMember(String idPerson, String name, String lastName, String tel) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mDb.execSQL("UPDATE " + DatabaseMy.TABLE_NAME_MEMBER + " SET " +
//                DatabaseMy.COL_IDPERSON + " = " +
                DatabaseMy.COL_NAME + " = '" + name + "', " +
                DatabaseMy.COL_LASTNAME + " = '" + lastName + "', " +
                DatabaseMy.COL_TEL + " = '" + tel + "' " +
                " WHERE " +
                DatabaseMy.COL_IDPERSON + " = '" + idPerson + "'" +

                ";");

    }

    public ArrayList<String> ShowMember(String idPerson) {
        ArrayList<String> memberList = new ArrayList<>();
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseMy.TABLE_NAME_MEMBER +
                " WHERE " + DatabaseMy.COL_IDPERSON + " = '" + idPerson + "'" +
                ";", null);
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            memberList.add(mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_IDPERSON)));
            memberList.add(mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_NAME)));
            memberList.add(mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_LASTNAME)));
            memberList.add(mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_TEL)));
            mCursor.moveToNext();
        }
        return memberList;

    }

    public void DelMember(String idPerson) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mDb.execSQL("DELETE FROM " + DatabaseMy.TABLE_NAME_MEMBER +
                " WHERE " +
                DatabaseMy.COL_IDPERSON + " = '" + idPerson + "'" +
                ";");
    }

    public List<ArrayList<String>> SelectBook() {
        ArrayList<String> bookList = new ArrayList<>();
        ArrayList<String> idBook = new ArrayList<>();
        List<ArrayList<String>> list = new ArrayList();
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseMy.TABLE_BOOK + ";", null);
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            bookList.add("Name: " +
                    mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_NAMEBOOK))
                    + "\nWriter: " +
                    mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_NAMEWRITER))

            );
            idBook.add(
                    mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_IDBOOK))
            );
            mCursor.moveToNext();
        }
        list.add(bookList);
        list.add(idBook);

        return list;
    }

    public ArrayList<String> ShowBook(String idBook) {
        ArrayList<String> BookList = new ArrayList<>();
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseMy.TABLE_BOOK +
                " WHERE " + DatabaseMy.COL_IDBOOK + " = '" + idBook + "'" +
                ";", null);
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            BookList.add(mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_IDBOOK)));
            BookList.add(mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_NAMEBOOK)));
            BookList.add(mCursor.getString(mCursor.getColumnIndex(DatabaseMy.COL_NAMEWRITER)));
            mCursor.moveToNext();
        }
        return BookList;

    }

    public void UpdateBook(String idBook, String nameBook, String nameWriter) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mDb.execSQL("UPDATE " + DatabaseMy.TABLE_BOOK + " SET " +
//                DatabaseMy.COL_IDPERSON + " = " +
                DatabaseMy.COL_NAMEBOOK + " = '" + nameBook + "', " +
                DatabaseMy.COL_NAMEWRITER + " = '" + nameWriter + "' " +
                " WHERE " +
                DatabaseMy.COL_IDBOOK + " = '" + idBook + "'" +

                ";");

    }

    public void DelBook(String idBook) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mDb.execSQL("DELETE FROM " + DatabaseMy.TABLE_BOOK +
                " WHERE " +
                DatabaseMy.COL_IDBOOK + " = '" + idBook + "'" +
                ";");
    }

    public void AddBook(String idBook, String nameBook, String nameWriter) {
        mHelper = new DatabaseMy(context);
        mDb = mHelper.getReadableDatabase();

        mDb.execSQL("INSERT INTO " + DatabaseMy.TABLE_BOOK + " (" +
                DatabaseMy.COL_IDBOOK + ", " + DatabaseMy.COL_NAMEBOOK + ", " +
                DatabaseMy.COL_NAMEWRITER +

                ") VALUES ('" + idBook + "', '" + nameBook + "', '" + nameWriter + "');");

    }
}
