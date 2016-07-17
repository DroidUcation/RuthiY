package com.ruthiy.care2car.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.gson.Gson;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.User;
import com.ruthiy.care2car.services.GPSTracker;
import com.ruthiy.care2car.tables.TablesContract;
import com.ruthiy.care2car.utils.SharedPrefUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private Fragment mMapFragment;

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int LOCATION_PERMISSION = 583;
    Marker fromMarker = null;

    Location mLastLocation;
    TextView currentLocation;
    Gson mGson = new Gson();
    User currentUser;
    String city;
    String address;

    // GPSTracker class
    GPSTracker gps;
    boolean gpsFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildGoogleApiClient();

        currentUser = SharedPrefUtil.getUserFromSP(this);
        gpsFlag = true;
        /*ImageButton findLoc = (ImageButton)  findViewById(R.id.ib_findLocation);
        findLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
*/      
        Button showRequests = (Button) findViewById(R.id.bn_showMyRequests);
        showRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowRequestsActivity.class);
                intent.putExtra("type", "request");
                if (intent != null) startActivity(intent);
            }
        });

        Button showVolunteering = (Button) findViewById(R.id.bn_showMyVolunteering);
        showVolunteering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowRequestsActivity.class);
                intent.putExtra("type", "volunteer");
                if (intent != null) startActivity(intent);
            }
        });


        Button button = (Button) findViewById(R.id.bn_ask4help);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLastLocation != null) {
                    Intent intent = new Intent(MainActivity.this, OpenRequest.class);
                    currentUser.setLocation(mLastLocation);
                    ArrayList<User> list = new ArrayList<User>();
                    list.add(currentUser);
                    intent.putParcelableArrayListExtra("user", list);
                    intent.putExtra("area", city);
                    intent.putExtra("address", address + ", " + city);
                    intent.putExtra("longitude", mLastLocation.getLongitude());
                    intent.putExtra("latitude", mLastLocation.getLatitude());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    if (intent != null) startActivity(intent);
                }
                else{
                    buildAlertMessageNoGps();
                }
            }
        });

        currentLocation = (TextView) findViewById(R.id.tv_currentLocation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getAddressByLocation() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
            if (addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                currentLocation.setText(address + ", " + city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeContactDetailsDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to change your contact details? ");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION);
        } else {
            getLocation();
        }
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                fromMarker = drawLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), "You are here!");
                Geocoder geocoder;
                List<Address> addresses;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    geocoder = new Geocoder(this, Locale.forLanguageTag("en-us"));
                } else {
                    geocoder = new Geocoder(this, Locale.getDefault());
                }

                try {
                    addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        address = addresses.get(0).getAddressLine(0);
                        city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();
                        currentLocation.setText(address + ", " + city);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if(gpsFlag) {
                buildAlertMessageNoGps();
                gpsFlag = false;

            }
        }
    }

    private Marker drawLocation(double latitude, double longitude, String title) {
        LatLng loc = new LatLng(latitude, longitude);
        Marker m = mMap.addMarker(new MarkerOptions().position(loc).title(title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
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

    /*private  void turnGPSOn(){
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);

    }
    private void turnGPSOff(){
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", false);
        sendBroadcast(intent);}*/
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                    }
                });
        if (gpsFlag) {
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                    dialog.cancel();
                }
            }).setCancelable(true);
        }
        else {
            builder.setNeutralButton("GPS is ON", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                  getLocation();
                }
            }).setCancelable(false);
        }
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //mGoogleApiClient.connect();
        getLocation();
    }


    public void testContentProvider(){
        String [] requestedColumns = { TablesContract.Request.COLUMN_CATEGORY_ID,  TablesContract.Request.COLUMN_AREA_ID};

        Integer userId = 3;
        Cursor requests = getApplicationContext().getContentResolver().query(
                TablesContract.Request.CONTENT_URI,
                requestedColumns,
                TablesContract.Request.COLUMN_USER_NAME + "='" + userId + "'",
                null, null);

        // A cursor is your primary interface to the query results.
        Cursor cursor = getApplicationContext().getContentResolver().query(
                TablesContract.Request.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );
    }

}
