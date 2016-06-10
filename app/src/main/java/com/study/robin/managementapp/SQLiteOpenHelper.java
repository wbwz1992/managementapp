package com.study.robin.managementapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by robin on 2016/5/18.
 */
public class SQLiteOpenHelper {



    /**
     * 查询本地SQLite数据库
     * @param mName
     * @param mId
     * @param mSex
     * @param mDepartment
     * @param mMajor
     * @param mClass
     * @param view
     */
    public static void selectFromSQLite(String mName, String mId,
                                 String mSex, String mDepartment,
                                 String mMajor, String mClass, View view, ListView listView, ArrayList<Student> mStudents){
        String check = mName + mId + mSex + mDepartment + mMajor + mClass;
        if( check.equals("") ){
            Toast.makeText(view.getContext(), "请输入查询信息", Toast.LENGTH_SHORT).show();
        }
        else {
            MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(view.getContext());
            SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
            String sqlCode = desideSQLiteCode(mName, mId, mSex, mDepartment, mMajor, mClass);
            Cursor cursor = myDatabase.rawQuery(sqlCode, null);
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(view.getContext(),
                    R.layout.student_list_item, cursor, new String[]{"name", "_id", "department"},
                    new int[]{R.id.student_item_name, R.id.student_item_id, R.id.student_item_department},0);
            listView.setAdapter(simpleCursorAdapter);
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
                    student.setmClassId(cursor.getString(cursor.getColumnIndex("class_id")));
                    mStudents.add(student);
                } while (cursor.moveToNext());
            }
        }

    }

    public static String desideSQLiteCode(String mName, String mId,
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
    }

    public static void selectFromSQLite(String mDepartment, String mMajor, String mClass, View view, ListView mClassList, ArrayList<Class> mClassSearchList){
        String check = mDepartment + mMajor + mClass;
        if( check.equals("") ){
            Toast.makeText(view.getContext(), "请输入查询信息", Toast.LENGTH_SHORT).show();
        }
        else {
            MySQLiteOpenHelper myDatabaseHelper = new MySQLiteOpenHelper(view.getContext());
            SQLiteDatabase myDatabase = myDatabaseHelper.getWritableDatabase();
            String sqlCode = "SELECT * FROM Class WHERE department LIKE'%" + mDepartment + "%' AND major LIKE'%" + mMajor + "%' AND class LIKE'%" + mClass + "%'";
            Cursor cursor = myDatabase.rawQuery(sqlCode, null);
            SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(view.getContext(),
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


}
