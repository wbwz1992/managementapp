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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.study.robin.asynctask.GetClassInfoTask;
import com.study.robin.asynctask.UpdateStudentInfoTask;

import java.util.ArrayList;

public class StudentChangeActivity extends AppCompatActivity {

    private String mId;
    private String mName;
    private String mSex = "";
    public static String mDepartment = "";
    public static String mMajor = "";
    public static String mClass = "";
    private String mClassId;
    private String mPosition;
    private EditText mStudentName;
    private EditText mStudentId;
    private EditText mStudentPosition;
    private Spinner mSexSpinner;
    private Spinner mDepartmentSpinner;
    private Spinner mMajorSpinner;
    private Spinner mClassSpinner;
    private Button mCommitButton;
    private ProgressBar progressBar;
    public static ArrayList mDepartments = new ArrayList();
    public static ArrayList mMajors = new ArrayList();
    public static ArrayList mClasses = new ArrayList();
    public static ArrayList mClassIds = new ArrayList();
    private String[] mSexList;
    private Student student = new Student();
    private Toolbar mToolbar;
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_change);

        findView();
        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", 0);
        if (tag == 1) {
            mToolbar.setSubtitle("修改");
            student = (Student) getIntent().getSerializableExtra("student");
            getInfo(student);
            mStudentId.setText(mId);
            mStudentName.setText(mName);
            mStudentPosition.setText(mPosition);
        }
        else if(tag == 0){
            mToolbar.setSubtitle("添加");
        }
        mToolbar.setTitle("学生");// 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mStudentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mStudentId.addTextChangedListener(new TextWatcher() {
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
        mStudentPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPosition = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        onLine();
        mCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStudentInfoTask updateStudentInfoTask = new UpdateStudentInfoTask(v.getContext(), progressBar, tag);
                updateStudentInfoTask.execute(mId, mName, mSex, mDepartment, mMajor, mClass, mPosition, mClassId);
            }
        });
    }

    public void findView(){
        mStudentName = (EditText)findViewById(R.id.name_edit_change);
        mStudentId = (EditText)findViewById(R.id.id_edit_change);
        mSexSpinner = (Spinner)findViewById(R.id.sex_change_spinner);
        mDepartmentSpinner = (Spinner)findViewById(R.id.student_change_department_spinner);
        mMajorSpinner = (Spinner)findViewById(R.id.student_change_major_spinner);
        mClassSpinner = (Spinner)findViewById(R.id.student_change_class_spinner);
        mCommitButton = (Button)findViewById(R.id.student_commit_button);
        mStudentPosition = (EditText)findViewById(R.id.position_edit_change);
        progressBar = (ProgressBar)findViewById(R.id.student_change_progressBar);
        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
    }
    public void getInfo(Student student){
        mId = student.getmId();
        mName = student.getmName();
        mSex = student.getmSex();
        mDepartment = student.getmDepartment();
        mMajor = student.getmMajor();
        mClass = student.getmClass();
        mClassId = student.getmClassId();
        mPosition = student.getmPosition();
    }
    public void onLine(){
        mSexList = getResources().getStringArray(R.array.student_sex);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSexList);
        mSexSpinner.setAdapter(adapter);
        if (mSex.equals("")){
            mSexSpinner.setSelection(0);
        }
        else if (mSex.equals("男")){
            mSexSpinner.setSelection(1);
        }
        else if(mSex.equals("女")){
            mSexSpinner.setSelection(2);
        }
        mSexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSex = mSexList[position].toString();
                if(mSex.equals("性别")){
                    mSex = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initSpinnerList("selectClassInfo", null, 0, mDepartmentSpinner);
        mDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDepartment = mDepartments.get(position).toString();
                if(mDepartment.equals("请选择院系")){
                    mDepartment = "";
                }
                initSpinnerList("selectClassInfo", mDepartment, 1, mMajorSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMajorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMajor = mMajors.get(position).toString();
                if(mMajor.equals("请选择专业")){
                    mMajor = "";
                }
                initSpinnerList("selectClassInfo", mMajor, 2, mClassSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mClass = mClasses.get(position).toString();
                if(mClass.equals("请选择班级")){
                    mClass = "";
                }
                else {
                    mClassId = mClassIds.get(position-1).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerList( String nameSpace, String value, int tag, Spinner spinner){
        GetClassInfoTask getClassInfoTask = new GetClassInfoTask(this, spinner, progressBar, 2);
        getClassInfoTask.execute(nameSpace,value, String.valueOf(tag));
    }
}
