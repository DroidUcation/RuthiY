package com.ruthiy.care2car.utils.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ruthi.Y on 7/3/2016.
 */
public class MyTextViewBold extends TextView {


    public MyTextViewBold(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(),  "fonts/openSans/OpenSans-Light.ttf");
        this.setTypeface(face, Typeface.BOLD);
    }

    public MyTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(),  "fonts/openSans/OpenSans-Light.ttf");
        this.setTypeface(face, Typeface.BOLD);
    }

    public MyTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, Typeface.BOLD);
        Typeface face=Typeface.createFromAsset(context.getAssets(),  "fonts/openSans/OpenSans-Light.ttf");
        this.setTypeface(face, Typeface.BOLD);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}
