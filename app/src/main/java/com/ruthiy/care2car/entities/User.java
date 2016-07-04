package com.ruthiy.care2car.entities;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */
/* B_USER*/
public class User implements Parcelable /*extends SugarRecord*/{


    String _id ;
    String name ;
    String phoneNumber;
    String password;
    String areaId;
    Location location;
    public User(){};
    public User(Parcel in) {
        _id = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        password = in.readString();
        areaId = in.readString();
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(password);
        dest.writeString(areaId);
        dest.writeParcelable(location, flags);
    }
}
