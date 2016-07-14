package com.ruthiy.care2car.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.ruthiy.care2car.tables.TablesContract;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */

/* B_REQUEST */

public class Request implements Serializable {

    String userName ;
    String userPhone ;
    String userToken ;
    String areaId ;
    double latitude ;
    double longitude ;
    String location ;
    String categoryId ;
    String engineVolumeId ;
    String carTypeId ;
    Timestamp requestStDate ;
    Timestamp requestEndDate ;
    String requestStatusId ;
    String requestKey ;
    long volunteerId ; //fk user table
    String remarks ;

    public Request() {

    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getEngineVolumeId() {
        return engineVolumeId;
    }

    public void setEngineVolumeId(String engineVolumeId) {
        this.engineVolumeId = engineVolumeId;
    }

    public Timestamp getRequestStDate() {
        return requestStDate;
    }

    public void setRequestStDate(Timestamp requestStDate) {
        this.requestStDate = requestStDate;
    }

    public Timestamp getRequestEndDate() {
        return requestEndDate;
    }

    public void setRequestEndDate(Timestamp requestEndDate) {
        this.requestEndDate = requestEndDate;
    }

    public long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public String getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(String requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCarTypeId() { return carTypeId; }

    public void setCarTypeId(String carTypeId) {this.carTypeId = carTypeId;    }

    public String getUserName() {return userName;}

    public void setUserName(String userName) {this.userName = userName; }

    public String getUserPhone() {return userPhone; }

    public void setUserPhone(String userPhone) {this.userPhone = userPhone; }

    public String getUserToken() {return userToken; }

    public void setUserToken(String userToken) {this.userToken = userToken; }

    public String getRequestKey() { return requestKey;}

    public void setRequestKey(String requestKey) {this.requestKey = requestKey;}

    public double getLatitude() { return latitude;}

    public void setLatitude(double latitude) { this.latitude = latitude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public static ContentValues getForInsert(Request request){
        ContentValues initialValues = new ContentValues();

        initialValues.put(TablesContract.Request.COLUMN_AREA_ID, request.getAreaId());
        initialValues.put(TablesContract.Request.COLUMN_CATEGORY_ID, request.getCategoryId());
        initialValues.put(TablesContract.Request.COLUMN_ENGINE_VOLUME_ID, request.getEngineVolumeId());
        initialValues.put(TablesContract.Request.COLUMN_LOCATION_LATITUDE, request.getLongitude());
        initialValues.put(TablesContract.Request.COLUMN_LOCATION_LONGITUDE, request.getLatitude());
        initialValues.put(TablesContract.Request.COLUMN_LOCATION, request.getLocation());
        initialValues.put(TablesContract.Request.COLUMN_REMARKS, request.getRemarks());
        initialValues.put(TablesContract.Request.COLUMN_REQUEST_END_DATE,
                request.getRequestEndDate() != null ? request.getRequestEndDate().toString(): null);
        initialValues.put(TablesContract.Request.COLUMN_REQUEST_ST_DATE,
                request.getRequestStDate()!=null ? request.getRequestStDate().toString(): null);
        initialValues.put(TablesContract.Request.COLUMN_REQUEST_STATUS_ID, request.getRequestStatusId());
        initialValues.put(TablesContract.Request.COLUMN_USER_NAME, request.getUserName());
        initialValues.put(TablesContract.Request.COLUMN_USER_PHONE, request.getUserPhone());
        initialValues.put(TablesContract.Request.COLUMN_USER_TOKEN, request.getUserToken());
        initialValues.put(TablesContract.Request.COLUMN_VOLUNTEER_ID, request.getVolunteerId());

        Log.i("entities.Request:", "getForInsert called");
        return initialValues;
    }

    public static Request getObjectByCursor(Cursor cursor){
        Request request = new Request();
        //cursor = getContentResolver().query(MythContract.buildMythNo(mythNum), null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            request.setLocation(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_LOCATION)));
            request.setLongitude(Double.parseDouble(
                    cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_LOCATION_LONGITUDE))));
            request.setLatitude(Double.parseDouble(
                    cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_LOCATION_LATITUDE))));
            request.setAreaId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_AREA_ID)));
            request.setCategoryId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_CATEGORY_ID)));
            request.setEngineVolumeId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_ENGINE_VOLUME_ID)));
            request.setRequestStDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_REQUEST_ST_DATE))));
            request.setRequestStatusId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_REQUEST_STATUS_ID)));
            request.setRemarks(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_REMARKS)));
            request.setUserName(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_USER_NAME)));
            request.setUserPhone(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_USER_PHONE)));
            request.setUserToken(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_USER_TOKEN)));
            request.setRequestKey(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_REQUEST_KEY)));
        }
        cursor.close();
        Log.i("getObjectById .Request:", "getObjectByCursor called");
        return request;
    }
}
