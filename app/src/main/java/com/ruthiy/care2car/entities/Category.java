package com.ruthiy.care2car.entities;

/**
 * Created by Ruthi.Y on 6/13/2016.
 */

/*  R_CATEGORY */

public class Category {

    long _id;
    String  categoryDesc;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }
}
