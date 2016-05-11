package com.course.mytestapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.course.mytestapp.dbs.MythContract;

import static com.course.mytestapp.dbs.MythContract.COLUMN_NAME_TITLE;

public class FirstAidactivity extends AppCompatActivity implements View.OnClickListener {

    Integer mythNum = 0;
    String resIdYN = "";
    TextView myth;
    TextView mythDesc;
    TextView or;
    ImageView rightImg;
    ImageView wrongImg;
    ImageView resultImg;
    Button next;
    Button prev;
    int imageIdNo;
    int imageIdYes;
    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mythNum = 1;

        cursor = getContentResolver().query(MythContract.CONTENT_URI, null, null, null, null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_aid_list);

        findAllViews();
        updateMythText(mythNum);
        updateLayoutParams(true);
        wrongImg.setOnClickListener(this);
        rightImg.setOnClickListener(this);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        Toast.makeText(getApplicationContext(), "Click 'RIGHT' if u think it is correct and 'WRONG' if not", Toast.LENGTH_LONG).show();

 }
    private void findAllViews() {
        myth = (TextView) findViewById(R.id.myth);
        mythDesc = (TextView) findViewById(R.id.mythDesc);
        resultImg = (ImageView) findViewById(R.id.resultImg);
        rightImg = (ImageView) findViewById(R.id.rightImg);
        wrongImg = (ImageView) findViewById(R.id.wrongImg);
        next = (Button) findViewById(R.id.mythNext);
        prev = (Button) findViewById(R.id.mythPrev);
        imageIdNo = R.drawable.no;
        imageIdYes =  R.drawable.yes;
        or = (TextView) findViewById(R.id.or);


    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        int id = v.getId();
        cursor.moveToPosition(mythNum-1);
        //cursor = getContentResolver().query(MythContract.buildMythNo(mythNum), null, null, null, null);
        //cursor.moveToFirst();
        resIdYN = cursor.getString(cursor.getColumnIndex(MythContract.COLUMN_NAME_ISITTRUE));

        switch (id)
        {
            case R.id.rightImg : /*Toast.makeText(v.getContext(), "ruthi 0 ", Toast.LENGTH_SHORT).show();*/
                if (resIdYN.compareTo("1") == 0) {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdNo));
                } else {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdYes));
                }
                updateLayoutParams(false);
                showMythDesc(mythNum);
                v.setClickable(true);
                break;
            case R.id.wrongImg:
                //resIdYN = (String) getResources().getText(this.getResources().getIdentifier("trueOrfalse" + mythNum, "string", this.getPackageName().toString()));
                if (resIdYN.compareTo("0") == 0) {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdNo));
                } else {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdYes));
                }
                updateLayoutParams(false);
                showMythDesc(mythNum);
                v.setClickable(true);
                break;
            case R.id.mythPrev:
                if (mythNum.equals(1)) {
                    Log.i("FirstAidactivity", "This is the first Myth");
                    Toast.makeText(v.getContext(),"This is the first Myth",Toast.LENGTH_SHORT).show();
                }
                else{
                    mythNum--;
                    updateMythText(mythNum);
                    updateLayoutParams(true);

                }
                break;
            case R.id.mythNext:
                if (mythNum.equals(5)) {
                    Toast.makeText(this, "This is the LAST Myth", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    mythNum++;
                    updateMythText(mythNum);
                    updateLayoutParams(true);
                }
                break;
        }
        Log.d("== My activity ===", "OnClick is called");
    }


    private void updateMythText(Integer mythNum) {
        cursor.moveToPosition(mythNum-1);
        //cursor = getContentResolver().query(MythContract.buildMythNo(mythNum), null, null, null, null);
        //cursor.moveToFirst();
        myth.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TITLE)));
        //myth.setText(getResources().getText(this.getResources().getIdentifier("myth" + this.mythNum, "string", this.getPackageName().toString())));

    }

    private void showMythDesc(Integer mythNum) {
        cursor.moveToPosition(mythNum-1);
        //cursor = getContentResolver().query(MythContract.buildMythNo(mythNum), null, null, null, null);
        //cursor.moveToFirst();
        mythDesc.setText(cursor.getString(cursor.getColumnIndex(MythContract.COLUMN_NAME_DESC)));

        //mythDesc.setText(getResources().getText(this.getResources().getIdentifier("mythDesc" + mythNum, "string", this.getPackageName())));
    }

    private void updateLayoutParams(boolean onCreate) {


        if (onCreate) {
            mythDesc.setLayoutParams(setWeight(mythDesc, 0.0f));
            rightImg.setLayoutParams(setWeight(rightImg, 1.0f));
            wrongImg.setLayoutParams(setWeight(wrongImg, 1.0f));
            resultImg.setLayoutParams(setWeight(resultImg, 0.0f));
            or.setLayoutParams(setWeight(or, 1.0f));
        } else {
            mythDesc.setLayoutParams(setWeight(mythDesc, 2.0f));
            mythDesc.setMovementMethod(new ScrollingMovementMethod());
            rightImg.setLayoutParams(setWeight(rightImg, 0.0f));
            wrongImg.setLayoutParams(setWeight(wrongImg, 0.0f));
            resultImg.setLayoutParams(setWeight(resultImg, 1.0f));
            or.setLayoutParams(setWeight(or, 0.0f));

        }
        prev.setClickable(mythNum.equals(1) ? false : true);
        next.setClickable(mythNum.equals(5) ? false : true);
        //prev.setTextColor(prev.getShadowColor());
    }

    private LayoutParams setWeight(View mythDesc, float weight) {
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) mythDesc.getLayoutParams();
        p.weight = weight;
        return p;
    }

}
