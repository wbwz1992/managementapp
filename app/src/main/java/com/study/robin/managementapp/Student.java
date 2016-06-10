package com.study.robin.managementapp;

import java.io.Serializable;

/**
 * Created by robin on 2016/5/12.
 */
public class Student implements Serializable {

    private String mId;
    private String mName;
    private String mSex;
    private String mDepartment;
    private String mMajor;
    private String mClass;
    private String mPosition;

    public String getmClassId() {
        return mClassId;
    }

    public void setmClassId(String mClassId) {
        this.mClassId = mClassId;
    }

    private String mClassId;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmSex() {
        return mSex;
    }

    public void setmSex(String mSex) {
        this.mSex = mSex;
    }

    public String getmDepartment() {
        return mDepartment;
    }

    public void setmDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public String getmMajor() {
        return mMajor;
    }

    public void setmMajor(String mMajor) {
        this.mMajor = mMajor;
    }

    public String getmPosition() {
        return mPosition;
    }

    public void setmPosition(String mPosition) {
        this.mPosition = mPosition;
    }
}
