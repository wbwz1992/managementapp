package com.study.robin.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.robin.managementapp.Class;
import com.study.robin.managementapp.Grade;
import com.study.robin.managementapp.GradeChangeActivity;
import com.study.robin.managementapp.R;
import com.study.robin.managementapp.SQLServerOpenHelper;

import java.util.ArrayList;

/**
 * Created by robin on 2016/6/5.
 */
public class GetGradeInfoTask extends AsyncTask<String, Integer, ArrayList> {
    private Context context;
    private Activity activity;
    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<Grade> mGrades = new ArrayList<>();
    public GetGradeInfoTask(Context context,Activity activity, ListView listView, ProgressBar progressBar){
        this.context = context;
        this.activity = activity;
        this.listView = listView;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        progressBar.setVisibility(View.GONE);
        GradeChangeActivity.mGrades = mGrades;
        GradeAdapter gradeAdapter = new GradeAdapter(mGrades);
        listView.setAdapter(gradeAdapter);
        super.onPostExecute(arrayList);
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        ArrayList gradeList = SQLServerOpenHelper.getGradeIfo(params[0], params[1], context);
        for(int i = 0; i < gradeList.size()/3; i++){
            Grade grade = new Grade();
            grade.setmGrade(gradeList.get(i*3).toString());
            grade.setmId(gradeList.get(i*3+1).toString());
            grade.setmName(gradeList.get(i*3+2).toString());
            mGrades.add(grade);
        }
        return gradeList;
    }

    public class GradeAdapter extends ArrayAdapter<Grade> {
        public GradeAdapter(ArrayList<Grade> mGrades){
            super(activity, 0, mGrades);
        }

        @Override
        public  View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = activity.getLayoutInflater()
                        .inflate(R.layout.grade_list_item, null);
            }

            Grade grade = getItem(position);
            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.grade_list_item_name);
            nameTextView.setText("姓名：" + grade.getmName());
            TextView idTextView =
                    (TextView)convertView.findViewById(R.id.grade_list_item_id);
            idTextView.setText("学号：" + grade.getmId());
            TextView departmentTextView =
                    (TextView)convertView.findViewById(R.id.grade_list_item_grade);
            departmentTextView.setText("成绩：" + grade.getmGrade());

            //return super.getView(position, convertView, parent);
            return convertView;
        }
    }
}
