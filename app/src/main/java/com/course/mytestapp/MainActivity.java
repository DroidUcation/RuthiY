package com.course.mytestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moveToMyths();

    }
    private void moveToMyths() {

        Log.i("MainActivity", " I am in moveToMyths method");
        final Context context = this;

/*        Intent intent = new Intent(context, FirstAidactivity.class);
        startActivity(intent);*/


        Button button = (Button) findViewById(R.id.next_button);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, FirstAidactivity.class);
                startActivity(intent);

            }

        });
    }


}
