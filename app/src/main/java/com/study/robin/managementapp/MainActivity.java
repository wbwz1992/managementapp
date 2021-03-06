package com.study.robin.managementapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.study.robin.asynctask.GetLessonListTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import layout.LessonFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private ListView mDrawerMenu;
    private ArrayAdapter arrayAdapter;
    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    public static MainActivity instance = null;
    private ViewPager mViewPager;
    public static ArrayList<ArrayList<Lesson>> mWeekLessons = new ArrayList<>();
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedPreferences = this.getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        instance = this;
        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mToolbar.setTitle(R.string.app_name);// 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setSubtitle("主页");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerMenu = (ListView)findViewById(R.id.lv_left_menu);
        String[] strings = this.getResources().getStringArray(R.array.item_list);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strings);
        mDrawerMenu.setAdapter(arrayAdapter);

        mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startAnotherActivity(MainActivity.this, position);
            }
        });
        initFragment();

    }
    public void initFragment(){
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, mainFragment, null);
        fragmentTransaction.commit();
    }

    public void startAnotherActivity(Context context, int position){
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent.setClass(context,StudentActivity.class);
                break;
            case 1:
                intent.setClass(context,ClassActivity.class);
                break;
            case 2:
                intent.setClass(context,LessonActivity.class);
                break;
            case 3:
                intent.setClass(context,GradeActivity.class);
                break;
            default:
                intent.setClass(context,LoginActivity.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
                MainActivity.this.finish();
                break;
        }
        context.startActivity(intent);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if(networkTag){
            menu.getItem(0).setTitle("打开离线模式");
        }
        else{
            menu.getItem(0).setTitle("返回在线模式");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings){
            sharedPreferences = this.getSharedPreferences("login_Mode",
                    Activity.MODE_PRIVATE);
            networkTag = sharedPreferences.getBoolean("networkTag", true);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (networkTag){
                item.setTitle("返回在线模式");
                editor.putBoolean("networkTag", false).apply();
            }
            else{
                item.setTitle("打开离线模式");
                editor.putBoolean("networkTag", true).apply();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if (mDrawerLayout.isDrawerOpen(findViewById(R.id.drawer_menu))){
            mDrawerLayout.closeDrawers();
        }else super.onBackPressed();
    }

}
