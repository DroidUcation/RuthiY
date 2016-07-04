package com.ruthiy.care2car.activities;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.entities.User;
import com.ruthiy.care2car.utils.Config;
import com.ruthiy.care2car.utils.views.MySpinnerAdapter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class OpenRequest extends AppCompatActivity implements Serializable, AdapterView.OnItemSelectedListener{
    Request request = new Request();
    Request openedRequest = new Request();
    User currentUser;
    User userFB ;
   /* public Typeface font = Typeface.createFromAsset(getAssets(),
            "fonts/openSans/OpenSans-Light.ttf");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open_request);
        TextView location = (TextView) findViewById(R.id.location);

        setupSpinnerAdapter(R.id.spinner_category, R.array.category, null);
        setupSpinnerAdapter(R.id.spinner_engineValume, R.array.engineValume, null);
        setupSpinnerAdapter(R.id.spinner_type, R.array.type, null);
        Bundle  b = this.getIntent().getExtras();

        if (b.containsKey("area")) {
            setupSpinnerAdapter(R.id.spinner_area, R.array.area, b.getString("area"));

        }
        if (b.containsKey("user")) {
            ArrayList<User> users = getIntent().getParcelableArrayListExtra("user");
            for(User userDetails : users) {
                Log.i("", userDetails.getName());
                currentUser = userDetails;
            }
            getUserDetailsFromFireBase();
            if(userFB!= null) {
                Toast.makeText(getBaseContext(), "Hello " + userFB.getName(), Toast.LENGTH_LONG).show();
            }
        }
        if (b.containsKey("address")) {
            String address = b.getString("address");
            location.setText(address!=null? address : "Uknown Address");
        }


        Button button = (Button) findViewById(R.id.bn_openRequset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setRequestStatusId("Active");
                request.setLocation(null); //currentUser.getLocation()
                TextView textView = (TextView) findViewById(R.id.et_remarks);
                request.setRemarks(textView.getText().toString());
                request.setRequestEndDate(null);
                java.util.Date date= new java.util.Date();
                request.setRequestStDate(new Timestamp(date.getTime()));
                request.setUserId(currentUser.getPhoneNumber());
                if(checkMandatoryFileds(request)) {
                    saveRequestOnFireBase();
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
            case R.id.spinner_area:
                request.setAreaId((String) parent.getSelectedItem());
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

    public void saveRequestOnFireBase(){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_URL);
        //Storing values to firebase
        ref.child("Request").setValue(request);
        //Value event listener for realtime data update
        ref.addChildEventListener(new ChildEventListener() {
        //ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                //adapter.add((String) dataSnapshot.child("title").getValue());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " requsets");
                for (com.firebase.client.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    Request request = postSnapshot.getValue(Request.class);
                    //Adding it to a string
                    String remarks = "Name: "+ request.getRemarks()+"\n\n";
                    System.out.println("Requsets remarks is " + remarks);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)  {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        Firebase fbUsers = new Firebase(Config.FIREBASE_REQUESTS_USER_URL + currentUser.getPhoneNumber().toString());

        //Storing values to firebase
        fbUsers.push().child("Request").setValue(request);

        Firebase fbAreas = new Firebase(Config.FIREBASE_REQUESTS_AREA_URL + currentUser.getAreaId().toString());
        //Storing values to firebase
        fbUsers.push().child("Request").setValue(request);


    }

    public void getUserDetailsFromFireBase(){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_USER_URL);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                userFB = (User) snapshot.child(currentUser.getPhoneNumber()).getValue();
               /* for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    BlogPost post = postSnapshot.getValue(BlogPost.class);
                    System.out.println(post.getAuthor() + " - " + post.getTitle());
                }*/
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void confirmOpenRequestDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.confirm);
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
