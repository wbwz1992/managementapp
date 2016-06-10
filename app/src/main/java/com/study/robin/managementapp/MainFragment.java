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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private TextView mMaintext;
    private TextView mTimeText;
    private Button mUserManageButton;
    private String loginTag;
    private String mName;
    private String[] mCharacters = {"老师", "管理员"};


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mMaintext = (TextView)view.findViewById(R.id.main_fragment_text);
        mTimeText = (TextView)view.findViewById(R.id.main_fragment_week);
        mUserManageButton = (Button)view.findViewById(R.id.user_manager_button);
        SharedPreferences mySharedPreferences = getContext().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        loginTag = mySharedPreferences.getString("loginTag", "");
        mName = mySharedPreferences.getString("mName", "");
        mMaintext.setText("亲爱的" + mCharacters[Integer.parseInt(loginTag)] + "：" + mName + "，您好");
        if(loginTag.equals("0")){
            mUserManageButton.setVisibility(View.GONE);
        }
        else{
            mUserManageButton.setVisibility(View.VISIBLE);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        mTimeText.setText("今天是 " + formatter.format(curDate));
        mUserManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), AccountActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
