package com.study.robin.managementapp;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.study.robin.asynctask.DeleteStudentInfoTask;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentDetailFragment extends Fragment {

    //private ArrayList<Student> mStudents = new ArrayList<>();
    private Student mStudent;

    private TextView mNameText;
    private TextView mIdText;
    private TextView mSexText;
    private TextView mDepartmentText;
    private TextView mMajorText;
    private TextView mClassText;
    private TextView mClassIdText;
    private TextView mPositionText;
    private Button mChangeButton;
    private Button mDeleteButton;
    private ProgressBar mProgressBar;
    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String mLoginTag;


    public static final String EXTRA_STUDENT_ID =
            "com.study.robin.managementapp.student_id";
    public static final String EXTRA_STUDENT_ITEM=
            "com.study.robin.managementapp.student_item";
    public static final String EXTRA_CLASS_ID=
            "com.study.robin.managementapp.class_id";


    public StudentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_detail, container, false);

        mStudent = (Student)getArguments().getSerializable(EXTRA_STUDENT_ITEM);
        mNameText = (TextView)view.findViewById(R.id.student_detail_name);
        mIdText = (TextView)view.findViewById(R.id.student_detail_id);
        mSexText = (TextView)view.findViewById(R.id.student_detail_sex);
        mDepartmentText = (TextView)view.findViewById(R.id.student_detail_department);
        mMajorText = (TextView)view.findViewById(R.id.student_detail_major);
        mClassText = (TextView)view.findViewById(R.id.student_detail_class);
        mClassIdText = (TextView)view.findViewById(R.id.student_detail_classId);
        mPositionText = (TextView)view.findViewById(R.id.student_detail_position);
        mChangeButton = (Button)view.findViewById(R.id.student_change_button);
        mDeleteButton = (Button)view.findViewById(R.id.student_delete_button);
        mProgressBar = (ProgressBar)view.findViewById(R.id.student_detail_progressBar);

        sharedPreferences = getContext().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        mLoginTag = sharedPreferences.getString("loginTag", "0");
        if (mLoginTag.equals("1")){
            mChangeButton.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
        }
        else{
            mChangeButton.setVisibility(View.GONE);
            mDeleteButton.setVisibility(View.GONE);
        }
        mNameText.setText("姓名：" + mStudent.getmName());
        mIdText.setText("学号：" + mStudent.getmId());
        mSexText.setText("性别：" + mStudent.getmSex());
        mDepartmentText.setText("院系：" + mStudent.getmDepartment());
        mMajorText.setText("专业：" + mStudent.getmMajor());
        mClassText.setText("班级：" + mStudent.getmClass());
        mClassIdText.setText("班级代号：" + mStudent.getmClassId());
        mPositionText.setText("职位：" + mStudent.getmPosition());

        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkTag) {
                    Intent intent = new Intent();
                    intent.putExtra("tag", 1);
                    intent.putExtra("student", mStudent);
                    intent.setClass(v.getContext(), StudentChangeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), "离线模式无法修改", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkTag) {
                    DeleteStudentInfoTask deleteStudentInfoTask = new DeleteStudentInfoTask(getContext(), mProgressBar);
                    deleteStudentInfoTask.execute(mStudent.getmId().toString());
                }
                else {
                    Toast.makeText(getContext(), "离线模式无法删除", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public static StudentDetailFragment newInstance(Student student){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_STUDENT_ITEM, student);

        StudentDetailFragment studentDetailFragment = new StudentDetailFragment();
        studentDetailFragment.setArguments(args);
        return studentDetailFragment;
    }

}
