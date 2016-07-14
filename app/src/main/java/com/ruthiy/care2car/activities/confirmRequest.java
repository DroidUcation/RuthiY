package com.ruthiy.care2car.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.entities.User;
import com.ruthiy.care2car.utils.Config;
import com.ruthiy.care2car.utils.sharedPreferencesUtil;

public class confirmRequest extends AppCompatActivity {

    User volunteerUser;
    TextView tvUserName ;
    TextView tvVolunteerName ;
    TextView tvVolunteerPhone ;
    User userFB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_request);

        Bundle  b = this.getIntent().getExtras();

        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvVolunteerName = (TextView) findViewById(R.id.tvVolunteerName);
        tvVolunteerPhone = (TextView) findViewById(R.id.tvVolunteerPhone);
        final Button bnCall = (Button) findViewById(R.id.bn_call);
        if (b.containsKey("userKey")) {
            String requestKey = b.getString("userKey");
            if(getVolunteerUserFromFireBase(requestKey));
            if(getUserDetailsFromFireBase());
        }

        bnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = tvVolunteerPhone.getText().toString();
                phoneNumber = phoneNumber.substring(8);
                Intent dialIntent= new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+ phoneNumber));
                if (dialIntent != null) startActivity(dialIntent);
            }
        });
    }

    public boolean getVolunteerUserFromFireBase(String userKey){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_USER_URL);
        ref.child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                volunteerUser = snapshot.getValue(User.class);


                tvVolunteerName.setText( volunteerUser.getName()+ tvVolunteerName.getText());
                tvVolunteerPhone.setText(tvVolunteerPhone.getText()+ volunteerUser.getPhoneNumber());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return volunteerUser!=null;
    }
    public boolean getUserDetailsFromFireBase(){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(Config.FIREBASE_USER_URL);
        ref.child(sharedPreferencesUtil.getUserKeyFromSP(this)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                userFB = snapshot.getValue(User.class);
                tvUserName.setText(tvUserName.getText()+ userFB.getName());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        return (userFB != null);
    }
}
