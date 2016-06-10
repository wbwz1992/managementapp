package com.study.robin.managementapp;

import java.io.Serializable;

/**
 * Created by robin on 2016/6/5.
 */
public class GradeLesson implements Serializable{
    private String mLessonId;
    private String mLessonName;
    private String mClassDepartment;
    private String mClassMajor;
    private String mClass;
    private String mClassId;

    public String getmLessonId() {
        return mLessonId;
    }

    public void setmLessonId(String mLessonId) {
        this.mLessonId = mLessonId;
    }

    public String getmLessonName() {
        return mLessonName;
    }

    public void setmLessonName(String mLessonName) {
        this.mLessonName = mLessonName;
    }

    public String getmClassDepartment() {
        return mClassDepartment;
    }

    public void setmClassDepartment(String mClassDepartment) {
        this.mClassDepartment = mClassDepartment;
    }

    public String getmClassMajor() {
        return mClassMajor;
    }

    public void setmClassMajor(String mClassMajor) {
        this.mClassMajor = mClassMajor;
    }

    public String getmClass() {
        return mClass;
    }

    public void setmClass(String mClass) {
        this.mClass = mClass;
    }

    public String getmClassId() {
        return mClassId;
    }

    public void setmClassId(String mClassId) {
        this.mClassId = mClassId;
    }
}
