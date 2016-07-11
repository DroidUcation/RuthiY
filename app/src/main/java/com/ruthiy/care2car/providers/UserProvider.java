package com.ruthiy.care2car.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.ruthiy.care2car.tables.DataBaseHelper;
import com.ruthiy.care2car.tables.TablesContract;

import java.util.HashMap;

/**
 * Created by Ruthi.Y on 6/8/2016.
 */
public class UserProvider extends ContentProvider {

    // fields for my content provider (in TableContract)

    // database declarations
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "care2car";
    static final String TABLE_NAME = "B_USER";
    static final int DATABASE_VERSION = 1;

    // fields for the database
    static final String _ID = TablesContract._ID;
    static final String AREA = TablesContract.User.COLUMN_AREA_ID;
    static final String NAME = TablesContract.User.COLUMN_NAME;
    /*static final String FIRST_NAME = TablesContract.User.COLUMN_FIRST_NAME;
    static final String LAST_NAME = TablesContract.User.COLUMN_LAST_NAME;*/
    static final String PHONE_NUMBER = TablesContract.User.COLUMN_PHONE_NUMBER;

    // projection map for a query
    private static HashMap<String, String> USERS_PROJECTION_MAP;

    // integer values used in content URI
    static final int USERS = 1;
    static final int USER_ID = 2;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TablesContract.User.CONTENT_AUTHORITY, TablesContract.User.PATH, USERS);
        uriMatcher.addURI(TablesContract.User.CONTENT_AUTHORITY, TablesContract.User.PATH +"/#", USER_ID);
    }

    public UserProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case USERS:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case USER_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){

            /* Get all USERS records */
            case USERS:
                return TablesContract.User.CONTENT_TYPE;

            /* Get a particular User */
            case USER_ID:
                return TablesContract.User.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        ContentValues initialValues;
        initialValues = values != null ? new ContentValues(values):  new ContentValues();

        long rowId = db.insert(TABLE_NAME, null, values);
        if (rowId > 0) {
            Uri _uri = ContentUris.withAppendedId(TablesContract.User.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DataBaseHelper(context);
        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case USERS:
                qb.setProjectionMap(USERS_PROJECTION_MAP);
                break;
            case USER_ID:
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            /*  By default sort on FIRST NAME */
            sortOrder = NAME;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        /* register to watch a content URI for changes */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case USERS:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_ID:
                count = db.update(TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
