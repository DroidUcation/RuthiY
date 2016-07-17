package com.ruthiy.care2car.services;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.activities.ViewRequestActivity;
import com.ruthiy.care2car.activities.confirmRequest;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.utils.Config;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class FCMMessageHandler extends FirebaseMessagingService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    Request requestFB;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String message = remoteMessage.getFrom();
        if (message.startsWith("/topics/")) {
            checkIfIsCurrentUser(remoteMessage.getData().get("message"));

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

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

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

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.care2car)
                .setContentTitle("Care2Car")
                .setContentText("Someone needs your help!")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
        /*notification.setAutoCancel(true);*/
    }

    private void checkIfIsCurrentUser(String remoteMessage) {
        // message received from some topic.
        //Map<String, String> data = remoteMessage.getData();
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_REQUESTS_URL);
        ref.child(remoteMessage).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                requestFB = snapshot.getValue(Request.class);
                //if (!requestFB.getUserToken().equals(sharedPreferencesUtil.getUserTokenFromSP(getBaseContext())))
                    sendNotificationTopic(snapshot.getKey());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }


}
