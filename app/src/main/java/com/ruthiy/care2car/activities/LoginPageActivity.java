package com.ruthiy.care2car.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.entities.User;
import com.ruthiy.care2car.providers.UserProvider;
import com.ruthiy.care2car.services.RegistrationIntentService;
import com.ruthiy.care2car.tables.TablesContract;
import com.ruthiy.care2car.utils.Config;

public class LoginPageActivity extends AppCompatActivity {
    EditText ed1,ed2;
    /*Spinner sp1;*/
    Button b1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    SharedPreferences sharedpreferences;
    Gson mGson  = new Gson();
    User user;
    Long userId;


    @Override
    protected void onStart() {
        super.onStart();
        String user = sharedpreferences.getString(Name, null);
        if (user != null) {
            Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        /* sp1=(Spinner) findViewById(R.id.sp_area);*/
        b1=(Button)findViewById(R.id.button);
       /* sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                b1.requestFocus();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });*/


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ed1.setVisibility(View.INVISIBLE);
                ed2.setVisibility(View.INVISIBLE);
             /*   sp1.setVisibility(View.INVISIBLE);*/
                b1.setVisibility(View.INVISIBLE);
                user = new User();

                String n  = ed1.getText().toString();
                String ph  = ed2.getText().toString();
              /*  String e  = sp1.getSelectedItem().toString();*/
                ProgressBar progressBar =(ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                user.setName(n);
                user.setPhoneNumber(ph);
              /*  user.setAreaId(e);*/

                SharedPreferences.Editor editor = sharedpreferences.edit();

                userId = saveUserOnLocalDB(user);
                String gsonString = mGson.toJson(user);
                editor.putString(Name, gsonString);
                editor.commit();
                saveUserOnFireBase(userId);
                registrationIntentService();
                Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
                //intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
    private  void registrationIntentService() {
        if (checkPlayServices()) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            /*intent.putExtra("areaId", sp1.getSelectedItem().toString());*/
            startService(intent);
        }
    }


    public Long saveUserOnLocalDB(User user){
        ContentValues initialValues = user.getForInsert(user);
        // Insert the new row, returning the primary key value of the new row
        Long newRowId;
        Uri uri = getContentResolver().insert(TablesContract.User.CONTENT_URI, initialValues);
        newRowId = TablesContract.getIdFromUri(uri) ;
      /*  int ur2i = getContentResolver().update(TablesContract.User.CONTENT_URI, initialValues, TablesContract._ID + " = ", new String[] { newRowId.toString() } );


        Cursor c = getContentResolver().query(TablesContract.User.CONTENT_URI.buildUpon().appendPath(newRowId.toString()
        ).build(), null, null, null, null);
        User.getFromDB(c);*/
        return newRowId;
    }

    public void updateUserKeyByIdOnLocalDB(Long userId, String userKey){
        Cursor c = getContentResolver().query(TablesContract.User.CONTENT_URI.buildUpon().appendPath(userId.toString()
        ).build(), null, null, null, null);
        User userForUpdate = User.getFromDB(c);
        ContentValues initialValues = user.getForInsert(userForUpdate);
        initialValues.put(TablesContract.User.COLUMN_USER_KEY, userKey);
        int ur2i = getContentResolver().update(TablesContract.User.CONTENT_URI.buildUpon().appendPath(userId.toString()).build(), initialValues, null,null );
    }

    public void saveUserOnFireBase(Long userId){/*
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String userKey = mDatabase.child("users").push().getKey();
        mDatabase.child("users").child(userKey).setValue(user);*/

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_USER_URL);
        //Storing values to firebase
        ref = ref.push();
        ref.setValue(user);
        String userKey = ref.getKey();
        Log.d("TSG", "saveUserOnFireBase: "+userKey);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        updateUserKeyByIdOnLocalDB(userId, userKey);
        editor.putString("key", userKey);
        editor.commit();
    }

    //notification fire base
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("MainActiviy", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    ///////************************************/////////////
}
