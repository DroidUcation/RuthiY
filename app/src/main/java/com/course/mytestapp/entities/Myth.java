package com.course.mytestapp.entities;

/**
 * Created by Ruthi.Y on 5/10/2016.
 */
public class Myth {

    private Integer mythNo;
    private String mythTitle;
    private String mythDesc;
    private boolean isItTrue;

    public Myth() {
    }

    public Myth(Integer mythNo, String mythTitle, String mythDesc, boolean isItTrue) {
        this.mythNo = mythNo;
        this.mythTitle = mythTitle;
        this.mythDesc = mythDesc;
        this.isItTrue = isItTrue;
    }

    public Integer getMythNo() {
        return mythNo;
    }

    public void setMythNo(Integer mythNo) {
        this.mythNo = mythNo;
    }

    public String getMythTitle() {
        return mythTitle;
    }

    public void setMythTitle(String mythTitle) {
        this.mythTitle = mythTitle;
    }

    public String getMythDesc() {
        return mythDesc;
    }

    public void setMythDesc(String mythDesc) {
        this.mythDesc = mythDesc;
    }

    public boolean isItTrue() {
        return isItTrue;
    }

    public void setIsItTrue(boolean isItTrue) {
        this.isItTrue = isItTrue;
    }
}
