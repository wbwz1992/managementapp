package com.study.robin.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.study.robin.managementapp.SQLServerOpenHelper;

/**
 * Created by robin on 2016/6/5.
 */
public class UpdateLessonInfoTask extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private ProgressBar progressBar;
    private int tag;
    public UpdateLessonInfoTask(Context context, ProgressBar progressBar, int tag){
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

    @Override
    protected Boolean doInBackground(String... params) {
        Boolean resualt = SQLServerOpenHelper.UpdateLessonInfo(params[0], params[1], params[2],
                params[3], params[4], params[5],
                params[6], params[7], params[8],
                params[9], tag, context);
        return resualt;
    }
}
