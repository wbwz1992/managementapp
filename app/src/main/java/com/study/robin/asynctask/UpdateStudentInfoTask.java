package com.study.robin.asynctask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.study.robin.managementapp.MySQLiteOpenHelper;
import com.study.robin.managementapp.R;
import com.study.robin.managementapp.SQLServerOpenHelper;

/**
 * Created by robin on 2016/6/1.
 */
public class UpdateStudentInfoTask extends AsyncTask<String, Integer, Boolean> {
    private int tag;
    private Context context;
    private ProgressBar progressBar;
    public UpdateStudentInfoTask(Context context, ProgressBar progressBar, int tag){
        this.context = context;
        this.progressBar = progressBar;
        this.tag = tag;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Boolean isDone =
                SQLServerOpenHelper.UpdateStudentInfo(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], tag, context);
        String[] studentDB = context.getResources().getStringArray(R.array.student_db);
        if (isDone) {
            MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(context);
            SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            for (int i = 0; i < 7; i++) {
                contentValues.put(studentDB[i], params[i]);
            }
            myDatabase.replace("Student", null, contentValues);
        }
        return isDone;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressBar.setVisibility(View.GONE);
        String[] strings = {"添加", "修改"};
        if (aBoolean){
            Toast.makeText(context, strings[tag] + "成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, strings[tag] + "失败", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(aBoolean);
    }
}
