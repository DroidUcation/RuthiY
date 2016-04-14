package com.course.mytestapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mythNum = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_aid_list);

        findAllViews();
        updateMythText();
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
        imageIdNo = getResources().getIdentifier("no", "drawable", getPackageName());
        imageIdYes = getResources().getIdentifier("yes", "drawable", getPackageName());
        or = (TextView) findViewById(R.id.or);


    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        int id = v.getId();

        switch (id)
        {
            case R.id.rightImg : /*Toast.makeText(v.getContext(), "ruthi 0 ", Toast.LENGTH_SHORT).show();*/
                resIdYN = (String) getResources().getText(this.getResources().getIdentifier("trueOrfalse" + mythNum, "string", this.getPackageName().toString()));
                if (resIdYN.compareTo("true") == 0) {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdNo));
                } else {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdYes));
                }
                updateLayoutParams(false);
                showMythDesc();
                v.setClickable(true);
                break;
            case R.id.wrongImg:
                resIdYN = (String) getResources().getText(this.getResources().getIdentifier("trueOrfalse" + mythNum, "string", this.getPackageName().toString()));
                if (resIdYN.compareTo("false") == 0) {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdNo));
                } else {
                    resultImg.setImageDrawable(getResources().getDrawable(imageIdYes));
                }
                updateLayoutParams(false);
                showMythDesc();
                v.setClickable(true);
                break;
            case R.id.mythPrev:
                if (mythNum.equals(1)) {
                    Log.i("FirstAidactivity", "This is the first Myth");
                    Toast.makeText(v.getContext(),"This is the first Myth",Toast.LENGTH_SHORT).show();
                }
                else{
                    mythNum--;
                    updateMythText();
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
                    updateMythText();
                    updateLayoutParams(true);
                }
                break;
        }
        Log.d("== My activity ===", "OnClick is called");
    }


    private void updateMythText() {
        myth.setText(getResources().getText(this.getResources().getIdentifier("myth" + mythNum, "string", this.getPackageName().toString())));

    }

    private void showMythDesc() {
        mythDesc.setText(getResources().getText(this.getResources().getIdentifier("mythDesc" + mythNum, "string", this.getPackageName())));
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
    }

    private LayoutParams setWeight(View mythDesc, float weight) {
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) mythDesc.getLayoutParams();
        p.weight = weight;
        return p;
    }

}
