package com.course.mytestapp.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.course.mytestapp.MainActivity;
import com.course.mytestapp.R;
import com.course.mytestapp.dbs.DBHelper;
import com.course.mytestapp.dbs.MythContract;

/**
 * Created by Ruthi.Y on 5/12/2016.
 */
public class FirstAidMythsService extends IntentService {

    SQLiteDatabase db;
    Cursor cursor;
    public FirstAidMythsService() {
        super("FirstAidMythsService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String count =  intent.getStringExtra("count");
        Log.i("FirstAidMythsService", "count is" + count);
        addNewMyths(count);
        addNotification(getApplicationContext(), count);
    }

    private void addNewMyths(String count) {

        int rowsDeleted = getContentResolver().delete(MythContract.CONTENT_URI, null,null);
         Log.i("FirstAidMythsService", "rowsDeleted: " + rowsDeleted);
        // Create a new map of values, where column names are the keys
        insertRow(1, getString(R.string.myth1) + count, getString(R.string.mythDesc1), getString(R.string.trueOrfalse1));
        insertRow(2, getString(R.string.myth2) + count,getString(R.string.mythDesc2), getString(R.string.trueOrfalse2));
        insertRow(3, getString(R.string.myth3) + count,getString(R.string.mythDesc3), getString(R.string.trueOrfalse3));
        insertRow(4, getString(R.string.myth4) + count,getString(R.string.mythDesc4), getString(R.string.trueOrfalse4));
        insertRow(5, getString(R.string.myth5) + count, getString(R.string.mythDesc5), getString(R.string.trueOrfalse5));
    }

    public long insertRow(Integer mythNameId, String mythTitle, String mythDesc, String isItTrue) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(MythContract.COLUMN_NAME_MYTH_NO, mythNameId);
        initialValues.put(MythContract.COLUMN_NAME_TITLE, mythTitle);
        initialValues.put(MythContract.COLUMN_NAME_DESC, mythDesc);
        initialValues.put(MythContract.COLUMN_NAME_ISITTRUE, Integer.parseInt(isItTrue));

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        Uri uri = getContentResolver().insert(MythContract.CONTENT_URI, initialValues);
        newRowId = MythContract.getIdFromUri(uri) ;
        Log.i("FirstAidMythsService", "insert newRowId: " + newRowId);
        return newRowId;
    }

    private void addNotification(Context context, String count) {
                PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

            NotificationCompat.Builder notification =
            new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.first_aid_pic)
                    .setContentTitle("'First Aid' - New myths is available " + count)
                    .setContentText("Click to see the news.");

        notification.setContentIntent(contentIntent);
        notification.setDefaults(Notification.DEFAULT_SOUND);
        notification.setAutoCancel(true);

        NotificationManager mNotificationManager =
        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification.build());

 }


    public static class AlarmReceiver extends BroadcastReceiver {

        private static Integer count = 0;
        @Override
        public void onReceive(Context context, Intent intent) {
            count++;
            Intent sendIntent = new Intent(context, FirstAidMythsService.class);
            sendIntent.putExtra("count", count.toString());
            context.startService(sendIntent);

        }
    }
}
