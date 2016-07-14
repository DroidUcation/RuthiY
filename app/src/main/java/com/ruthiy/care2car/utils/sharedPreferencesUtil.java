package com.ruthiy.care2car.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ruthiy.care2car.activities.MainActivity;
import com.ruthiy.care2car.entities.User;

/**
 * Created by Ruthi.Y on 7/14/2016.
 */


public class sharedPreferencesUtil {

    Context context;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    static SharedPreferences sharedpreferences;
    static Gson mGson = new Gson();

    public static String getUserKeyFromSP(Context context){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userKey = sharedpreferences.getString("key", null);
        return userKey;
    }



    public static String getUserTokenFromSP(Context context){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userToken = sharedpreferences.getString("token", null);
        return userToken;
    }

    public static User getUserFromSP(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String user = sharedpreferences.getString(Name, null);
        return mGson.fromJson(user, User.class);
    }
}
