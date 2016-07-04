package com.ruthiy.care2car.utils.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Ruthi.Y on 7/3/2016.
 */
public class MyEditText extends EditText {


    public MyEditText(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(),  "fonts/openSans/OpenSans-Light.ttf");
        this.setTypeface(face);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(),  "fonts/openSans/OpenSans-Light.ttf");
        this.setTypeface(face);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(),  "fonts/openSans/OpenSans-Light.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}