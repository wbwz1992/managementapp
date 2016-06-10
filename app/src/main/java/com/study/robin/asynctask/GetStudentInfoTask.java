package com.study.robin.asynctask;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.study.robin.managementapp.MySQLiteOpenHelper;
import com.study.robin.managementapp.R;
import com.study.robin.managementapp.SQLServerOpenHelper;
import com.study.robin.managementapp.Student;
import com.study.robin.managementapp.StudentFragment;

import java.util.ArrayList;

/**
 * Created by robin on 2016/5/17.
 */
public class GetStudentInfoTask extends AsyncTask<String, Integer, ArrayList<Student>> {
    private Context context;
    private ArrayList studentList;
    private ProgressBar progressBar;
    private ListView listView;
    private Activity activity;
    private ArrayList<Student> mStudents = new ArrayList<>();
    public GetStudentInfoTask(Context context, ProgressBar progressBar, ListView listView, Activity activity){
        this.context = context;
        this.progressBar = progressBar;
        this.listView = listView;
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(String... params) {

        studentList = SQLServerOpenHelper.getStudentList(
                params[0],params[1],params[2],params[3],params[4],params[5],context);
        MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(context);
        SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        for(int i = 0;i < studentList.size() / 8; i++){
            Student student = new Student();
            student.setmId(studentList.get(i*8).toString());
            student.setmName(studentList.get(i*8+1).toString());
            student.setmSex(studentList.get(i*8+2).toString());
            student.setmDepartment(studentList.get(i*8+3).toString());
            student.setmMajor(studentList.get(i*8+4).toString());
            student.setmClass(studentList.get(i*8+5).toString());
            student.setmPosition(studentList.get(i*8+6).toString());
            student.setmClassId(studentList.get(i*8+7).toString());
            mStudents.add(student);
            contentValues.clear();
            contentValues.put("_id",studentList.get(i*8).toString());
            contentValues.put("name",studentList.get(i*8+1).toString());
            contentValues.put("sex",studentList.get(i*8+2).toString());
            contentValues.put("department",studentList.get(i*8+3).toString());
            contentValues.put("major",studentList.get(i*8+4).toString());
            contentValues.put("class",studentList.get(i*8+5).toString());
            contentValues.put("position",studentList.get(i*8+6).toString());
            contentValues.put("class_id",studentList.get(i*8+7).toString());
            myDatabase.replace("Student",null,contentValues);
        }
        myDatabase.close();
        return mStudents;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        Log.d("sn", "2222222222");
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(ArrayList result) {
        // TODO Auto-generated method stub
        Log.d("sn", "3333333333");
        progressBar.setVisibility(View.GONE);

        StudentFragment.mStudents = result;

        StudentAdapter studentAdapter = new StudentAdapter(mStudents);
        listView.setAdapter(studentAdapter);

        super.onPostExecute(result);
    }

    public class StudentAdapter extends ArrayAdapter<Student>{
        public StudentAdapter(ArrayList<Student> students){
            super(activity, 0, students);
        }

        @Override
        public  View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = activity.getLayoutInflater()
                        .inflate(R.layout.student_list_item, null);
            }

            Student student = getItem(position);

            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.student_item_name);
            nameTextView.setText("姓名：" + student.getmName());
            TextView idTextView =
                    (TextView)convertView.findViewById(R.id.student_item_id);
            idTextView.setText("院系：" + student.getmDepartment());
            TextView departmentTextView =
                    (TextView)convertView.findViewById(R.id.student_item_department);
            departmentTextView.setText("学号：" + student.getmId());

            //return super.getView(position, convertView, parent);
            return convertView;
        }
    }

}


