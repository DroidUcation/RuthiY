package com.ruthiy.care2car.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.services.SendMessages;
import com.ruthiy.care2car.utils.Config;
import com.ruthiy.care2car.utils.SharedPrefUtil;

public class ViewRequestActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    Request requestFB;
    TextView tvlocation ;
    TextView tvProblemDescription;
    TextView tvName ;
    TextView tvphone ;
    Location userLocation;
    String requestKey;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int LOCATION_PERMISSION = 583;
    Marker fromMarker = null;
    Marker userMarker = null;

    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        buildGoogleApiClient();
        Bundle  b = this.getIntent().getExtras();

         tvlocation = (TextView) findViewById(R.id.location);
         tvProblemDescription = (TextView) findViewById(R.id.problemDescription);
         tvName = (TextView) findViewById(R.id.name);
         tvphone = (TextView) findViewById(R.id.phone);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        if (b.containsKey("request")) {
            requestKey = b.getString("request");
          if (getRequestFromFireBase(requestKey)){
              Log.d("ViewRequestActivity" , " --> request is downloaded");
          }
        }

       /* if (b.containsKey("userLocation")) {
            ArrayList<Location> locations = getIntent().getParcelableArrayListExtra("userLocation");
            for(Location userLocations : locations) {
                Log.i("", userLocations.toString());
                userLocation = userLocations;
            }
        }*/
        final Button bnCall = (Button) findViewById(R.id.bn_call);
        final Button bnNavigate = (Button) findViewById(R.id.bn_navigate);


        final Button declineRequset = (Button) findViewById(R.id.bn_declineRequset);
        declineRequset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmCancelRequestDialog();;
                }
        });

        final Button acceptRequset = (Button) findViewById(R.id.bn_acceptRequset);
        acceptRequset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bnCall.setVisibility(View.VISIBLE);
                bnNavigate.setVisibility(View.VISIBLE);
                declineRequset.setVisibility(View.INVISIBLE);
                acceptRequset.setVisibility(View.VISIBLE);
                saveRequestOnFireBase();
                String userKey = SharedPrefUtil.getUserKeyFromSP(getBaseContext());
                new SendMessages().execute("https://fcm.googleapis.com/fcm/send", requestFB.getUserToken(), userKey, "true");
            }
        });

        bnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = tvphone.getText().toString();
                Intent dialIntent= new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+ phoneNumber));
                if (dialIntent != null) startActivity(dialIntent);
            }
        });

        bnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?"  +
                        "&daddr=" + String.valueOf(requestFB.getLatitude()) + ","
                        + String.valueOf(requestFB.getLongitude()));

                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                // Attempt to start an activity that can handle the Intent
                try {
                    startActivity(mapIntent);
                }catch (ActivityNotFoundException e){
                    Log.d("ViewRequestActivity", "No Activity found to handle Intent") ;
                    noNavigationAppDialog();
                }

            }
        });

    }

    public void confirmCancelRequestDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
       /* new AlertDialog.Builder(this).setTitle("Argh").setMessage("Watch out!").setIcon(R.drawable.icon).setNeutralButton("Close", null).show();
        */

        alertDialogBuilder.setTitle("  Care2Car");
        alertDialogBuilder.setMessage("Thanks! maybe next time :) ");
        alertDialogBuilder.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void noNavigationAppDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
       /* new AlertDialog.Builder(this).setTitle("Argh").setMessage("Watch out!").setIcon(R.drawable.icon).setNeutralButton("Close", null).show();
        */

        alertDialogBuilder.setTitle("  Care2Car");
        alertDialogBuilder.setMessage("Navigation Application not found on this device ");
        alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    public boolean getRequestFromFireBase(String requestKey){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_REQUESTS_URL);
        ref.child(requestKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                requestFB = snapshot.getValue(Request.class);
                tvlocation = (TextView) findViewById(R.id.location);
                tvProblemDescription = (TextView) findViewById(R.id.problemDescription);
                tvName = (TextView) findViewById(R.id.name);
                tvphone = (TextView) findViewById(R.id.phone);
                userLocation = new Location(" User Location");
                //Location targetLocation = new Location("");//provider name is unecessary
                userLocation.setLatitude(requestFB.getLatitude());//your coords of course
                userLocation.setLongitude(requestFB.getLongitude());

                //float distanceInMeters =  targetLocation.distanceTo(myLocation);

                tvName.setText(requestFB.getUserName());
                tvphone.setText(requestFB.getUserPhone());
                String ProblemDescription = requestFB.getCategoryId();
                ProblemDescription= ProblemDescription.concat(", Car Type; ").concat(requestFB.getCarTypeId());
                ProblemDescription= ProblemDescription.concat(", Remarks; ").concat(requestFB.getRemarks());
                tvProblemDescription = (TextView) findViewById(R.id.problemDescription);
                tvProblemDescription.setText(ProblemDescription);
                tvlocation.setText(requestFB.getLocation());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return requestFB!=null;
    }

    public void saveRequestOnFireBase(){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_REQUESTS_URL);
        //Storing values to firebase
        String userKeyFromSP = SharedPrefUtil.getUserKeyFromSP(this);
        if (userKeyFromSP != null) {
            requestFB.setVolunteerId(userKeyFromSP);
            requestFB.setRequestKey(requestKey);
        }

        Log.d("ViewRequestActivity", "saveRequestOnFireBase: "+requestFB.getVolunteerId());

        Firebase fbUsers = new Firebase(Config.FIREBASE_REQUESTS_VOLUNTEER_URL + userKeyFromSP);
        //Storing values to firebase
        fbUsers = fbUsers.push();
        fbUsers.setValue(requestFB);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();/*
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.care2car);*/
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          //  mGoogleApiClient.disconnect();
           // requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
       // } else {
            getLocation();
       // }
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (userLocation != null) {
                userMarker = drawLocation(userLocation.getLatitude(), userLocation.getLongitude(), "user is here");
            }
            if (mLastLocation != null) {
                fromMarker = drawLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), "You are here!");
            }

        }
    }

    private Marker drawLocation(double latitude, double longitude, String title) {
        LatLng loc = new LatLng(latitude, longitude);
        Marker m = mMap.addMarker(new MarkerOptions().position(loc).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        return m;
    }

    private void removeLocation(Marker marker) {
        if (marker != null)
            marker.remove();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    SetDefaultLocation();
                }
                return;
            }

        }
    }

    private void SetDefaultLocation() {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
    }

}
