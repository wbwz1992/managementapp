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

import com.study.robin.asynctask.DeleteClassInfoTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClassDetailFragment extends Fragment {
    public static final String EXTRA_CLASS_ITEM=
            "com.study.robin.managementapp.class_item";

    private TextView mIdText;
    private TextView mCountText;
    private TextView mDepartmentText;
    private TextView mMajorText;
    private TextView mClassText;
    private Class mClass;
    private Button mChangeButton;
    private Button mDeleteButton;
    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String mLoginTag;
    private ProgressBar progressBar;


    public ClassDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class_detail, container, false);

        mClass = (Class)getArguments().getSerializable(EXTRA_CLASS_ITEM);
        mIdText = (TextView)view.findViewById(R.id.class_detail_id);
        mCountText = (TextView)view.findViewById(R.id.class_detail_count);
        mDepartmentText = (TextView)view.findViewById(R.id.class_detail_department);
        mMajorText = (TextView)view.findViewById(R.id.class_detail_major);
        mClassText = (TextView)view.findViewById(R.id.class_detail_class);
        mChangeButton = (Button)view.findViewById(R.id.class_change_button);
        mDeleteButton = (Button)view.findViewById(R.id.class_delete_button);
        progressBar = (ProgressBar)view.findViewById(R.id.class_detail_progressBar);

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

        mIdText.setText("班级号：" + mClass.getmId());
        mDepartmentText.setText("院系：" + mClass.getmDepartment());
        mMajorText.setText("专业：" + mClass.getmMajor());
        mClassText.setText("班级：" + mClass.getmClass());
        mCountText.setText("人数：" + mClass.getmCount());

        mChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkTag) {
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(), ClassChangeActivity.class);
                    intent.putExtra("tag", 1);
                    intent.putExtra("class", mClass);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(v.getContext(), "离线模式无法修改", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkTag) {
                    DeleteClassInfoTask deleteClassInfoTask = new DeleteClassInfoTask(v.getContext(), progressBar);
                    deleteClassInfoTask.execute(mClass.getmId());
                }
                else{
                    Toast.makeText(v.getContext(), "离线模式无法删除", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public static ClassDetailFragment newInstance(Class mClass){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CLASS_ITEM, mClass);
        ClassDetailFragment classDetailFragment = new ClassDetailFragment();
        classDetailFragment.setArguments(args);
        return classDetailFragment;
    }

}
