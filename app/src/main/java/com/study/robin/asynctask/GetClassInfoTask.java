package com.study.robin.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.study.robin.managementapp.ClassActivity;
import com.study.robin.managementapp.ClassChangeActivity;
import com.study.robin.managementapp.ClassFragment;
import com.study.robin.managementapp.SQLServerOpenHelper;
import com.study.robin.managementapp.StudentActivity;
import com.study.robin.managementapp.StudentChangeActivity;
import com.study.robin.managementapp.StudentFragment;

import java.util.ArrayList;

/**
 * Created by robin on 2016/5/17.
 */
public class GetClassInfoTask extends AsyncTask<String, Integer, ArrayList> {
    private int mTag;
    private String[] label = {"请选择院系", "请选择专业", "请选择班级"};
    private Spinner spinner;
    private Context context;
    private int activityTag;
    private ArrayList classId = new ArrayList();
    private ProgressBar progressBar;
    private int index;
    public GetClassInfoTask(Context context, Spinner spinner, ProgressBar progressBar, int activityTag){
        this.activityTag = activityTag;
        this.progressBar = progressBar;
        this.spinner = spinner;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        int tag = Integer.parseInt(params[2]);
        ArrayList spinnerList = new ArrayList();
        mTag = tag;
        if (params[1] != "") {
            if(tag != 2) {
                spinnerList = SQLServerOpenHelper.getClassInfo(params[0], params[1], tag, context);
            }
            else{
                ArrayList resultList = SQLServerOpenHelper.getClassInfo(params[0], params[1], tag, context);
                for (int i = 0; i < resultList.size(); i++){
                    if (i % 2 == 0){
                        classId.add(resultList.get(i));
                    }
                    else{
                        spinnerList.add(resultList.get(i));
                    }
                }
            }
        }
        spinnerList.add(0, label[mTag]);
        return spinnerList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        Log.d("sn", "2222222222");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList result) {
        // TODO Auto-generated method stub
        Log.d("sn", "3333333333");
        progressBar.setVisibility(View.GONE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, result);
        spinner.setAdapter(adapter);
        if (activityTag == 0) {
            switch (mTag) {
                case 0:
                    ClassFragment.mDepartments = result;
                    break;
                case 1:
                    ClassFragment.mMajors = result;
                    break;
                case 2:
                    ClassFragment.mClasses = result;
                    break;
                default:
                    break;
            }
        }
        else if(activityTag == 1){
            switch (mTag) {
                case 0:
                    StudentFragment.mDepartments = result;
                    break;
                case 1:
                    StudentFragment.mMajors = result;
                    break;
                case 2:
                    StudentFragment.mClasses = result;
                    break;
                default:
                    break;
            }
        }
        else if(activityTag == 2){
            switch (mTag) {
                case 0:
                    StudentChangeActivity.mDepartments = result;
                    for (int i = 0; i < result.size(); i++){
                        if (StudentChangeActivity.mDepartment.equals(result.get(i).toString())){
                            index = i;
                            break;
                        }
                    }
                    spinner.setSelection(index);
                    break;
                case 1:
                    StudentChangeActivity.mMajors = result;
                    for (int i = 0; i < result.size(); i++){
                        if (StudentChangeActivity.mMajor.equals(result.get(i).toString())){
                            index = i;
                            break;
                        }
                    }
                    spinner.setSelection(index);
                    break;
                case 2:
                    StudentChangeActivity.mClasses = result;
                    StudentChangeActivity.mClassIds = classId;
                    for (int i = 0; i < result.size(); i++){
                        if (StudentChangeActivity.mClass.equals(result.get(i).toString())){
                            index = i;
                            break;
                        }
                    }
                    spinner.setSelection(index);
                    break;
                default:
                    break;
            }
        }
        else if (activityTag == 3) {
            ClassChangeActivity.mDepartments = result;
            for (int i = 0; i < result.size(); i++) {
                if (ClassChangeActivity.mDepartment.equals(result.get(i).toString())) {
                    index = i;
                    break;
                }
            }
            spinner.setSelection(index);
        }
        super.onPostExecute(result);
    }
}

