package com.study.robin.managementapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.study.robin.asynctask.DeleteClassInfoTask;
import com.study.robin.asynctask.DeleteLessonInfoTask;


public class LessonDetailFragment extends Fragment {

    public static final String EXTRA_LESSON_ITEM=
            "com.study.robin.managementapp.lesson_item";

    private TextView mLessonId;
    private TextView mLessonName;
    private TextView mLessonWeek;
    private TextView mLessonDaytime;
    private TextView mLessonStart;
    private TextView mLessonFinish;
    private TextView mLessonTeacher;
    private TextView mLessonClassId;
    private TextView mLessonClassroom;
    private Button mLessonChangeButton;
    private Button mLessonDeleteButton;
    private Lesson lesson = new Lesson();
    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String mLoginTag;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson_detail, container, false);
        initView(view);
        lesson = (Lesson)getArguments().getSerializable(EXTRA_LESSON_ITEM);
        setView(lesson);
        mLessonChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkTag) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), LessonChangeActivity.class);
                    intent.putExtra(EXTRA_LESSON_ITEM, lesson);
                    intent.putExtra("tag", 1);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "离线模式无法修改", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mLessonDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkTag) {
                    DeleteLessonInfoTask deleteLessonInfoTask = new DeleteLessonInfoTask(v.getContext(), progressBar);
                    deleteLessonInfoTask.execute(lesson.getmId(), String.valueOf(lesson.getmWeekday()), lesson.getmTeacher());
                }
                else{
                    Toast.makeText(v.getContext(), "离线模式无法删除", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void initView(View view){
        mLessonId = (TextView)view.findViewById(R.id.lesson_id_detail);
        mLessonName = (TextView)view.findViewById(R.id.lesson_name_detail);
        mLessonWeek = (TextView)view.findViewById(R.id.lesson_week_detail);
        mLessonDaytime = (TextView)view.findViewById(R.id.lesson_daytime_detail);
        mLessonStart = (TextView)view.findViewById(R.id.lesson_start_detail);
        mLessonFinish = (TextView)view.findViewById(R.id.lesson_finish_detail);
        mLessonTeacher = (TextView)view.findViewById(R.id.lesson_teacher_detail);
        mLessonClassId = (TextView)view.findViewById(R.id.lesson_class_detail);
        mLessonClassroom = (TextView)view.findViewById(R.id.lesson_classroom_detail);
        mLessonChangeButton = (Button)view.findViewById(R.id.lesson_detail_change_button);
        mLessonDeleteButton = (Button)view.findViewById(R.id.lesson_detail_delete_button);
        sharedPreferences = getContext().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        mLoginTag = sharedPreferences.getString("loginTag", "0");
        if (mLoginTag.equals("1")){
            mLessonChangeButton.setVisibility(View.VISIBLE);
            mLessonDeleteButton.setVisibility(View.VISIBLE);
        }
        else{
            mLessonChangeButton.setVisibility(View.GONE);
            mLessonDeleteButton.setVisibility(View.GONE);
        }
    }

    public void setView(Lesson lesson){
        String[] mDaytimes = getContext().getResources().getStringArray(R.array.lesson_daytime);
        String[] mWeeks = getContext().getResources().getStringArray(R.array.lesson_week);
        mLessonId.setText(lesson.getmId());
        mLessonName.setText(lesson.getmName());
        mLessonWeek.setText(mWeeks[lesson.getmWeekday()-1]);
        mLessonDaytime.setText(mDaytimes[lesson.getmDaytime()-1]);
        mLessonStart.setText(String.valueOf(lesson.getmStart()));
        mLessonFinish.setText(String.valueOf(lesson.getmFinish()));
        mLessonTeacher.setText(lesson.getmTeacher());
        mLessonClassId.setText(lesson.getmClassId());
        mLessonClassroom.setText(lesson.getmClassRoom());
    }

    public static LessonDetailFragment newInstance(Lesson mLesson) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_LESSON_ITEM, mLesson);
        LessonDetailFragment fragment = new LessonDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
