package com.study.robin.managementapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.study.robin.asynctask.GetLoginInfoTask;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    private EditText mAccountNameEdit;
    private EditText mAccountIdEdit;
    private ListView mAccountList;
    private Button mSearchButton;
    private Button mInsertButton;
    private String mName;
    private String mId;
    private ProgressBar progressBar;
    public static ArrayList<Account> mAccounts = new ArrayList<>();

    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String loginTag;
    private String mPassword;

    private String[] tags = {"教师", "管理员"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = this.getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        loginTag = sharedPreferences.getString("loginTag", "0");
        mPassword = sharedPreferences.getString("mPassword", "");
        mAccountNameEdit = (EditText)findViewById(R.id.account_name_edit);
        mAccountIdEdit = (EditText)findViewById(R.id.account_id_edit);
        mSearchButton = (Button) findViewById(R.id.account_search_button);
        mInsertButton = (Button)findViewById(R.id.account_insert_button);
        mAccountList = (ListView)findViewById(R.id.account_list);
        progressBar = (ProgressBar)findViewById(R.id.account_search_progressBar);
        mName = mAccountNameEdit.getText().toString();
        mId = mAccountIdEdit.getText().toString();
        mSearchButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getAccountList(mAccountIdEdit.getText().toString(), mPassword);
           }
        });
        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog();
            }
        });
        mAccountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initDialog(mAccounts.get(position));
            }
        });
    }
    public void getAccountList(String mId, String mPassword){
        GetLoginInfoTask getLoginInfoTask = new GetLoginInfoTask(this, progressBar, this, mAccountList, 1);
        getLoginInfoTask.execute(mId, mPassword);
    }
    public void initDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setTitle("账户添加");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View v = LayoutInflater.from(AccountActivity.this).inflate(R.layout.dialog2, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(v);
        EditText mIdText = (EditText)findViewById(R.id.account_id_dialog_edit);
        EditText mNameText = (EditText)findViewById(R.id.account_name_dialog_edit);
        EditText mPasswordText = (EditText)findViewById(R.id.account_password_dialog_edit);
        EditText mLoginTagText = (EditText)findViewById(R.id.account_tag_dialog_edit);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }
    public void initDialog(Account account){
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        builder.setTitle("账户修改");
        //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
        View v = LayoutInflater.from(AccountActivity.this).inflate(R.layout.dialog2, null);
        //    设置我们自己定义的布局文件作为弹出框的Content
        builder.setView(v);
        final EditText mIdText = (EditText)v.findViewById(R.id.account_id_dialog_edit);
        final EditText mNameText = (EditText)v.findViewById(R.id.account_name_dialog_edit);
        final EditText mPasswordText = (EditText)v.findViewById(R.id.account_password_dialog_edit);
        final EditText mLoginTagText = (EditText)v.findViewById(R.id.account_tag_dialog_edit);
        mIdText.setText(account.getmId());
        mNameText.setText(account.getmName());
        mPasswordText.setText(account.getmPassword());
        mLoginTagText.setText(tags[account.getmLoginTag()]);
        builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }
}
