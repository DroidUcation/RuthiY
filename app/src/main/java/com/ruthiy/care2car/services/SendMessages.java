package com.ruthiy.care2car.services;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.ruthiy.care2car.http.PostHttpRequest;

import java.io.IOException;

import static com.ruthiy.care2car.http.PostHttpRequest.bowlingJson;

/**
 * Created by Ruthi.Y on 7/7/2016.
 */
public class SendMessages extends AsyncTask<String, Void, String> {
    ProgressDialog pDialog;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Hi", "Download Commencing");
    }

    @Override
    protected String doInBackground(String... params) {
    // avoid creating several instances, should be singleon
        PostHttpRequest example = new PostHttpRequest();
        String json;
        if (params[3].toString().compareTo("true") == 0 ){
            json = bowlingJson(null, params[1], params[2]);
        }
        else{
            json =  bowlingJson(params[1], null, params[2]);
        }

        Call response = example.post("https://fcm.googleapis.com/fcm/send", json ,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                // Something went wrong
                Log.d("Hi", "Something went wrong :" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    // Do what you want to do with the response.
                    Log.d("Hi", "Do what you want to do with the response.");
                } else {
                    // Request not successful
                    Log.d("Hi", "Request not successful");
                }
            }
        });
        Log.d("Hi", "Executed");
        return "Executed!";

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("Hi", "Done Downloading.");
        //pDialog.dismiss();

    }
}
