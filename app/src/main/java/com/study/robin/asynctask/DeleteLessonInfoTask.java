package com.study.robin.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.study.robin.managementapp.SQLServerOpenHelper;

/**
 * Created by robin on 2016/6/4.
 */
public class DeleteLessonInfoTask extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private ProgressBar progressBar;
    public DeleteLessonInfoTask(Context context, ProgressBar progressBar){
        this.context = context;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
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

    @Override
    protected Boolean doInBackground(String... params) {
        Boolean resualt = SQLServerOpenHelper.DeleteLessonInfo(params[0], params[1], params[2], context);
        return resualt;
    }
}
