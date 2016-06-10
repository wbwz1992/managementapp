package com.study.robin.managementapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by robin on 2016/5/15.
 */
public class SQLServerOpenHelper {
    private static String nameSpace = "http://tempuri.org/";
    private static String endPoint = "http://10.0.2.2/WebService1.asmx";
    //private static String endPoint = "http://wbwz1992.wicp.io/WebService1.asmx";
    //private static String endPoint = "http://1c5104r326.iok.la/WebService1.asmx";

    public static ArrayList getLoginInfo( String mId, String password, Context context){
        String methodName = "userLogin";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mId", mId);
        rpc.addProperty("mPassword", password);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        ArrayList loginInfo = new ArrayList();
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                for (int i = 0; i < object.getPropertyCount(); i++){
                    loginInfo.add(object.getProperty(i).toString());
                }
            }
            else{
                Toast.makeText(context, "未查询到信息", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return loginInfo;
    }

    public static ArrayList getClassInfo(String methodName , String value, int tag, Context context){
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("value", value);
        rpc.addProperty("tag", tag);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        ArrayList arrayList = new ArrayList();
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                for (int i = 0; i < object.getPropertyCount(); i++){
                    arrayList.add(object.getProperty(i).toString());
                }
            }
            else{
                Toast.makeText(context, "未查询到信息", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return arrayList;
    }

    public static ArrayList getStudentList(
            String mName, String mId, String mSex, String mDepartment,
            String mMajor, String mClass, Context context){
        String methodName = "selectStudentInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mId", mId);
        rpc.addProperty("mName", mName);
        rpc.addProperty("mSex", mSex);
        rpc.addProperty("mDepartment", mDepartment);
        rpc.addProperty("mMajor", mMajor);
        rpc.addProperty("mClass", mClass);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        ArrayList arrayList = new ArrayList();
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                for (int i = 0; i < object.getPropertyCount(); i++){
                    arrayList.add(object.getProperty(i).toString());
                }
            }
            else{
                Toast.makeText(context, "未查询到信息", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return arrayList;
    }

    public static ArrayList getClassList( String mDepartment, String mMajor, String mClass, Context context){
        String methodName = "selectClassList";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mDepartment", mDepartment);
        rpc.addProperty("mMajor", mMajor);
        rpc.addProperty("mClass", mClass);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        ArrayList classList = new ArrayList();
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                for (int i = 0; i < object.getPropertyCount(); i++){
                    classList.add(object.getProperty(i).toString());
                }
            }
            else{
                Toast.makeText(context, "未查询到信息", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return classList;
    }

    public static Boolean UpdateStudentInfo(String mId, String mName, String mSex, String mDepartment, String mMajor,
                                            String mClass, String mPosition, String mClassId, int tag, Context context) {
        String methodName = "insertStudentInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mId", mId);
        rpc.addProperty("mName", mName);
        rpc.addProperty("mSex", mSex);
        rpc.addProperty("mDepartment", mDepartment);
        rpc.addProperty("mMajor", mMajor);
        rpc.addProperty("mClass", mClass);
        rpc.addProperty("mPosition", mPosition);
        rpc.addProperty("mClassId", mClassId);
        rpc.addProperty("tag", tag);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        Boolean isDone = true;
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                isDone = (Boolean) object.getProperty(0);
            }
            else{
                String[] strings = {"添加", "修改"};
                Toast.makeText(context, strings[tag] + "失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return isDone;
    }

    public static Boolean DeleteStudentInfo(String mId, Context context) {
        String methodName = "deleteStudentInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mId", mId);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        Boolean isDone = true;
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                isDone = (Boolean) object.getProperty(0);
            }
            else{
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return isDone;
    }

    public static Boolean UpdateClassInfo(String mOldId, String mId, String mDepartment, String mMajor,
                                            String mClass, String mCount, int tag, Context context) {
        String methodName = "insertClassInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mOldId", mOldId);
        rpc.addProperty("mId", mId);
        rpc.addProperty("mDepartment", mDepartment);
        rpc.addProperty("mMajor", mMajor);
        rpc.addProperty("mClass", mClass);
        rpc.addProperty("mCount", mCount);
        rpc.addProperty("tag", tag);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        Boolean isDone = true;
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                isDone = (Boolean) object.getProperty(0);
            }
            else{
                String[] strings = {"添加", "修改"};
                Toast.makeText(context, strings[tag] + "失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return isDone;
    }

    public static Boolean DeleteClassInfo(String mId, Context context) {
        String methodName = "deleteClassInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mId", mId);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        Boolean isDone = true;
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                isDone = (Boolean) object.getProperty(0);
            }
            else{
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return isDone;
    }

    public static ArrayList getLessonList( String mTeacher, String mName, int mWeek, Context context){
        String methodName = "selectLessonInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mTeacher", mTeacher);
        rpc.addProperty("mName", mName);
        rpc.addProperty("mWeekday", mWeek);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        ArrayList mLessonList = new ArrayList();
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                for (int i = 0; i < object.getPropertyCount(); i++){
                    mLessonList.add(object.getProperty(i).toString());
                }
            }
            else{
                Toast.makeText(context, "未查询到信息", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return mLessonList;
    }
    public static Boolean DeleteLessonInfo(String mId, String mWeekday, String mteacher, Context context) {
        String methodName = "deleteLessonInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mId", mId);
        rpc.addProperty("mWeekday", mWeekday);
        rpc.addProperty("mTeacher", mteacher);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        Boolean isDone = true;
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                isDone = (Boolean) object.getProperty(0);
            }
            else{
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return isDone;
    }

    public static Boolean UpdateLessonInfo(String oldId, String mId, String mName, String mWeekday,
                                           String mDaytime, String mStart, String mFinish, String mTeacher,
                                           String mClassId, String mClassroom, int tag, Context context) {
        String methodName = "insertLessonInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("oldId", oldId);
        rpc.addProperty("mId", mId);
        rpc.addProperty("mName", mName);
        rpc.addProperty("mWeekday", mWeekday);
        rpc.addProperty("mTeacher", mTeacher);
        rpc.addProperty("mDaytime", mDaytime);
        rpc.addProperty("mStart", mStart);
        rpc.addProperty("mFinish", mFinish);
        rpc.addProperty("mClassId", mClassId);
        rpc.addProperty("mClassroom", mClassroom);
        rpc.addProperty("tag", tag);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        Boolean isDone = true;
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                isDone = (Boolean) object.getProperty(0);
            }
            else{
                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return isDone;
    }
    public static ArrayList getGradeIfo( String mLessonId, String mClassId, Context context){
        String methodName = "selectGradeInfo";
        String soapAction = nameSpace + methodName;
        SoapObject rpc = new SoapObject(nameSpace, methodName);
        rpc.addProperty("mLessonId", mLessonId);
        rpc.addProperty("mClassId", mClassId);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(rpc);
        transport.debug = true;
        ArrayList mGradeList = new ArrayList();
        try {
            transport.call(soapAction, envelope);
            if (envelope.getResponse() != null){
                SoapObject object = (SoapObject) envelope.getResponse();
                for (int i = 0; i < object.getPropertyCount(); i++){
                    mGradeList.add(object.getProperty(i).toString());
                }
            }
            else{
                Toast.makeText(context, "未查询到信息", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("soap", e.getMessage());
        }
        return mGradeList;
    }
}
