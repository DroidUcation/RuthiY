package com.ruthiy.care2car.entities;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */

/* R_STATUS */

public class Status {
    long _id;
    String  statusDesc;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    @Override
    public String toString() {
        return "Status{" +
                "_id=" + _id +
                ", statusDesc='" + statusDesc + '\'' +
                '}';
    }


}
