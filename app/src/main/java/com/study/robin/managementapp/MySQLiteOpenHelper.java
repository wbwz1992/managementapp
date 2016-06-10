package com.study.robin.managementapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Toast;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "Management.db";
    private final static String CREATE_STUDENT = "CREATE TABLE Student("
            + "_id TEXT PRIMARY KEY,"
            + "name TEXT,"
            + "sex TEXT,"
            + "department TEXT,"
            + "major TEXT,"
            + "class TEXT,"
            + "position TEXT,"
            + "class_id TEXT)";
    //private final static String CREARE_STUDENT_INDEX = "CREATE UNIQUE INDEX student_index ON Student(_id)";

    private final static String CREATE_CLASS = "CREATE TABLE Class("
            + "_id TEXT PRIMARY KEY,"
            + "department TEXT,"
            + "major TEXT,"
            + "class TEXT,"
            + "count TEXT)";

    //private final static String CREARE_CLASS_INDEX = "CREATE UNIQUE INDEX class_index ON Class(_id)";

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_CLASS);
    }
    public MySQLiteOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

}


