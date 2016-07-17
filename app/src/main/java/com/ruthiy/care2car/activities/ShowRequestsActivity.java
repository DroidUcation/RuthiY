package com.ruthiy.care2car.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.ruthiy.care2car.R;
import com.ruthiy.care2car.entities.Request;
import com.ruthiy.care2car.utils.Config;
import com.ruthiy.care2car.utils.SharedPrefUtil;
import com.ruthiy.care2car.utils.views.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShowRequestsActivity extends AppCompatActivity {
    private List<Request> requestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView emptyView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_requests);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        emptyView = (TextView) findViewById(R.id.empty_view);
        mAdapter = new MyAdapter(requestList, getBaseContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
      /*  recyclerView.setItemAnimator(new DefaultItemAnimator());*/
        recyclerView.setAdapter(mAdapter);

        Bundle  b = this.getIntent().getExtras();
        String type = "";
        if (b.containsKey("type")) {
            type = b.getString("type");
        }
        prepareRequestData(type);
    }

    private void prepareRequestData(String type) {
        getRequestFromFireBase(type);
    }

    public void getRequestFromFireBase(String type){
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase(type.equals("request")? Config.FIREBASE_REQUESTS_USER_URL : Config.FIREBASE_REQUESTS_VOLUNTEER_URL);
        ref.child(SharedPrefUtil.getUserKeyFromSP(this)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Request request = postSnapshot.getValue(Request.class);
                    System.out.println(postSnapshot.getKey() + " - " + request.getCategoryId());
                    requestList.add(request);
                }
                if(requestList.size()>0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    mAdapter.notifyDataSetChanged();
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
