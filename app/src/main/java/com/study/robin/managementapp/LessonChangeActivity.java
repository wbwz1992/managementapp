package com.study.robin.managementapp;

import android.app.Activity;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.study.robin.asynctask.UpdateLessonInfoTask;

public class LessonChangeActivity extends AppCompatActivity {

    private String mOldId;
    private String mId;
    private String mName;
    private int mWeek;
    private int mDaytime;
    private String mStart;
    private String mFinish;
    private String mTeacher;
    private String mClassId;
    private String mClassroom;
    private EditText mLessonId;
    private EditText mLessonName;
    private Spinner mLessonWeek;
    private Spinner mLessonDaytime;
    private EditText mLessonStart;
    private EditText mLessonFinish;
    private EditText mLessonTeacher;
    private EditText mLessonClassId;
    private EditText mLessonClassroom;
    private Button mLessonChangeButton;
    private ProgressBar progressBar;
    private Toolbar mToolbar;
    private Lesson lesson = new Lesson();
    private int tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_change);
        tag = getIntent().getIntExtra("tag", 0);
        initView();
        if (tag == 1){
            lesson = (Lesson)getIntent().getSerializableExtra(LessonDetailFragment.EXTRA_LESSON_ITEM);
            initInfo(lesson);
            setView();
        }
        changeInfo();
        mLessonChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateLessonInfoTask updateLessonInfoTask = new UpdateLessonInfoTask(v.getContext(), progressBar, tag);
                updateLessonInfoTask.execute(mOldId, mId,mName, String.valueOf(mWeek),
                        String.valueOf(mDaytime), mStart, mFinish, mTeacher, mClassId, mClassroom);
            }
        });
    }
    public void initView(){
        mLessonId = (EditText)findViewById(R.id.lesson_id_change);
        mLessonName = (EditText)findViewById(R.id.lesson_name_change);
        mLessonWeek = (Spinner)findViewById(R.id.lesson_week_change);
        mLessonDaytime = (Spinner)findViewById(R.id.lesson_daytime_change);
        mLessonStart = (EditText)findViewById(R.id.lesson_start_change);
        mLessonFinish = (EditText)findViewById(R.id.lesson_finish_change);
        mLessonTeacher = (EditText)findViewById(R.id.lesson_teacher_change);
        mLessonClassId = (EditText)findViewById(R.id.lesson_class_change);
        mLessonClassroom = (EditText)findViewById(R.id.lesson_classroom_change);
        mLessonChangeButton = (Button)findViewById(R.id.lesson_commit_button);
        progressBar = (ProgressBar)findViewById(R.id.lesson_change_progressBar);
        mToolbar = (Toolbar)findViewById(R.id.tl_custom);
        String[] mDaytimes = this.getResources().getStringArray(R.array.lesson_daytime);
        String[] mWeeks = this.getResources().getStringArray(R.array.lesson_week);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mWeeks);
        mLessonWeek.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mDaytimes);
        mLessonDaytime.setAdapter(adapter2);
        if (tag == 1) {
            mToolbar.setSubtitle("修改");
        }
        else if(tag == 0){
            mToolbar.setSubtitle("添加");
        }
        mToolbar.setTitle("课程");// 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setView(){
        mLessonId.setText(mId);
        mLessonName.setText(mName);
        mLessonStart.setText(mStart);
        mLessonFinish.setText(mFinish);
        mLessonTeacher.setText(mTeacher);
        mLessonClassId.setText(mClassId);
        mLessonClassroom.setText(mClassroom);
        mLessonWeek.setSelection(mWeek-1);
        mLessonDaytime.setSelection(mDaytime-1);
    }
    public void initInfo(Lesson lesson){
        mId = lesson.getmId();
        mOldId = mId;
        mName = lesson.getmName();
        mWeek = lesson.getmWeekday();
        mDaytime = lesson.getmDaytime();
        mStart = String.valueOf(lesson.getmStart());
        mFinish = String.valueOf(lesson.getmFinish());
        mTeacher = lesson.getmTeacher();
        mClassId = lesson.getmClassId();
        mClassroom = lesson.getmClassRoom();
    }
    public void changeInfo(){
        mLessonId.addTextChangedListener(new TextWatcher() {
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
        mLessonName.addTextChangedListener(new TextWatcher() {
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
        mLessonWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mWeek = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLessonDaytime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDaytime = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLessonStart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStart = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLessonFinish.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mFinish = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLessonTeacher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeacher = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLessonClassId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mClassId = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mLessonClassroom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mClassroom = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
