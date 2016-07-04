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
    public static final String MyPREFERENCES = "MyPrefs" ;
    Gson mGson = new Gson();
    User currentUser;
    public static final String Name = "nameKey";


    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedpreferences.edit().clear();
        sharedpreferences.edit().commit();
        String user = sharedpreferences.getString(Name, null);
        String areaId = "";
        if (user!=null) {
            currentUser = mGson.fromJson(user, User.class);
            areaId =  currentUser.getAreaId().toString();
            FirebaseMessaging.getInstance().subscribeToTopic("/topics/" + Config.FIREBASE_REQUESTS_AREA_URL + areaId);
        }

        // Make a call to Instance API
      /*  FirebaseInstanceId instanceID = FirebaseInstanceId.getInstance();
        String senderId = getResources().getString(R.string.gcm_defaultSenderId);
        // request token that will be used by the server to send push notifications
        String token = instanceID.getToken();
        Log.d(TAG, "FCM Registration Token: " + token);

        // pass along this data
        sendRegistrationToServer(token);*/
    }

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