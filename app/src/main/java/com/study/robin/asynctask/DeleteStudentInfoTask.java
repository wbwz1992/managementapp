package com.study.robin.asynctask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.study.robin.managementapp.MySQLiteOpenHelper;
import com.study.robin.managementapp.SQLServerOpenHelper;

/**
 * Created by robin on 2016/6/2.
 */
public class DeleteStudentInfoTask extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private ProgressBar progressBar;
    public DeleteStudentInfoTask(Context context, ProgressBar progressBar){
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        Boolean aBoolean = SQLServerOpenHelper.DeleteStudentInfo(params[0],context);
        if (aBoolean) {
            MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(context);
            SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
            myDatabase.delete("Student", "_id=?", new String[]{params[0]});
        }
        return aBoolean;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressBar.setVisibility(View.GONE);
        if (aBoolean){
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(aBoolean);
    }
}
