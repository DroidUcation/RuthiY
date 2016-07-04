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
    String userId ;
    String areaId ;
    String location ;
    String categoryId ;
    String engineVolumeId ;
    String carTypeId ;
    Timestamp requestStDate ;
    Timestamp requestEndDate ;
    String requestStatusId ;
    long volunteerId ; //fk user table
    String remarks ;

    public Request() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    /*public ContentValues getForInsert(){
        ContentValues initialValues = new ContentValues();

        initialValues.put(TablesContract.Request.COLUMN_AREA_ID, this.getAreaId());
        initialValues.put(TablesContract.Request.COLUMN_CATEGORY_ID, this.getCategoryId());
        initialValues.put(TablesContract.Request.COLUMN_ENGINE_VOLUME_ID, this.getEngineVolumeId());
        initialValues.put(TablesContract.Request.COLUMN_LOCATION, this.getLocation());
        initialValues.put(TablesContract.Request.COLUMN_REMARKS, this.getRemarks());
        initialValues.put(TablesContract.Request.COLUMN_REQUEST_END_DATE,
                this.getRequestEndDate() != null ? this.getRequestEndDate().toString(): null);
        initialValues.put(TablesContract.Request.COLUMN_REQUEST_ST_DATE,
                this.getRequestStDate()!=null ? this.getRequestStDate().toString(): null);
        initialValues.put(TablesContract.Request.COLUMN_REQUEST_STATUS_ID, this.getRequestStatusId());
        initialValues.put(TablesContract.Request.COLUMN_USER_ID, this.getUserId());
        initialValues.put(TablesContract.Request.COLUMN_VOLUNTEER_ID, this.getVolunteerId());

        Log.i("entities.Request:", "getForInsert called");
        return initialValues;
    }*/

    public static Request getObjectByCursor(Cursor cursor){
        Request request = new Request();
        //cursor = getContentResolver().query(MythContract.buildMythNo(mythNum), null, null, null, null);
        cursor.moveToFirst();

        request.setLocation(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_LOCATION)));
        request.setAreaId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_AREA_ID)));
        request.setCategoryId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_CATEGORY_ID)));
        request.setEngineVolumeId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_ENGINE_VOLUME_ID)));
        request.setRequestStDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_REQUEST_ST_DATE))));
        request.setRequestStatusId(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_REQUEST_STATUS_ID)));
        request.setRemarks(cursor.getString(cursor.getColumnIndex(TablesContract.Request.COLUMN_REMARKS)));

        Log.i("getObjectById .Request:", "getForInsert called");
        return request;
    }
}
