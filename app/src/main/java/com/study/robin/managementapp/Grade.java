package com.study.robin.managementapp;

import java.io.Serializable;

/**
 * Created by robin on 2016/6/5.
 */
public class Grade implements Serializable {
    private String mName;
    private String mId;
    private String mGrade;

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmGrade() {
        return mGrade;
    }

    public void setmGrade(String mGrade) {
        this.mGrade = mGrade;
    }
}
