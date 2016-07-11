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

import java.sql.Date;
import java.util.HashMap;


public class RequestProvider extends ContentProvider {

    // fields for my content provider (in TableContract)

    // database declarations
    private DataBaseHelper dbHelper;
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "care2car";
    static final String TABLE_NAME = "B_REQUEST";
    static final int DATABASE_VERSION = 1;

    // fields for the database
    static final String _ID = TablesContract._ID;
    static final String AREA = TablesContract.Request.COLUMN_AREA_ID;
    static final String USER = TablesContract.Request.COLUMN_USER_NAME;
    static final String CATEGORY = TablesContract.Request.COLUMN_CATEGORY_ID;
    static final String VOLUNTEER = TablesContract.Request.COLUMN_VOLUNTEER_ID;
    static final String STATUS = TablesContract.Request.COLUMN_REQUEST_STATUS_ID;
    static final String START_DATE = TablesContract.Request.COLUMN_REQUEST_ST_DATE;

    // projection map for a query
    private static HashMap<String, String> REQUESTS_PROJECTION_MAP;
    
    // integer values used in content URI
    static final int REQUESTS = 1;
    static final int REQUEST_ID = 2;
    //static final int REQUEST_DETAILS = 3;

    private static final SQLiteQueryBuilder sRequestQueryBuilder;

    static{
        sRequestQueryBuilder = new SQLiteQueryBuilder();
        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sRequestQueryBuilder.setTables(
                TablesContract.Request.TABLE_NAME + " INNER JOIN " +
                        TablesContract.Status.TABLE_NAME +
                        " ON " + TablesContract.Request.TABLE_NAME +
                        "." + TablesContract.Request.COLUMN_REQUEST_STATUS_ID +
                        " = " + TablesContract.Status.TABLE_NAME +
                        "." + TablesContract._ID + " INNER JOIN " +
                        TablesContract.Status.TABLE_NAME +
                        " ON " + TablesContract.Request.TABLE_NAME +
                        "." + TablesContract.Request.COLUMN_USER_NAME +
                        " = " + TablesContract.User.TABLE_NAME +
                        "." + TablesContract._ID);
    }

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(TablesContract.Request.CONTENT_AUTHORITY, TablesContract.Request.PATH , REQUESTS);
        uriMatcher.addURI(TablesContract.Request.CONTENT_AUTHORITY, TablesContract.Request.PATH +"/#", REQUEST_ID);
        //uriMatcher.addURI(TablesContract.CONTENT_AUTHORITY, TablesContract.Request.PATH +"/#", REQUEST_DETAILS);
    }

    public RequestProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String id;
        switch (uriMatcher.match(uri)){
            case REQUESTS:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case REQUEST_ID:
                 id = uri.getPathSegments().get(1);
                count = db.delete(TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
           /* case REQUEST_DETAILS:
                 id = uri.getPathSegments().get(1);
                count = db.delete(TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;*/

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){

            /* Get all Requests records */
            case REQUESTS:
                return TablesContract.Request.CONTENT_TYPE;

            /* Get a particular Request */
            case REQUEST_ID:
                return TablesContract.Request.CONTENT_ITEM_TYPE;

            /* Get a particular Request */
        /*    case REQUEST_DETAILS:
                return TablesContract.Request.CONTENT_ITEM_TYPE;*/

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
            Uri _uri = ContentUris.withAppendedId(TablesContract.Request.CONTENT_URI, rowId);
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
        Cursor c;
        /*  By default sort on START DATE */
        sortOrder = (sortOrder == null || sortOrder == "")? START_DATE : sortOrder;

        switch (uriMatcher.match(uri)) {
            case REQUESTS:
                qb.setProjectionMap(REQUESTS_PROJECTION_MAP);
                c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);
                break;
            case REQUEST_ID:
                qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
                c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);
                break;
            /*case REQUEST_DETAILS:
                selectionArgs = new String[]{uri.getPathSegments().get(1), uri.getPathSegments().get(2)};
                c = sRequestQueryBuilder.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);
                break;*/

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

       /* register to watch a content URI for changes */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case REQUESTS:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case REQUEST_ID:
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
