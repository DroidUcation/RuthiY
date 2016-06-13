package com.ruthiy.care2car.entities;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */

/* R_EMERGENCY_PHONE */

public class EmergencyPhone {
    long _id;
    String  contactName;
    String  phoneNumber;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
