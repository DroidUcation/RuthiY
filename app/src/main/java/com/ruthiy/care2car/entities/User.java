package com.ruthiy.care2car.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.ruthiy.care2car.tables.TablesContract;

import java.io.Serializable;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */
/* B_USER*/
public class User implements Parcelable /*extends SugarRecord*/{


    String userKey ;
    String name ;
    String userToken ;
    String phoneNumber;
    String password;
    String areaId;
    Location location;
    public User(){};
    public User(Parcel in) {
        userKey = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        password = in.readString();
        areaId = in.readString();
        userToken = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserToken() {return userToken; }

    public void setUserToken(String userToken) {this.userToken = userToken; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userKey);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(userToken);
        dest.writeString(password);
        dest.writeString(areaId);
        dest.writeParcelable(location, flags);
    }

    public static ContentValues getForInsert(User user){
        ContentValues initialValues = new ContentValues();

        initialValues.put(TablesContract.User.COLUMN_AREA_ID, user.getAreaId());
        initialValues.put(TablesContract.User.COLUMN_NAME, user.getName());
       /* initialValues.put(TablesContract.User.COLUMN_LOCATION_LATITUDE, this.getLocation().getLatitude());
        initialValues.put(TablesContract.User.COLUMN_LOCATION_LONGITUDE, this.getLocation().getLongitude());*/
        initialValues.put(TablesContract.User.COLUMN_LOCATION, user.getLocation()!=null? user.getLocation().toString():"");
        initialValues.put(TablesContract.User.COLUMN_PASSWORD, user.getPassword());
        initialValues.put(TablesContract.User.COLUMN_PHONE_NUMBER, user.getPhoneNumber());

        Log.i("entities.User:", "getForInsert called");
        return initialValues;
    }

    public static User getFromDB(Cursor c){
        User user = new User();
            if (c != null && c.moveToFirst()) {
            /*do{*/
            user.setUserKey(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_USER_KEY)));
            user.setName(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_NAME)));
            user.setAreaId(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_AREA_ID)));
            user.setPassword(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_PASSWORD)));
            user.setPhoneNumber(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_PHONE_NUMBER)));
            /*double latitude = Double.parseDouble(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_LOCATION_LATITUDE)));
            double longitude= Double.parseDouble(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_LOCATION_LONGITUDE)));*/
            Location location = new Location(c.getString(c.getColumnIndex(TablesContract.User.COLUMN_LOCATION)));
            user.setLocation(location);
           /* }
            while (c.moveToNext());*/
        }
        c.close();
        return user;
    }

}
