package com.course.mytestapp.dbs;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.course.mytestapp.providers.MythProvider;

/**
 * Created by Ruthi.Y on 4/18/2016.
 */
public class MythContract implements BaseColumns {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.course.mytestapp.providers";
    //public static final String AUTHORITY ="com.course.mytestapp.providers.MythProvider";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_MYTH = "myths";

    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_MYTH).build();

    //public static final String CONTENT_TYPE =  ContentResolver.CURSOR_ITEM_BASE_TYPE+ "/vnd.com.course.mytestapp.providers.MythProvider.myths";
    // "vnd.android.cursor.dir/vnd.jwei512.notes";
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MYTH;
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MYTH;

    public static final String TABLE_NAME = "myth";
    public static final String COLUMN_NAME_MYTH_NO = "mythNo";
    public static final String COLUMN_NAME_TITLE = "mythTitle";
    public static final String COLUMN_NAME_DESC = "mythDesc";
    public static final String COLUMN_NAME_ISITTRUE = "isItTrue";
    public static final String COLUMN_NAME_NULLABLE = COLUMN_NAME_DESC;

    public static Uri buildMythUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    /*
        Student: This is the buildWeatherLocation function you filled in.
     */
    public static Uri buildMythNo(Integer mythNo) {
        return CONTENT_URI.buildUpon().appendPath(mythNo.toString()).build();
    }

    public static String getMythNoFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    public static Long getIdFromUri(Uri uri) {
        return ContentUris.parseId(uri) ;
    }


    /*public static Uri buildWeatherLocationWithStartDate(
            String locationSetting, long startDate) {
        long normalizedDate = normalizeDate(startDate);
        return CONTENT_URI.buildUpon().appendPath(locationSetting)
                .appendQueryParameter(COLUMN_DATE, Long.toString(normalizedDate)).build();
    }

    public static Uri buildWeatherLocationWithDate(String locationSetting, long date) {
        return CONTENT_URI.buildUpon().appendPath(locationSetting)
                .appendPath(Long.toString(normalizeDate(date))).build();
    }

    public static String getLocationSettingFromUri(Uri uri) {
        return uri.getPathSegments().get(1);
    }

    public static long getDateFromUri(Uri uri) {
        return Long.parseLong(uri.getPathSegments().get(2));
    }

    public static long getStartDateFromUri(Uri uri) {
        String dateString = uri.getQueryParameter(COLUMN_DATE);
        if (null != dateString && dateString.length() > 0)
            return Long.parseLong(dateString);
        else
            return 0;
    }*/
}
