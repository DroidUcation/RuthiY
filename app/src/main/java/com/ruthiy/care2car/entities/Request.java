package com.ruthiy.care2car.entities;

import java.sql.Date;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */

/* B_REQUEST */

public class Request {
    long userId ;
    long areaId ;
    String location ;
    long categoryId ;
    long engineVolumeId ;
    Date requestStDate ;
    Date requestEndDate ;
    long requestStatusId ;
    long volunteerId ; //fk user table
    String remarks ;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAreaId() {
        return areaId;
    }

    public void setAreaId(long areaId) {
        this.areaId = areaId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getEngineVolumeId() {
        return engineVolumeId;
    }

    public void setEngineVolumeId(long engineVolumeId) {
        this.engineVolumeId = engineVolumeId;
    }

    public Date getRequestStDate() {
        return requestStDate;
    }

    public void setRequestStDate(Date requestStDate) {
        this.requestStDate = requestStDate;
    }

    public Date getRequestEndDate() {
        return requestEndDate;
    }

    public void setRequestEndDate(Date requestEndDate) {
        this.requestEndDate = requestEndDate;
    }

    public long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public long getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(long requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
