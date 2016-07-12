package com.ruthiy.care2car.activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.entities.User;
import com.ruthiy.care2car.services.SendMessages;
import com.ruthiy.care2car.tables.TablesContract;
import com.ruthiy.care2car.utils.Config;
import com.ruthiy.care2car.utils.views.MySpinnerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenRequest extends AppCompatActivity implements Serializable, AdapterView.OnItemSelectedListener{
    Request request = new Request();
    Request openedRequest = new Request();
    User currentUser;
    User userFB ;
    String area;
    String userAddress;
    Location userLocation;
    TextView location ;
    EditText userName;
    EditText phone;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    SharedPreferences sharedpreferences;
    Gson mGson = new Gson();
   /* public Typeface font = Typeface.createFromAsset(getAssets(),
            "fonts/openSans/OpenSans-Light.ttf");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_request);
        setupSpinnerAdapter(R.id.spinner_category, R.array.category, null);
        setupSpinnerAdapter(R.id.spinner_engineValume, R.array.engineValume, null);
        setupSpinnerAdapter(R.id.spinner_type, R.array.type, null);
        getUserDetailsFromFireBase();
        Bundle  b = this.getIntent().getExtras();

        /*if (b.containsKey("user")) {
            ArrayList<User> users = getIntent().getParcelableArrayListExtra("user");
            for(User userDetails : users) {
                Log.i("", userDetails.getName());
                currentUser = userDetails;
            }
            userName.setText(currentUser.getName());
            userLocation = currentUser.getLocation();
            phone.setText(currentUser.getPhoneNumber());
        }*/

        TextView location = (TextView) findViewById(R.id.location);
        if (b.containsKey("address")) {
            userAddress = b.getString("address");
            location.setText(userAddress!=null? userAddress : "Uknown Address");
        }

        if (b.containsKey("area")) {
            area = b.getString("area");
        }

        Button button = (Button) findViewById(R.id.bn_openRequset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setRequestStatusId("Active");
                request.setLocation(userAddress); //currentUser.getLocation()
                TextView textView = (TextView) findViewById(R.id.et_remarks);
                request.setRemarks(textView.getText().toString());
                request.setRequestEndDate(null);
                java.util.Date date= new java.util.Date();
                request.setRequestStDate(new Timestamp(date.getTime()));
                request.setUserPhone(currentUser.getPhoneNumber());
                request.setUserName(currentUser.getName());
                if(checkMandatoryFileds(request)) {
                    Long requestId = saveRequestOnLocalDB(request);
                    saveRequestOnFireBase(requestId);
                    Intent intent = new Intent(OpenRequest.this, ViewRequestActivity.class);
                    ArrayList<Location> list = new ArrayList<Location>();
                    list.add(userLocation);
                    intent.putParcelableArrayListExtra("userLocation", list);
                    confirmOpenRequestDialog();
                   new SendMessages().execute("https://fcm.googleapis.com/fcm/send", currentUser.getAreaId(), request.getRequestKey(), "false");
                    /*intent.putExtra("request", request.getRequestKey());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    if (intent != null) startActivity(intent);*/
                }

            }
        });
    }



    private boolean checkMandatoryFileds(Request request) {
        return true;
    }

    public void setupSpinnerAdapter(int spinnerId, int arrayResId, String selected) {
        final Spinner spinner = (Spinner) findViewById(spinnerId);
        // Create an ArrayAdapter using the string array and a default spinner layout

        MySpinnerAdapter adapter = new MySpinnerAdapter(
                this,
                R.layout.item_spinner,
                Arrays.asList(getResources().getStringArray(arrayResId))
        );

        if(selected!=null){
            int position = adapter.getPosition("selected");
            spinner.setSelection(position);
        }else{ spinner.setSelection(adapter.getCount());
        }
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_category:
                request.setCategoryId((String) parent.getSelectedItem());
            case R.id.spinner_engineValume:
                request.setEngineVolumeId((String) parent.getSelectedItem());
            case R.id.spinner_type:
                request.setCarTypeId((String) parent.getSelectedItem());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private Long saveRequestOnLocalDB(Request request) {
        ContentValues initialValues = request.getForInsert(request);
        // Insert the new row, returning the primary key value of the new row
        Long newRowId;
        Uri uri = getContentResolver().insert(TablesContract.Request.CONTENT_URI, initialValues);
        newRowId = TablesContract.getIdFromUri(uri) ;


        return newRowId;
    }

    public void updateRequestKeyByIdOnLocalDB(Long requestId, String requestKey){
        Cursor c = getContentResolver().query(TablesContract.Request.CONTENT_URI.buildUpon().appendPath(requestId.toString()
        ).build(), null, null, null, null);
        Request requestForUpdate = Request.getObjectByCursor(c);;
        ContentValues initialValues = Request.getForInsert(requestForUpdate);
        initialValues.put(TablesContract.Request.COLUMN_REQUEST_KEY, requestKey);
        int ur2i = getContentResolver().update(TablesContract.Request.CONTENT_URI.buildUpon().appendPath(requestId.toString()).build(), initialValues, null,null );
    }

    public String getUserKeyFromSP(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userKey = sharedpreferences.getString("key", null);
        return userKey;
    }

    public String getUserTokenFromSP(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userToken = sharedpreferences.getString("token", null);
        return userToken;
    }
    public void saveRequestOnFireBase(Long requestId){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_REQUESTS_URL);
        //Storing values to firebase
        String userToken = sharedpreferences.getString("token", null);
        if (userToken != null) {
            request.setUserToken(userToken);
        }
        ref = ref.push();
        ref.setValue(request);
        String requestKey = ref.getKey();
        request.setRequestKey(ref.getKey());
        Log.d("TSG", "saveRequestOnFireBase: "+requestKey);

        Firebase fbUsers = new Firebase(Config.FIREBASE_REQUESTS_USER_URL + getUserKeyFromSP());
        //Storing values to firebase
        fbUsers = fbUsers.push();
        fbUsers.setValue(request);

        Firebase fbAreas = new Firebase(Config.FIREBASE_REQUESTS_AREA_URL + area);
        //Storing values to firebase
        fbAreas = fbAreas.push();
        fbAreas.setValue(request);
        //sendFCMtoTopics();
        updateRequestKeyByIdOnLocalDB(requestId, requestKey);
    }


   /* private void createPushNotification() {
        final String GCM_API_KEY = "yourKey";
        final int retries = 3;
        final String notificationToken = "deviceNotificationToken";
        Sender sender = new Sender(GCM_API_KEY);
        Message msg = new Message.Builder().build();

        try {
            Result result = sender.send(msg, notificationToken, retries);

            if (StringUtils.isEmpty(result.getErrorCodeName())) {
                logger.debug("GCM Notification is sent successfully");
                return true;
            }

            Log.e("Error occurred while sending push notification :" + result.getErrorCodeName());
        } catch (InvalidRequestException e) {
            Log.e("Invalid Request", e);
        } catch (IOException e) {
            Log.e("IO Exception", e);
        }
        return false;

    }*/

    public void getUserDetailsFromFireBase(){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_USER_URL);
        ref.child(getUserKeyFromSP()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                userFB = snapshot.getValue(User.class);
                if (userFB != null) {
                   /* Toast.makeText(getBaseContext(), "Hello " + userFB.getName(), Toast.LENGTH_LONG).show();
                   */ currentUser = userFB;
                    EditText userName = (EditText) findViewById(R.id.et_user_name);
                    EditText phone = (EditText) findViewById(R.id.et_user_phone);
                    userName.setText(currentUser.getName());
                    userLocation = currentUser.getLocation();
                    phone.setText(currentUser.getPhoneNumber());
                /*for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    BlogPost post = postSnapshot.getValue(BlogPost.class);
                    System.out.println(post.getAuthor() + " - " + post.getTitle());
                }*/
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    /*@TargetApi(Build.VERSION_CODES.KITKAT)
    private static void sendFCMtoTopics(){
        try {
            HttpURLConnection httpcon = (HttpURLConnection) ((new URL("https://fcm.googleapis.com/fcm/send").openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Authorization: key", "AIza...iD9wk");
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
    }*/

    public void confirmOpenRequestDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
       /* new AlertDialog.Builder(this).setTitle("Argh").setMessage("Watch out!").setIcon(R.drawable.icon).setNeutralButton("Close", null).show();
        */

        alertDialogBuilder.setIcon(R.drawable.confirm);
        alertDialogBuilder.setTitle("  Care2Car");
        alertDialogBuilder.setMessage("Your request send successfully ");
        alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
