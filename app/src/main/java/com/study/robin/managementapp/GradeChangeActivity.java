package com.study.robin.managementapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.study.robin.asynctask.GetGradeInfoTask;

import java.util.ArrayList;

public class GradeChangeActivity extends AppCompatActivity {

    private ListView mGradeChangeList;
    private GradeLesson gradeLesson;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private String mLessonId;
    public static ArrayList<Grade> mGrades = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_change);
        gradeLesson = (GradeLesson)getIntent().getSerializableExtra("gradeLesson");
        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mToolbar.setTitle("成绩");
        mToolbar.setSubtitle(gradeLesson.getmLessonName());
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mGradeChangeList = (ListView)findViewById(R.id.grade_change_list);
        mProgressBar = (ProgressBar)findViewById(R.id.grade_list_progressBar);
        getGradeInfo(gradeLesson.getmLessonId(), gradeLesson.getmClassId());
        initDialog();
    }
    public void getGradeInfo(String mLessonId, String mClassId){
        GetGradeInfoTask getGradeInfoTask = new GetGradeInfoTask(this, this, mGradeChangeList, mProgressBar);
        getGradeInfoTask.execute(mLessonId, mClassId);
    }
    public void initDialog(){
        mGradeChangeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GradeChangeActivity.this);
                builder.setTitle("成绩录入");
                //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                View v = LayoutInflater.from(GradeChangeActivity.this).inflate(R.layout.dialog, null);
                //    设置我们自己定义的布局文件作为弹出框的Content
                builder.setView(v);
                final TextView mNameText = (TextView)v.findViewById(R.id.grade_change_dialog_name);
                final TextView mIdText = (TextView)v.findViewById(R.id.grade_change_dialog_id);
                final EditText mGradeText = (EditText)v.findViewById(R.id.grade_change_dialog_edit);
                mNameText.setText("姓名：" + mGrades.get(position).getmName());
                mIdText.setText("学号：" + mGrades.get(position).getmId());
                mGradeText.setText(mGrades.get(position).getmGrade());

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
    }
}
