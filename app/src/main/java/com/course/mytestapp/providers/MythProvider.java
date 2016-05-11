package com.course.mytestapp.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.course.mytestapp.dbs.DBHelper;
import com.course.mytestapp.dbs.MythContract;

/**
 * Created by Ruthi.Y on 5/9/2016.
 */
public class MythProvider  extends ContentProvider {
    private DBHelper mDB;

    private static final String TAG = "MythContentProvider";

    private static final String DATABASE_NAME = "notes.db";

    private static final int DATABASE_VERSION = 1;

    private static final String MYTH_TABLE_NAME = MythContract.TABLE_NAME;

    public static final String AUTHORITY ="com.course.mytestapp.providers.MythProvider";/*
    public static final String AUTHORITY = "jason.wei.apps.notes.providers.NotesContentProvider";*///"com.mamlambo.tutorial.tutlist.data.TutListProvider";

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final int MYTH = 1;
    public static final int MYTH_ID = 2;
    public static final int MYTH_NO = 3;


    static UriMatcher buildUriMatcher() {
        // I know what you're thinking.  Why create a UriMatcher when you can use regular
        // expressions instead?  Because you're not crazy, that's why.

        // All paths added to the UriMatcher have a corresponding code to return when a match is
        // found.  The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MythContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MythContract.PATH_MYTH, MYTH);
        matcher.addURI(authority, MythContract.PATH_MYTH + "/*", MYTH_ID);
        matcher.addURI(authority, MythContract.PATH_MYTH + "/*", MYTH_NO);
        // matcher.addURI(authority, MythTable.PATH_MYTH + "/*", WEATHER_WITH_LOCATION);
        //matcher.addURI(authority, WeatherContract.PATH_WEATHER + "/*/#", WEATHER_WITH_LOCATION_AND_DATE);
        //matcher.addURI(authority, WeatherContract.PATH_LOCATION, LOCATION);
        return matcher;
    }
/*    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, MYTH_TABLE_NAME, MYTHS);
        sURIMatcher.addURI(AUTHORITY, MYTH_TABLE_NAME + "/#", MYTH_ID);
        sURIMatcher.addURI(AUTHORITY, MYTH_TABLE_NAME + "/#", MYTH_NO);
    }*/
    @Override
    public boolean onCreate() {
        mDB = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DBHelper.DATABASE_NAME);

        int uriType = sUriMatcher.match(uri);
        Cursor retCursor;
        //retCursor = getMythDetailsByNo(uri, projection, sortOrder);
        switch (uriType) {
            case MYTH:
                retCursor = mDB.getReadableDatabase().query(
                        MYTH_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MYTH_ID:
                queryBuilder.appendWhere(MythContract._ID + "="
                + uri.getLastPathSegment());
                retCursor = queryBuilder.query(mDB.getReadableDatabase(),
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MYTH_NO:
                retCursor = getMythDetailsByNo(uri, projection, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    private Cursor getMythDetailsByNo(
            Uri uri, String[] projection, String sortOrder) {
        //String mythNo = uri.getLastPathSegment();
        String mythNo = MythContract.getMythNoFromUri(uri);

        return mDB.getReadableDatabase().query(
                MYTH_TABLE_NAME,
                projection,
                sMythNoSelection,
                new String[]{mythNo},
                null,
                null,
                sortOrder
        );

    }

    //location.location_setting = ? AND date = ?
    private static final String sMythNoSelection =
            MythContract.TABLE_NAME +
                    "." + MythContract.COLUMN_NAME_MYTH_NO + " = ? ";

    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            // Student: Uncomment and fill out these two cases
            case MYTH:
                return MythContract.CONTENT_TYPE;
            case MYTH_ID:
                return MythContract.CONTENT_ITEM_TYPE;
            case MYTH_NO:
                return MythContract.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = mDB.getWritableDatabase();
        int rowsAffected = 0;
        switch (uriType) {
            case MYTH:
                rowsAffected = sqlDB.delete(DBHelper.DATABASE_NAME,
                        selection, selectionArgs);
                break;
            case MYTH_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsAffected = sqlDB.delete(DBHelper.DATABASE_NAME,
                            MythContract._ID  + "=" + id, null);
                } else {
                    rowsAffected = sqlDB.delete(DBHelper.DATABASE_NAME,
                            selection + " and " + MythContract._ID + "=" + id,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsAffected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
