package com.course.mytestapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;

import com.course.mytestapp.dbs.DBHelper;
import com.course.mytestapp.dbs.MythContract;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
        moveToMyths();

    }

    private void createDB() {

        DBHelper mDbHelper = new DBHelper(getApplicationContext());
        // Gets the data repository in write mode
        db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys

        insertRow(1,"Soothe a burn by applying butter",
                "If you apply butter or an oily substance to a serious burn, you could make it difficult for a doctor to treat the burn later " +
                "and increase risk of infection.\n" +
                "The right approach: Treat a burn with cool water. If a burn is severe and starts to blister, " +
                "make sure to see a doctor. Keep the affected area clean and loosely covered with a dry, sterile dressing",
                0);
        insertRow(2,"apply an ice pack on the bruise to ease blood circulation",
                "What to do: The best home treatment is to apply an ice pack on the bruise; this will reduce the internal bleeding. The ice should not touch the" +
                        " skin directly since this can cause ice burn, similar to sunburn",
                1);
        insertRow(3,"Squeeze the stinger to treat a bee sting",
                "Fact: Squeezing the stinger will cause more toxins to flow into the bloodstream. \n" +
                 " What to do: Quickly pull out the stinger with a pair of tweezers, then apply an antihistamine cream to the affected area",
                0);
        insertRow(4," Tilt your head back to stop a nosebleed",
                "If you apply butter or an oily substance to a serious burn, you could make it difficult for a doctor to treat the burn later " +
                        "and increase risk of infection.\n" +
                        "The right approach: Treat a burn with cool water. If a burn is severe and starts to blister, " +
                        "make sure to see a doctor. Keep the affected area clean and loosely covered with a dry, sterile dressingFact: " +
                        "If you tilt your head back when you have a nosebleed, " +
                        "the blood may go into your throat and your stomach, which may lead to nausea and vomiting.\n" +
                        " \n What to do: Tilt your head forward and press the fleshy part of your nose, " +
                        "the part you would hold for a bad smell, for a full 10 minutes, while breathing through your mouth. " +
                        "If you are still bleeding after half an hour, seek emergency help.",
                0);
        insertRow(5,"Treat a black eye with a ice wrapped in a clean towel",
                "What to do: Apply ice wrapped in a clean towel to the eye area to reduce the swelling. Don’t place ice directly on the affected area since" +
                        " this can cause an ice burn, similar to a sunburn. See a doctor to check for head injuries.",
                1);


    }

    public long insertRow(Integer mythNameId, String mythTitle, String mythDesc, Integer isItTrue) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(MythContract.COLUMN_NAME_MYTH_NO, mythNameId);
        initialValues.put(MythContract.COLUMN_NAME_TITLE, mythTitle);
        initialValues.put(MythContract.COLUMN_NAME_DESC,mythDesc);
        initialValues.put(MythContract.COLUMN_NAME_ISITTRUE, isItTrue);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(MythContract.TABLE_NAME, MythContract.COLUMN_NAME_NULLABLE, initialValues);

        return newRowId;
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
