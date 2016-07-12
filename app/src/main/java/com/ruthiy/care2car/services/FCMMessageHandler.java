package com.ruthiy.care2car.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.activities.MainActivity;
import com.ruthiy.care2car.activities.ViewRequestActivity;
import com.ruthiy.care2car.activities.confirmRequest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Map;

public class FCMMessageHandler extends FirebaseMessagingService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String message = remoteMessage.getFrom();
        //String from = remoteMessage.getFrom();

        if (message.startsWith("/topics/")) {
            // message received from some topic.
            //Map<String, String> data = remoteMessage.getData();
            sendNotificationTopic(remoteMessage.getData().get("message"));

        } else {
            // normal downstream message.
            sendNotification(remoteMessage.getData().get("message"));
        }
        Log.d("onMessageReceived", "From: " + remoteMessage.getFrom());
        //Log.d("onMessageReceived", "Notification Message Body: " + remoteMessage.getNotification().getBody());
//        if (from.startsWith("/topics/dogs")) {
//            Log.d("fff", "got here");
//        } else {
//            // normal downstream message.
//        }
        /*Map<String, String> data = remoteMessage.getData();
        String from = remoteMessage.getFrom();
        createNotification(notification);*/
       // RemoteMessage.Notification notification = remoteMessage.getNotification();

    }

    // Creates notification based on title and body received
    /*private void createNotification(RemoteMessage.Notification notification) {
        Context context = getBaseContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(notification.getTitle())
                .setContentText(notification.getBody());
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }*/

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, confirmRequest.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("userKey", messageBody);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.care2car)
                .setContentTitle("Care2Car")
                .setContentText("Someone on his way to you!")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
        /*notification.setAutoCancel(true);*/

    }
    private void sendNotificationTopic(String messageBody) {
        Intent intent = new Intent(this, ViewRequestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("request", messageBody);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.care2car)
                .setContentTitle("Care2Car")
                .setContentText("Someone need your help!")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
        /*notification.setAutoCancel(true);*/

    }


}
