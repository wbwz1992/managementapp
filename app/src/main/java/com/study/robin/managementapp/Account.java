package com.study.robin.managementapp;

import java.io.Serializable;

/**
 * Created by robin on 2016/6/5.
 */
public class Account implements Serializable {
    private String mName;
    private String mId;
    private String mPassword;
    private int mLoginTag;

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

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public int getmLoginTag() {
        return mLoginTag;
    }

    public void setmLoginTag(int mLoginTag) {
        this.mLoginTag = mLoginTag;
    }
}
