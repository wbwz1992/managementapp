package com.study.robin.managementapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.study.robin.asynctask.GetClassInfoTask;
import com.study.robin.asynctask.GetClassListTask;


import java.util.ArrayList;



public class ClassFragment extends Fragment {

    private Spinner mDepartmentSpinner;
    private Spinner mMajorSpinner;
    private Spinner mClassSpinner;
    private Button mSearchButton;
    private Button mInsertButton;
    private ListView mClassList;
    private TextView mTestText;
    private ProgressBar progressBar;
    private String mDepartment;
    private String mMajor;
    private String mClass;
    public static  ArrayList mDepartments = new ArrayList();
    public static  ArrayList mMajors = new ArrayList();
    public static  ArrayList mClasses = new ArrayList();
    private String[] label = {"请选择院系", "请选择专业", "请选择班级"};
    private ArrayList<String> spinnerList;
    public static ArrayList<Class> mClassSearchList = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String loginTag;

    public static final String CLASS_LIST =
            "com.study.robin.managementapp.class_list";
    public static final String SELECT_POSITION =
            "com.study.robin.managementapp.select_class_position";

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        loginTag = sharedPreferences.getString("loginTag", "0");

        mDepartmentSpinner = (Spinner)view.findViewById(R.id.class_department_spinner);
        mMajorSpinner = (Spinner)view.findViewById(R.id.class_major_spinner);
        mClassSpinner = (Spinner)view.findViewById(R.id.class_class_spinner);
        mSearchButton = (Button)view.findViewById(R.id.class_search_button);
        mInsertButton = (Button)view.findViewById(R.id.class_insert_button);
        mClassList = (ListView)view.findViewById(R.id.class_search_list);
        mTestText = (TextView)view.findViewById(R.id.test_text);
        progressBar = (ProgressBar)view.findViewById(R.id.class_progressBar);
        if(loginTag.equals("0")){
            mInsertButton.setVisibility(View.GONE);
        }
        else{
            mInsertButton.setVisibility(View.VISIBLE);
        }
        if(networkTag){
            onLine();
        }
        else {
            offLine(view);
        }
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectFromSQLite( mDepartment,mMajor, mClass, v);
                networkTag = sharedPreferences.getBoolean("networkTag", true);
                if(networkTag){
                    selectClassFromSQLServer(mDepartment, mMajor, mClass, v);
                }
                else{
                    SQLiteOpenHelper.selectFromSQLite( mDepartment,mMajor, mClass, v,mClassList,mClassSearchList);
                }
            }
        });

        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkTag) {
                    Intent intent = new Intent();
                    intent.setClass(v.getContext(), ClassChangeActivity.class);
                    intent.putExtra("tag", 0);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(v.getContext(), "离线模式无法插入新数据", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mClassList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ClassPagerActivity.class);
                i.putExtra(CLASS_LIST, mClassSearchList);
                i.putExtra(SELECT_POSITION, position);
                startActivity(i);
            }
        });
        return view;
    }

    public void onLine(){
        initSpinnerList("selectClassInfo", null, 0, mDepartmentSpinner);
        mDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDepartment = mDepartments.get(position).toString();
                if(mDepartment.equals("请选择院系")){
                    mDepartment = "";
                }
                initSpinnerList("selectClassInfo", mDepartment, 1, mMajorSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMajorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMajor = mMajors.get(position).toString();
                if(mMajor.equals("请选择专业")){
                    mMajor = "";
                }
                initSpinnerList("selectClassInfo", mMajor, 2, mClassSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mClass = mClasses.get(position).toString();
                if(mClass.equals("请选择班级")){
                    mClass = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void offLine(View view){
        mDepartments = initSpinnerList(null, null, "department", mDepartmentSpinner, "请选择院系",view);
        mDepartmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDepartment = mDepartments.get(position).toString();
                if(mDepartment.equals("请选择院系")){
                    mDepartment = "";
                }
                mMajors = initSpinnerList("department", mDepartment, "major", mMajorSpinner, "请选择专业",view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mMajorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMajor = mMajors.get(position).toString();
                if(mMajor.equals("请选择专业")){
                    mMajor = "";
                }
                mClasses = initSpinnerList("major", mMajor, "class", mClassSpinner, "请选择班级", view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mClass = mClasses.get(position).toString();
                if(mClass.equals("请选择班级")){
                    mClass = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerList( String nameSpace, String value, int tag, Spinner spinner){
        GetClassInfoTask getClassInfoTask = new GetClassInfoTask(getContext(), spinner, progressBar, 0);
        getClassInfoTask.execute(nameSpace,value, String.valueOf(tag));
    }

    public ArrayList initSpinnerList(String column1, String value, String column2, Spinner spinner, String label,View view){
        String sql = new String();
        MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(view.getContext());
        SQLiteDatabase myDatabase = myDatabaseHelper.getReadableDatabase();
        if ( column1 == null){
            sql = "SELECT DISTINCT " + column2 + " FROM Class";
        }
        else{
            sql = "SELECT DISTINCT " + column2 + " FROM Class WHERE " + column1 + "='" + value + "'";
        }
        Cursor cursor = myDatabase.rawQuery(sql, null);
        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add(label);
        if(cursor.moveToFirst()){
            do {
                spinnerList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerList);
        spinner.setAdapter(adapter);
        return spinnerList;

    }

    public void selectFromSQLite(String mDepartment, String mMajor, String mClass, View view){
        String check = mDepartment + mMajor + mClass;
        if( check.equals("") ){
            Toast.makeText(getContext(), "请输入查询信息", Toast.LENGTH_SHORT).show();
        }
        else {
            MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(view.getContext());
            SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
            String sqlCode = "SELECT * FROM Class WHERE department LIKE'%" + mDepartment + "%' AND major LIKE'%" + mMajor + "%' AND class LIKE'%" + mClass + "%'";
            Cursor cursor = myDatabase.rawQuery(sqlCode, null);
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getContext(),
                    R.layout.class_list_item, cursor, new String[]{"department", "major", "class"},
                    new int[]{R.id.class_item_department, R.id.class_item_major, R.id.class_item_class},0);
            mClassList.setAdapter(simpleCursorAdapter);
            if (cursor.moveToFirst()) {
                do {
                    Class sClass = new Class();
                    sClass.setmId(cursor.getString(cursor.getColumnIndex("_id")));
                    sClass.setmDepartment(cursor.getString(cursor.getColumnIndex("department")));
                    sClass.setmMajor(cursor.getString(cursor.getColumnIndex("major")));
                    sClass.setmClass(cursor.getString(cursor.getColumnIndex("class")));
                    sClass.setmCount(cursor.getInt(cursor.getColumnIndex("count")));
                    mClassSearchList.add(sClass);
                } while (cursor.moveToNext());
            }
        }

    }

    public void selectClassFromSQLServer(String mDepartment, String mMajor, String mClass, View view){
        GetClassListTask getClassListTask = new GetClassListTask(getContext(), getActivity(),mClassList, progressBar);
        getClassListTask.execute(mDepartment, mMajor, mClass);
    }

}
