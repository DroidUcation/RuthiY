package com.ruthiy.care2car.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.gson.Gson;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.entities.User;
import com.ruthiy.care2car.utils.Config;

public class LoginPageActivity extends AppCompatActivity {
    EditText ed1,ed2;
    Spinner sp1;
    Button b1;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;
    Gson mGson  = new Gson();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText2);
        sp1=(Spinner) findViewById(R.id.sp_area);

        b1=(Button)findViewById(R.id.button);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b1.setEnabled(false);
                user = new User();

                String n  = ed1.getText().toString();
                String ph  = ed2.getText().toString();
                String e  = sp1.getSelectedItem().toString();

                user.setName(n);
                user.setPhoneNumber(ph);
                user.setAreaId(e);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                String gsonString = mGson.toJson(user);
                editor.putString(Name, gsonString);
                editor.commit();
                saveUserOnFireBase();

                Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
                //intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }
    public void saveUserOnFireBase(){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_USER_URL);
        //Storing values to firebase
        ref.push();
        ref.child(user.getPhoneNumber()).setValue(user);

    }
}
