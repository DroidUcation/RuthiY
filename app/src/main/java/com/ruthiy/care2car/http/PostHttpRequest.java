package com.ruthiy.care2car.http;


import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PostHttpRequest {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public Call post(String url, String json, okhttp3.Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "key=AIzaSyDk4Vgg0xNXMJasOiz3ofBvoDbdwpmGYDE")
                .addHeader("Content-Type","application/json")
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static String bowlingJson(String topic, String token, String message) {
        String to = topic!= null ? "/topics/" + topic : "/" + token;
        return
                "{\"to\":\"" + to + "\","
                        + " \"data\": {"
                        + "\"message\": \""+message+"\""
                        +"}}";
    }


    /*@TargetApi(Build.VERSION_CODES.KITKAT)
    private static void sendFCMtoTopics(){
        try {
            HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Authorization", "key=AIzaSyDk4Vgg0xNXMJasOiz3ofBvoDbdwpmGYDE");
            httpcon.setRequestMethod("POST");
            httpcon.connect();
            System.out.println("Connected!");
            byte[] outputBytes = "{\"notification\":{\"title\": \"My title\", \"text\": \"My text\", \"sound\": \"default\"}, \"to\": \"cAhmJfN...bNau9z\"}".getBytes("UTF-8");
            OutputStream os = httpcon.getOutputStream();
            os.write(outputBytes);
            os.close();

            // Reading response

            InputStream input = httpcon.getInputStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                for (String line; (line = reader.readLine()) != null;) {
                    System.out.println(line);
                }
            }

            System.out.println("Http POST request sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
   /* private void sendPost() throws Exception {

        //Below is a good tutorial , how to post json data
        //http://hmkcode.com/android-send-json-data-to-server/

        final String REGISTRATION_ID ="APA91bHH4iNCFdWUIXSHRXV3hsBeF8IU0ZElts9AXaHItDfRdRld-kwkVx69EFYZePPuLOW1hTkUCmAwyTeGdoirr25KJ3RG1AikGbBzsvqaPCLLz9YYCwPDuB6xUupVKmllNoTn2v0BRTTkC6OS_i8zerATtBP3gg" ;
        final String API_KEY = "AIzaSyARQTvQ5pRYEbW-9V98uDHNnn10Rwffx18";

        String url = "https://android.googleapis.com/gcm/send";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject mainData = new JSONObject();
        try {
            JSONObject data = new JSONObject();
            data.putOpt("message1", "test msg");
            data.putOpt("message2", "testing..................");
            JSONArray regIds = new JSONArray();
            regIds.put(REGISTRATION_ID);
            mainData.put("registration_ids", regIds);
            mainData.put("data", data);
            Log.e("test","Json data="+mainData.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        StringEntity se = new StringEntity(mainData.toString());
        post.setEntity(se);
        post.addHeader("Authorization", "key="+API_KEY);
        post.addHeader("Content-Type", "application/json");
        HttpResponse response = client.execute(post);
        Log.e("test" ,
                "response code ="+Integer.toString(response.getStatusLine().getStatusCode()));
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null)
        {
            result.append(line);
        }
        Log.e("test","response is"+result.toString());
    }*/

    public static void main(String[] args) throws IOException {
        PostHttpRequest example = new PostHttpRequest();
      //  sendFCMtoTopics();
        /*String json = example.bowlingJson("Jesse", "Jake");
        String response = example.post("https://fcm.googleapis.com/fcm/send", json);*/
       /* System.out.println(response);*/
    }
}