package com.study.robin.managementapp;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.study.robin.asynctask.GetLessonListTask;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GradeFragment extends Fragment {

    private ListView mGradeLessonList;
    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String mUserId;
    private String mLoginTag;
    public static ArrayList<GradeLesson> mGradeLessons = new ArrayList<>();

    public GradeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grade, container, false);
        sharedPreferences = view.getContext().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        mUserId = sharedPreferences.getString("mId", "");
        mLoginTag = sharedPreferences.getString("loginTag","0");
        mGradeLessonList = (ListView)view.findViewById(R.id.grade_lesson_list);
        getLessonList(mUserId, "", "8", mGradeLessonList, 2);

        mGradeLessonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), GradeChangeActivity.class);
                intent.putExtra("gradeLesson", mGradeLessons.get(position));
                startActivity(intent);
            }
        });

        return view;
    }
    public void getLessonList(String mUserId, String mName, String mWeekday, ListView listView, int tag){
        GetLessonListTask getLessonListTask = new GetLessonListTask(getContext(), getActivity(), listView, tag);
        getLessonListTask.execute(mUserId, mName, mWeekday);
    }
}
