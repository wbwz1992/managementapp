package com.study.robin.managementapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.study.robin.asynctask.*;

import java.util.ArrayList;

public class ClassChangeActivity extends AppCompatActivity {


    private String mId;
    private String mOldId;
    private int mCount;
    public static String mDepartment = "";
    public static ArrayList mDepartments = new ArrayList();
    private String mMajor = "";
    private String mClass = "";
    private EditText mClassId;
    private EditText mClassCount;
    private Spinner mDepartmentSpinner;
    private EditText mClassMajor;
    private EditText mClassEdit;
    private Button mChangeButton;
    private ProgressBar progressBar;
    private Toolbar mToolbar;
    private Class myClass;
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_change);
        initView();
        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", 0);
        if (tag == 1) {
            mToolbar.setSubtitle("修改");
            myClass = (Class) intent.getSerializableExtra("class");
            getInfo(myClass);
            mClassId.setText(mId);
            mClassMajor.setText(mMajor);
            mClassEdit.setText(mClass);
            mClassCount.setText(String.valueOf(mCount));
        }
        else if(tag == 0){
            mToolbar.setSubtitle("添加");
        }
        mToolbar.setTitle("班级");// 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        onLine();
        mClassId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mId = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mClassMajor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMajor = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mClassEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mClass = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mClassCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCount = Integer.parseInt( s.toString() );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateClassInfoTask updateClassInfoTask = new UpdateClassInfoTask(v.getContext(), progressBar, tag);
                updateClassInfoTask.execute(mOldId, mId, mDepartment, mMajor, mClass, String.valueOf(mCount));
            }
        });
    }
    public void initView(){
        mClassId = (EditText)findViewById(R.id.class_id_edit);
        mDepartmentSpinner = (Spinner)findViewById(R.id.class_department_change_spinner);
        mClassMajor = (EditText)findViewById(R.id.class_major_edit);
        mClassEdit = (EditText)findViewById(R.id.class_class_edit);
        mClassCount = (EditText)findViewById(R.id.class_count_edit);
        mChangeButton = (Button)findViewById(R.id.class_change_commit);
        progressBar = (ProgressBar)findViewById(R.id.class_change_progressBar);
        mToolbar = (Toolbar)findViewById(R.id.tl_custom);
    }
    public void getInfo(Class myClass){
        mId = myClass.getmId();
        mOldId = mId;
        mDepartment = myClass.getmDepartment();
        mMajor = myClass.getmMajor();
        mClass = myClass.getmClass();
        mCount = myClass.getmCount();
    }
    public void onLine(){
        initSpinnerList("selectClassInfo", null, 0, mDepartmentSpinner);
        mDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDepartment = mDepartments.get(position).toString();
                if(mDepartment.equals("请选择院系")){
                    mDepartment = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void initSpinnerList( String nameSpace, String value, int tag, Spinner spinner){
        com.study.robin.asynctask.GetClassInfoTask getClassInfoTask = new com.study.robin.asynctask.GetClassInfoTask(this, spinner, progressBar, 3);
        getClassInfoTask.execute(nameSpace,value, String.valueOf(tag));
    }
}
