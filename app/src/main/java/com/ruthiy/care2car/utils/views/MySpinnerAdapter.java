package com.ruthiy.care2car.utils.views;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ruthi.Y on 7/3/2016.
 */
public class MySpinnerAdapter extends ArrayAdapter<String> {
    // Initialise custom font, for example:
    // (In reality I used a manager which caches the Typeface objects)
    // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

    public MySpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/openSans/OpenSans-Light.ttf"));
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/openSans/OpenSans-Light.ttf"));
        return view;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count>0 ? count-1 : count ;        }
}