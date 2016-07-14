package com.ruthiy.care2car.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.activities.LoginPageActivity;
import com.ruthiy.care2car.activities.MainActivity;
import com.ruthiy.care2car.entities.User;
import com.ruthiy.care2car.utils.Config;

import java.io.IOException;

// abbreviated tag name
public class RegistrationIntentService extends IntentService {

    // abbreviated tag name
    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String areaId =  intent.getStringExtra("areaId");
        FirebaseMessaging.getInstance().subscribeToTopic(areaId);
    }

    // Make a call to Instance API
      /*  FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
        // request token that will be used by the server to send push notifications
        String token = instanceID.getToken();
        Log.d(TAG, "FCM Registration Token: " + token);

        // pass along this data
        sendRegistrationToServer(token);*/

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }

   /* private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }*/
}