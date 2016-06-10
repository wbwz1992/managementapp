package com.study.robin.asynctask;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.robin.managementapp.ClassFragment;
import com.study.robin.managementapp.MySQLiteOpenHelper;
import com.study.robin.managementapp.R;
import com.study.robin.managementapp.SQLServerOpenHelper;
import com.study.robin.managementapp.Class;

import java.util.ArrayList;

/**
 * Created by robin on 2016/5/18.
 */
public class GetClassListTask extends AsyncTask<String, Integer, ArrayList> {

    private Context context;
    private Activity activity;
    private ProgressBar progressBar;
    private ListView listView;
    private ArrayList arrayList = new ArrayList();
    private ArrayList<Class> mClasses = new ArrayList<>();
    public GetClassListTask(Context context, Activity activity, ListView listView, ProgressBar progressBar){
        this.progressBar = progressBar;
        this.context = context;
        this.activity = activity;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(String... params) {

        arrayList = SQLServerOpenHelper.getClassList(params[0], params[1], params[2], context);
        MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(context);
        SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < arrayList.size()/5; i++){
            Class mClass = new Class();
            mClass.setmId(arrayList.get(i*5).toString());
            mClass.setmDepartment(arrayList.get(i*5+1).toString());
            mClass.setmMajor(arrayList.get(i*5+2).toString());
            mClass.setmClass(arrayList.get(i*5+3).toString());
            mClass.setmCount(Integer.parseInt(arrayList.get(i*5+4).toString()));
            mClasses.add(mClass);
            contentValues.clear();
            contentValues.put("_id",arrayList.get(i*5).toString());
            contentValues.put("department",arrayList.get(i*5+1).toString());
            contentValues.put("major",arrayList.get(i*5+2).toString());
            contentValues.put("class",arrayList.get(i*5+3).toString());
            contentValues.put("count",arrayList.get(i*5+4).toString());
            myDatabase.replace("Class", null, contentValues);
        }
        myDatabase.close();
        return mClasses;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        Log.d("sn", "2222222222");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList result) {

        progressBar.setVisibility(View.GONE);
        ClassFragment.mClassSearchList = result;
        ClassAdapter classAdapter = new ClassAdapter(result);
        listView.setAdapter(classAdapter);
        super.onPostExecute(result);
    }

    public class ClassAdapter extends ArrayAdapter<Class> {
        public ClassAdapter(ArrayList<Class> classes){
            super(activity, 0, classes);
        }

        @Override
        public  View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = activity.getLayoutInflater()
                        .inflate(R.layout.class_list_item, null);
            }

            Class mClass = getItem(position);

            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.class_item_department);
            nameTextView.setText("院系：" + mClass.getmDepartment());
            TextView idTextView =
                    (TextView)convertView.findViewById(R.id.class_item_major);
            idTextView.setText("专业：" + mClass.getmMajor());
            TextView departmentTextView =
                    (TextView)convertView.findViewById(R.id.class_item_class);
            departmentTextView.setText("班级：" + mClass.getmClass());

            //return super.getView(position, convertView, parent);
            return convertView;
        }
    }
}
