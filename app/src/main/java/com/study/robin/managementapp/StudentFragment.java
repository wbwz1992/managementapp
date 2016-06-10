package com.study.robin.managementapp;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.study.robin.asynctask.GetClassInfoTask;
import com.study.robin.asynctask.GetStudentInfoTask;
import com.study.robin.managementapp.SQLiteOpenHelper;

import java.util.ArrayList;


public class StudentFragment extends Fragment {

    private String mId;
    private String mName;
    private String mSex;
    private String mDepartment;
    private String mMajor;
    private String mClass;
    private EditText mStudentName;
    private EditText mStudentId;
    private Spinner mSexSpinner;
    private Spinner mDepartmentSpinner;
    private Spinner mMajorSpinner;
    private Spinner mClassSpinner;
    private Button mSearchButton;
    private Button mInsertButton;
    private ListView mStudentSearchList;
    private ProgressBar progressBar;
    public static ArrayList<Student> mStudents = new ArrayList<>();
    public static ArrayList mDepartments = new ArrayList();
    public static ArrayList mMajors = new ArrayList();
    public static ArrayList mClasses = new ArrayList();
    private String[] mSexList;

    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String loginTag;

    public static final String STUDENT_LIST =
            "com.study.robin.managementapp.student_list";
    public static final String SELECT_POSITION =
            "com.study.robin.managementapp.select_position";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_student, container, false);
        findView(view);

        sharedPreferences = getContext().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        loginTag = sharedPreferences.getString("loginTag","0");
        if (loginTag.equals("1")) {
            mInsertButton.setVisibility(View.VISIBLE);
        }
        else{
            mInsertButton.setVisibility(View.GONE);
        }

        mSexList = getResources().getStringArray(R.array.student_sex);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, mSexList);
        mSexSpinner.setAdapter(adapter);
        mSexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSex = mSexList[position].toString();
                if(mSex.equals("性别")){
                    mSex = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (networkTag){
            onLine();
        }
        else{
            offLine(view);
        }

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudents.clear();
                networkTag = sharedPreferences.getBoolean("networkTag", true);
                mId = mStudentId.getText().toString();
                mName = mStudentName.getText().toString();
                if(networkTag) {
                    selectFromSQLServer(mName, mId, mSex, mDepartment, mMajor, mClass);
                }
                else {
                    SQLiteOpenHelper.selectFromSQLite( mName, mId,mSex, mDepartment,mMajor, mClass, v, mStudentSearchList, mStudents);
                }
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mStudentSearchList.getWindowToken(), 0);
            }
        });


        mStudentSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), StudentPagerActivity.class);
                i.putExtra(STUDENT_LIST, mStudents);
                i.putExtra(SELECT_POSITION, position);
                startActivity(i);
            }
        });

        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkTag) {
                    Intent intent = new Intent();
                    intent.putExtra("tag", 0);
                    intent.setClass(v.getContext(), StudentChangeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(v.getContext(), "离线模式无法插入新数据", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void findView(View view){
        mStudentName = (EditText)view.findViewById(R.id.name_edit_text);
        mStudentId = (EditText)view.findViewById(R.id.id_edit_text);
        mSexSpinner = (Spinner)view.findViewById(R.id.sex_spinner);
        mDepartmentSpinner = (Spinner)view.findViewById(R.id.student_department_spinner);
        mMajorSpinner = (Spinner)view.findViewById(R.id.student_major_spinner);
        mClassSpinner = (Spinner)view.findViewById(R.id.student_class_spinner);
        mSearchButton = (Button)view.findViewById(R.id.student_search_button);
        mInsertButton = (Button)view.findViewById(R.id.student_insert_button);
        mStudentSearchList = (ListView)view.findViewById(R.id.student_search_list);
        progressBar = (ProgressBar)view.findViewById(R.id.student_progressBar);
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
        GetClassInfoTask getClassInfoTask = new GetClassInfoTask(getContext(), spinner, progressBar, 1);
        getClassInfoTask.execute(nameSpace,value, String.valueOf(tag));
    }

    public ArrayList initSpinnerList(String column1, String value, String column2, Spinner spinner, String label,View view){
        String sql = new String();
        MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(view.getContext());
        SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
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

    /**
     * 查询服务器端数据库
     * @param mName
     * @param mId
     * @param mSex
     * @param mDepartment
     * @param mMajor
     * @param mClass
     */

    public void selectFromSQLServer(String mName, String mId,
                                    String mSex, String mDepartment,
                                    String mMajor, String mClass){

        GetStudentInfoTask getStudentInfoTask = new GetStudentInfoTask(getContext(), progressBar, mStudentSearchList, getActivity());
        getStudentInfoTask.execute(mName, mId, mSex, mDepartment, mMajor, mClass);
    }

    /*
    public void selectFromSQLite(String mName, String mId,
                                 String mSex, String mDepartment,
                                 String mMajor, String mClass, View view){
        String check = mName + mId + mSex + mDepartment + mMajor + mClass;
        if( check.equals("") ){
            Toast.makeText(getContext(), "请输入查询信息", Toast.LENGTH_SHORT).show();
        }
        else {
            MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(view.getContext());
            SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
            String sqlCode = desideSQLiteCode(mName, mId, mSex, mDepartment, mMajor, mClass);
            Cursor cursor = myDatabase.rawQuery(sqlCode, null);
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getContext(),
                    R.layout.student_list_item, cursor, new String[]{"name", "_id", "department"},
                    new int[]{R.id.student_item_name, R.id.student_item_id, R.id.student_item_department},0);
            mStudentSearchList.setAdapter(simpleCursorAdapter);
            if (cursor.moveToFirst()) {
                do {
                    Student student = new Student();
                    student.setmId(cursor.getString(cursor.getColumnIndex("_id")));
                    student.setmName(cursor.getString(cursor.getColumnIndex("name")));
                    student.setmSex(cursor.getString(cursor.getColumnIndex("sex")));
                    student.setmDepartment(cursor.getString(cursor.getColumnIndex("department")));
                    student.setmMajor(cursor.getString(cursor.getColumnIndex("major")));
                    student.setmClass(cursor.getString(cursor.getColumnIndex("class")));
                    student.setmPosition(cursor.getString(cursor.getColumnIndex("position")));
                    mStudents.add(student);
                } while (cursor.moveToNext());
            }
        }

    }*/

    /*public String desideSQLiteCode(String mName, String mId,
                                   String mSex, String mDepartment,
                                   String mMajor, String mClass){
        String sql1 = "SELECT * FROM Student WHERE _id='" + mId + "'";
        String sql2 = "SELECT * FROM Student WHERE name LIKE '%" + mName + "%'" +
                "AND department LIKE '%" + mDepartment + "%'" +
                "AND major LIKE '%" + mMajor + "%'" +
                "AND class LIKE '%" + mClass + "%'"+
                "AND sex LIKE '%" + mSex + "%'";
        if ( !mId.equals("") ) {
            return sql1;
        }
        else{
            return sql2;
        }
    }*/

}
