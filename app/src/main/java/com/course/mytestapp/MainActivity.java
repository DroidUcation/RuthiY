package com.course.mytestapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;

import com.course.mytestapp.dbs.DBHelper;
import com.course.mytestapp.dbs.MythContract;
import com.course.mytestapp.services.FirstAidMythsService;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        moveToMyths();
        updateNewMyths();

    }

    private void createDB() {

        DBHelper mDbHelper = new DBHelper(getApplicationContext());
        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();

        //int rowsDeleted = getContentResolver().delete(MythContract.CONTENT_URI, null,null);
        //Log.i("MainActivity", "rowsDeleted: " + rowsDeleted);
        // Create a new map of values, where column names are the keys
        insertRow(1, getString(R.string.myth1),getString(R.string.mythDesc1), getString(R.string.trueOrfalse1));
        insertRow(2, getString(R.string.myth2),getString(R.string.mythDesc2), getString(R.string.trueOrfalse2));
        insertRow(3, getString(R.string.myth3),getString(R.string.mythDesc3), getString(R.string.trueOrfalse3));
        insertRow(4, getString(R.string.myth4),getString(R.string.mythDesc4), getString(R.string.trueOrfalse4));
        insertRow(5, getString(R.string.myth5), getString(R.string.mythDesc5),getString(R.string.trueOrfalse5));
    }

    public long insertRow(Integer mythNameId, String mythTitle, String mythDesc, String isItTrue) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(MythContract.COLUMN_NAME_MYTH_NO, mythNameId);
        initialValues.put(MythContract.COLUMN_NAME_TITLE, mythTitle);
        initialValues.put(MythContract.COLUMN_NAME_DESC, mythDesc);
        initialValues.put(MythContract.COLUMN_NAME_ISITTRUE, Integer.parseInt(isItTrue));

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(MythContract.TABLE_NAME, MythContract.COLUMN_NAME_NULLABLE, initialValues);

        return newRowId;
    }

    private void moveToMyths() {

        Log.i("MainActivity", " I am in moveToMyths method");
        final Context context = this;

        Button button = (Button) findViewById(R.id.next_button);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, FirstAidactivity.class);
                startActivity(intent);

            }

        });
    }

    private void updateNewMyths(){
        Intent alarmIntent = new Intent(this, FirstAidMythsService.AlarmReceiver.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
          //Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,
                PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);

    }

}
