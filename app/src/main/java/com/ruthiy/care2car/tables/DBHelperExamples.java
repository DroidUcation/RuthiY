package com.ruthiy.care2car.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

public class DBHelperExamples extends SQLiteOpenHelper {

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.tutionimage/databases/";
    private static String DB_NAME = "master_subject.db";
    private SQLiteDatabase myDatabase;
    private final Context myContext;
    private boolean add2;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DBHelperExamples(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            // do nothing - database already exist
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

        // Toast.makeText(myContext, "Copy Done", 300).show();
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (myDatabase != null)
            myDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Add your public helper methods to access and get content from the
        // database.
        // You could return cursors by doing "return myDatabase.query(....)" so
        // it'd be easy
        // to you to create adapters for your views.
    }

    //Insert Title into database
    public long insertTitle(String sem, String subject, String jee, String chp, String marks, String time) {

        ContentValues initialValues = new ContentValues();


       /* SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();


        // convert date to string
        String dateString = dateFormat.format(date);

        initialValues.put(KEY_DATE,dateString);*/

        initialValues.put("sem", sem);
        initialValues.put("subject", subject);
        initialValues.put("jee_flag", jee);
        initialValues.put("chapter", chp);
        initialValues.put("marks", marks);
        initialValues.put("time", time);

        return myDatabase.insert("testdata", null, initialValues);
    }

    //Fetching data from the database
    public Cursor getTestData() throws SQLException {

        Cursor cursor = null;
        String queryString = "";


        if (queryString.split("-")[2].equalsIgnoreCase("n")) {

            queryString = "select * from testdata where jee_flag='n' order by id desc";

        } else if (/*NewStartPanel.*/queryString.split("-")[2].equalsIgnoreCase("y")) {

            queryString = "select * from testdata where jee_flag='y' order by id desc";
        }

        System.out.println("Query String:................  " + queryString);
        cursor = myDatabase.rawQuery(queryString, null);

        //  cursor.moveToFirst();

        System.out.println("cursor count of testdata is:" + cursor.getCount());


        return cursor;

    }
}