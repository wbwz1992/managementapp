package com.study.robin.managementapp;

import java.io.Serializable;

/**
 * Created by robin on 2016/5/14.
 */
public class Class implements Serializable {

    private String mId;
    private String mDepartment;
    private String mMajor;
    private String mClass;
    private int mCount;
    private String mMonitor;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmMajor() {
        return mMajor;
    }

    public void setmMajor(String mMajor) {
        this.mMajor = mMajor;
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

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }

    public String getmMonitor() {
        return mMonitor;
    }

    public void setmMonitor(String mMonitor) {
        this.mMonitor = mMonitor;
    }
}
