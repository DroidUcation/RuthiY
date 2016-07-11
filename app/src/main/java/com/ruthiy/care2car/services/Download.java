package com.ruthiy.care2car.services;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.ruthiy.care2car.activities.MainActivity;
import com.ruthiy.care2car.http.PostExample;

import java.io.IOException;

import static com.ruthiy.care2car.http.PostExample.bowlingJson;

/**
 * Created by Ruthi.Y on 7/7/2016.
 */
public class Download extends AsyncTask<String, Void, String> {
    ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Hi", "Download Commencing");

        PostExample example = new PostExample();
            Call response = example.post("https://fcm.googleapis.com/fcm/send", bowlingJson(),new Callback(){
                @Override
                public void onFailure(Call call, IOException e) {
                    // Something went wrong
                    Log.d("Hi", "Something went wrong");
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
    }

    @Override
    protected String doInBackground(String... params) {
    // avoid creating several instances, should be singleon
       /* final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .header("Authorization", "key=AIzaSyDk4Vgg0xNXMJasOiz3ofBvoDbdwpmGYDE")
                .header("Content-Type","application/json")
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    // do something wih the result
                }
            }*/
        //INSERT YOUR FUNCTION CALL     HERE
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
