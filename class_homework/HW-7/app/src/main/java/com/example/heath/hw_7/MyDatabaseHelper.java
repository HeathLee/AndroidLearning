package com.example.heath.hw_7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by heath on 15-12-4.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final  int DB_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + TABLE_NAME +
                "(id integer primary key autoincrement, " +
                "sid text not null, " +
                "name text not null, " +
                "phone text not null);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sid", contact.getSid());
        contentValues.put("name", contact.getName());
        contentValues.put("phone", contact.getPhone());

        long id = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return id;
    }

    public int update(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "sid = ?";
        String[] whereArgs = {contact.getSid()};

        ContentValues contentValues = new ContentValues();
        contentValues.put("sid", contact.getSid());
        contentValues.put("name", contact.getName());
        contentValues.put("phone", contact.getPhone());

        int rows = db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        db.close();
        return rows;
    }

    public int delete(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "sid = ?";
        String[] whereArgs = {contact.getSid()};

        int rows = db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
        return rows;
    }

    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
