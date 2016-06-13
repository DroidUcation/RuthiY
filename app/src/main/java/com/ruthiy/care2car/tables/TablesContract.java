package com.ruthiy.care2car.tables;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

import com.ruthiy.care2car.utils.DateUtils;

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
public class TablesContract implements BaseColumns {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.ruthiy.care2car.providers";
    //public static final String AUTHORITY ="com.course.mytestapp.providers.MythProvider";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final Uri CONTENT_URI = null;

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.

    /*public static final String PATH_USER = "user";*/
    public static String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/";
    public static String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DATE_TYPE = " DATETIME DEFAULT CURRENT_TIMESTAMP";
    private static final String COMMA_SEP = ", ";
    //REFERENCE TABLES
   public static class Status {
            public static final String TABLE_NAME = "R_STATUS";
            public static final String PATH = TABLE_NAME;
            public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
            public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
            public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

            public static final String COLUMN_STATUS_DESC = "STATUS_DESC";

           private static final String SQL_CREATE =
                    "CREATE TABLE " + TABLE_NAME + " (" +
                            TablesContract._ID + " INTEGER PRIMARY KEY , " +
                            COLUMN_STATUS_DESC + TEXT_TYPE + ")";

            public static String getSqlCreate() {
                return SQL_CREATE;
            }
    }


    public static class Area {
        public static final String TABLE_NAME = "R_AREA";
        public static final String PATH = TABLE_NAME;
        public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
        public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

        public static final String COLUMN_AREA_DESC = "AREA_DESC";
        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        TablesContract._ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_AREA_DESC + TEXT_TYPE + ")";

        public static String getSqlCreate() {
            return SQL_CREATE;
        }
    }
    public static class Category {
        public static final String TABLE_NAME = "R_CATEGORY";
        public static final String PATH = TABLE_NAME;
        public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
        public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

        public static final String COLUMN_CATEGORY_DESC = "CATEGORY_DESC";
        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        TablesContract._ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_CATEGORY_DESC + TEXT_TYPE + ")";

        public static String getSqlCreate() {
            return SQL_CREATE;
        }
    }

    public static class EngineValume{
        public static final String TABLE_NAME = "R_ENGINE_VALUME";
        public static final String PATH = TABLE_NAME;
        public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
        public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

        public static final String COLUMN_ENGINE_VALUME = "ENGINE_VALUME";
        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        TablesContract._ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_ENGINE_VALUME + TEXT_TYPE + ")";

        public static String getSqlCreate() {
            return SQL_CREATE;
        }
    }

    public static class EmergencyPhone{
        public static final String TABLE_NAME = "R_EMERGENCY_PHONE";
        public static final String PATH = TABLE_NAME;
        public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
        public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

        public static final String COLUMN_CONTACT_NAME = "CONTACT_NAME";
        public static final String COLUMN_PHONE_NUMBER = "PHONE_NUMBER";
        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        TablesContract._ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_CONTACT_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_PHONE_NUMBER + TEXT_TYPE + ")";

        public static String getSqlCreate() {
            return SQL_CREATE;
        }
    }

    //BUSINESS TABLES
    public static class User{
        public static final String TABLE_NAME = "B_USER";
        public static final String PATH = TABLE_NAME;
        public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
        public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

        public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
        public static final String COLUMN_LAST_NAME = "LAST_NAME";
        public static final String COLUMN_PHONE_NUMBER = "PHONE_NUMBER";
        public static final String COLUMN_PASSWORD = "PASSWORD";
        public static final String COLUMN_AREA_ID = "AREA_ID";
        public static final String COLUMN_LOCATION = "LOCATION";
        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        TablesContract._ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_FIRST_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                        COLUMN_PHONE_NUMBER + TEXT_TYPE + COMMA_SEP +
                        COLUMN_PASSWORD + TEXT_TYPE + COMMA_SEP +
                        COLUMN_AREA_ID + INTEGER_TYPE + COMMA_SEP +
                        COLUMN_LOCATION + TEXT_TYPE +")";

        public static String getSqlCreate() {
            return SQL_CREATE;
        }
    }

    public static class Request {
        public static final String TABLE_NAME = "B_REQUEST";
        public static final String PATH = TABLE_NAME;
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
        public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

        public static final String COLUMN_USER_ID = "USER_ID";
        public static final String COLUMN_AREA_ID = "AREA_ID";
        public static final String COLUMN_LOCATION = "LOCATION";
        public static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";
        public static final String COLUMN_ENGINE_VOLUME_ID = "ENGINE_VOLUME_ID";
        public static final String COLUMN_REQUEST_ST_DATE = "REQUEST_ST_DATE";
        public static final String COLUMN_REQUEST_END_DATE = "REQUEST_END_DATE";
        public static final String COLUMN_REQUEST_STATUS_ID = "REQUEST_STATUS_ID";
        public static final String COLUMN_VOLUNTEER_ID = "VOLUNTEER_ID"; //FK USER TABLE
        public static final String COLUMN_REMARKS = "REMARKS";

        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        TablesContract._ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_USER_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_AREA_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_LOCATION +  TEXT_TYPE + COMMA_SEP +
                        COLUMN_CATEGORY_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_ENGINE_VOLUME_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_REQUEST_ST_DATE +  DATE_TYPE + COMMA_SEP +
                        COLUMN_REQUEST_END_DATE +  DATE_TYPE + COMMA_SEP +
                        COLUMN_REQUEST_STATUS_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_VOLUNTEER_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_REMARKS +  TEXT_TYPE+ ")";

        public static String getSqlCreate() {
            return SQL_CREATE;
        }
    }

    public static class RequestHis {
        public static final String TABLE_NAME = "B_REQUEST_HIS";
        public static final String PATH = TABLE_NAME;
        public final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH).build();
        public static final String CONTENT_TYPE = TablesContract.CONTENT_TYPE + PATH;
        public static final String CONTENT_ITEM_TYPE =TablesContract.CONTENT_ITEM_TYPE + PATH;

        public static final String COLUMN_REQUEST_ID = "REQUEST_ID";
        public static final String COLUMN_REQUEST_STATUS_ID = "REQUEST_STATUS_ID";
        public static final String COLUMN_REMARKS = "REMARKS";
        public static final String COLUMN_UPDATE_DATE = "UPDATE_DATE";
        public static final String COLUMN_UPDATE_USER = "UPDATE_USER";
        private static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        TablesContract._ID + " INTEGER PRIMARY KEY , " +
                        COLUMN_REQUEST_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_REQUEST_STATUS_ID +  INTEGER_TYPE + COMMA_SEP +
                        COLUMN_REMARKS + TEXT_TYPE + COMMA_SEP +
                        COLUMN_UPDATE_DATE +  DATE_TYPE + COMMA_SEP +
                        COLUMN_UPDATE_USER + TEXT_TYPE +")";

        public static String getSqlCreate() {
            return SQL_CREATE;
        }
    }

    public static Uri buildUriWithId(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    public static Long getIdFromUri(Uri uri) {
        return ContentUris.parseId(uri) ;
    }

    public static Uri buildRequestWithDate(
            Integer requestId, long startDate) {
        long normalizedDate = DateUtils.normalizeDate(startDate);
        return Request.CONTENT_URI.buildUpon().appendPath(String.valueOf(requestId))
                .appendQueryParameter(Request.COLUMN_REQUEST_ST_DATE, Long.toString(normalizedDate)).build();
    }
    public static long getRequestDateFromUri(Uri uri) {
        String dateString = uri.getQueryParameter(Request.COLUMN_REQUEST_ST_DATE);
        if (null != dateString && dateString.length() > 0)
            return Long.parseLong(dateString);
        else
            return 0;
    }

    public static Uri buildRequestWithUserAndDate(Integer UserId, long date) {
        return CONTENT_URI.buildUpon().appendPath(String.valueOf(UserId))
                .appendPath(Long.toString(DateUtils.normalizeDate(date))).build();
    }

    public static String getUserFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    public static long getDateFromUri(Uri uri) {
        return Long.parseLong(uri.getPathSegments().get(2));
    }


}