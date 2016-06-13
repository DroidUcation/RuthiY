package com.ruthiy.care2car.utils;

import android.text.format.Time;

/**
 * Created by Ruthi.Y on 6/8/2016.
 */
public class DateUtils {
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        return time.setJulianDay(Time.getJulianDay(startDate, time.gmtoff));
    }
}
