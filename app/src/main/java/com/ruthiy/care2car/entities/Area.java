package com.ruthiy.care2car.entities;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */

/*R_AREA */
public class Area {

    long _id;
    String  areaDesc;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAreaDesc() {
        return areaDesc;
    }

    public void setAreaDesc(String areaDesc) {
        this.areaDesc = areaDesc;
    }
}
