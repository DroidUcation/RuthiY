package com.ruthiy.care2car.entities;

import java.sql.Date;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */

/* R_REQUEST_HIS */

public class RequestHis {

    long requestId ;
    long requestStatusId ;
    String remarks ;
    Date updateDate ;
    Date updateUser ;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Date updateUser) {
        this.updateUser = updateUser;
    }
}
