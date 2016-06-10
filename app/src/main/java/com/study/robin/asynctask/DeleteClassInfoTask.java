package com.study.robin.asynctask;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.study.robin.managementapp.MySQLiteOpenHelper;
import com.study.robin.managementapp.R;
import com.study.robin.managementapp.SQLServerOpenHelper;

/**
 * Created by robin on 2016/6/2.
 */
public class DeleteClassInfoTask extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private ProgressBar progressBar;
    public DeleteClassInfoTask(Context context, ProgressBar progressBar){
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
        Boolean isDone =
                SQLServerOpenHelper.DeleteClassInfo(params[0], context);
        if (isDone) {
            MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(context);
            SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
            myDatabase.delete("Class","_id=?", new String[]{params[0]});
            myDatabase.delete("Student", "class_id=?", new String[]{params[0]});
        }
        return isDone;
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
