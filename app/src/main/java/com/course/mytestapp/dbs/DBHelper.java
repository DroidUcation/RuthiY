package com.course.mytestapp.dbs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ruthi.Y on 4/18/2016.
 */
    public class DBHelper extends SQLiteOpenHelper {


    private static final String TAG = "contentProvider";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FirstAidMyth.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MythContract.TABLE_NAME + " (" +
                    MythContract._ID + " INTEGER PRIMARY KEY , " +
                    MythContract.COLUMN_NAME_MYTH_NO + TEXT_TYPE + COMMA_SEP +
                    MythContract.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    MythContract.COLUMN_NAME_DESC + TEXT_TYPE + COMMA_SEP +
                    MythContract.COLUMN_NAME_ISITTRUE + " INTEGER )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MythContract.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
