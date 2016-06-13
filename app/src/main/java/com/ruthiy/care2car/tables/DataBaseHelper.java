package com.ruthiy.care2car.tables;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ruthi.Y on 6/6/2016.
 */

/*TableName:
* R_STATUS
* R_AREA
* R_CATEGORY
* R_ENGINE_VALUME
* R_EMERGENCY_PHONE
* B_USER
* B_REQUEST
* R_REQUEST_HIS
* */
public class DataBaseHelper extends SQLiteOpenHelper {
    
    private static final String TAG = "contentProvider";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "care2car.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";

    //Create Tables
    private static final String SQL_CREATE_STATUS = TablesContract.Status.getSqlCreate();
    private static final String SQL_CREATE_AREA = TablesContract.Area.getSqlCreate();
    private static final String SQL_CREATE_CATEGORY = TablesContract.Category.getSqlCreate();
    private static final String SQL_CREATE_ENGINE_VOLUME= TablesContract.EngineValume.getSqlCreate();
    private static final String SQL_CREATE_EMERGENCY_PHONE = TablesContract.EmergencyPhone.getSqlCreate();
    private static final String SQL_CREATE_USER = TablesContract.User.getSqlCreate();
    private static final String SQL_CREATE_REQUEST = TablesContract.Request.getSqlCreate();
    private static final String SQL_CREATE_REQUEST_HIS = TablesContract.RequestHis.getSqlCreate();

    //Drop Tables
    private static final String SQL_DELETE_STATUS =
            "DROP TABLE IF EXISTS " + TablesContract.Status.TABLE_NAME;
    private static final String SQL_DELETE_AREA =
            "DROP TABLE IF EXISTS " + TablesContract.Area.TABLE_NAME;
    private static final String SQL_DELETE_CATEGORY =
            "DROP TABLE IF EXISTS " + TablesContract.Category.TABLE_NAME;
    private static final String SQL_DELETE_ENGINE_VOLUME =
            "DROP TABLE IF EXISTS " + TablesContract.EngineValume.TABLE_NAME;
    private static final String SQL_DELETE_EMERGENCY_PHONE =
            "DROP TABLE IF EXISTS " + TablesContract.EmergencyPhone.TABLE_NAME;
    private static final String SQL_DELETE_USER=
            "DROP TABLE IF EXISTS " + TablesContract.User.TABLE_NAME;
    private static final String SQL_DELETE_REQUEST =
            "DROP TABLE IF EXISTS " + TablesContract.Request.TABLE_NAME;
    private static final String SQL_DELETE_REQUEST_HIS =
            "DROP TABLE IF EXISTS " + TablesContract.RequestHis.TABLE_NAME;
    

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_STATUS);
        db.execSQL(SQL_CREATE_AREA);
        db.execSQL(SQL_CREATE_CATEGORY);
        db.execSQL(SQL_CREATE_EMERGENCY_PHONE);
        db.execSQL(SQL_CREATE_ENGINE_VOLUME);
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_REQUEST);
        db.execSQL(SQL_CREATE_REQUEST_HIS);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL(SQL_DELETE_STATUS);
        db.execSQL(SQL_DELETE_AREA);
        db.execSQL(SQL_DELETE_CATEGORY);
        db.execSQL(SQL_DELETE_EMERGENCY_PHONE);
        db.execSQL(SQL_DELETE_ENGINE_VOLUME);
        db.execSQL(SQL_DELETE_USER);
        db.execSQL(SQL_DELETE_REQUEST);
        db.execSQL(SQL_DELETE_REQUEST_HIS);
        
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    

}