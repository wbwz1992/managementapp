package com.study.robin.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.study.robin.managementapp.Account;
import com.study.robin.managementapp.AccountActivity;
import com.study.robin.managementapp.Class;
import com.study.robin.managementapp.LoginActivity;
import com.study.robin.managementapp.LoginFragment;
import com.study.robin.managementapp.MainActivity;
import com.study.robin.managementapp.R;
import com.study.robin.managementapp.SQLServerOpenHelper;

import java.util.ArrayList;

/**
 * Created by robin on 2016/5/18.
 */
public class GetLoginInfoTask extends AsyncTask<String, Integer, ArrayList> {
    private ProgressBar progressBar;
    private Context context;
    private Activity activity;
    private String mId;
    private String mPassword;
    private ListView listView;
    private ArrayList<Account> mAccounts = new ArrayList<>();
    private int tag;
    public GetLoginInfoTask(Context context,ProgressBar progressBar, Activity activity, int tag){
        this.context = context;
        this.activity = activity;
        this.progressBar = progressBar;
        this.tag = tag;
    }
    public GetLoginInfoTask(Context context, ProgressBar progressBar, Activity activity, ListView listView, int tag){
        this.context = context;
        this.activity = activity;
        this.progressBar = progressBar;
        this.listView = listView;
        this.tag = tag;
    }
    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(String... params) {

        mId = params[0];
        ArrayList loginInfo = SQLServerOpenHelper.getLoginInfo(params[0], params[1], context);
        if (tag == 1){
            for (int i = 0; i < loginInfo.size()/4; i++){
                Account account = new Account();
                account.setmId(loginInfo.get(i*4).toString());
                account.setmName(loginInfo.get(i*4+1).toString());
                account.setmPassword(loginInfo.get(i*4+2).toString());
                account.setmLoginTag(Integer.parseInt(loginInfo.get(i*4+3).toString()));
                mAccounts.add(account);
            }
        }
        return loginInfo;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        Log.d("sn", "2222222222");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(ArrayList result) {
        // TODO Auto-generated method stub
        Log.d("sn", "3333333333");
        progressBar.setVisibility(View.GONE);
        if(tag == 0) {
            if (!result.get(0).toString().equals("false")) {
                SharedPreferences mySharedPreferences = context.getSharedPreferences("login_Mode",
                        Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mySharedPreferences.edit();
                editor.putString("loginTag", result.get(3).toString());
                editor.putString("mId", mId);
                editor.putString("mPassword", result.get(2).toString());
                editor.putString("mName", result.get(1).toString());
                editor.putBoolean("networkTag", true);
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(context, MainActivity.class);
                activity.finish();
                context.startActivity(intent);
            }
            else{
                Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            AccountActivity.mAccounts = mAccounts;
            AccountAdapter accountAdapter = new AccountAdapter(mAccounts);
            listView.setAdapter(accountAdapter);
        }
        super.onPostExecute(result);
    }

    public class AccountAdapter extends ArrayAdapter<Account> {
        public AccountAdapter(ArrayList<Account> mAccounts){
            super(activity, 0, mAccounts);
        }

        @Override
        public  View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = activity.getLayoutInflater()
                        .inflate(R.layout.class_list_item, null);
            }

            Account account = getItem(position);
            String[] tags = {"教师", "管理员"};
            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.class_item_department);
            nameTextView.setText("账号：" + account.getmId());
            TextView idTextView =
                    (TextView)convertView.findViewById(R.id.class_item_major);
            idTextView.setText("姓名：" + account.getmName());
            TextView departmentTextView =
                    (TextView)convertView.findViewById(R.id.class_item_class);
            departmentTextView.setText("权限：" + tags[account.getmLoginTag()]);

            //return super.getView(position, convertView, parent);
            return convertView;
        }
    }
}
