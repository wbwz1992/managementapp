package com.study.robin.managementapp;

import java.io.Serializable;

/**
 * Created by robin on 2016/6/3.
 */
public class Lesson implements Serializable {
    private String mId = "";
    private String mName = "";
    private int mWeekday;
    private int mDaytime;
    private int mStart;
    private int mFinish;
    private String mTeacher = "";
    private String mClassId = "";
    private String mClassRoom = "";

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

    public int getmWeekday() {
        return mWeekday;
    }

    public void setmWeekday(int mWeekday) {
        this.mWeekday = mWeekday;
    }

    public int getmDaytime() {
        return mDaytime;
    }

    public void setmDaytime(int mDaytime) {
        this.mDaytime = mDaytime;
    }

    public int getmStart() {
        return mStart;
    }

    public void setmStart(int mStart) {
        this.mStart = mStart;
    }

    public int getmFinish() {
        return mFinish;
    }

    public void setmFinish(int mFinish) {
        this.mFinish = mFinish;
    }

    public String getmTeacher() {
        return mTeacher;
    }

    public void setmTeacher(String mTeacher) {
        this.mTeacher = mTeacher;
    }

    public String getmClassRoom() {
        return mClassRoom;
    }

    public void setmClassRoom(String mClassRoom) {
        this.mClassRoom = mClassRoom;
    }

    public String getmClassId() {
        return mClassId;
    }

    public void setmClassId(String mClassId) {
        this.mClassId = mClassId;
    }
}
