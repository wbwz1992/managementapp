package com.study.robin.managementapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import layout.LessonFragment;

public class LessonPagerActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private ListView mDrawerMenu;
    private ArrayAdapter arrayAdapter;

    private ViewPager mViewPager;
    private ArrayList<Lesson> mLessons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_pager);

        mToolbar = (Toolbar) findViewById(R.id.tl_custom);
        mToolbar.setTitle("班级");
        mToolbar.setSubtitle("详情");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setSubtitleTextColor(Color.parseColor("#ffffff"));
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                startAnotherActivity(LessonPagerActivity.this, position);
            }
        });

        mViewPager = (ViewPager)findViewById(R.id.custom_view_pager);

        mLessons = (ArrayList<Lesson>)this.getIntent().getSerializableExtra(LessonFragment.LESSON_LIST);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Lesson mLesson = mLessons.get(position);
                return LessonDetailFragment.newInstance(mLesson);
            }

            @Override
            public int getCount() {
                return mLessons.size();
            }
        });
        int mPosition = getIntent().getIntExtra(LessonFragment.SELECT_POSITION, 0);
        mViewPager.setCurrentItem(mPosition);
    }

    public void startAnotherActivity(Context context, int position){
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent.setClass(context,StudentActivity.class);
                LessonPagerActivity.this.finish();
                context.startActivity(intent);
                break;
            case 1:
                intent.setClass(context,ClassActivity.class);
                LessonPagerActivity.this.finish();
                context.startActivity(intent);
                break;
            case 2:
                break;
            case 3:
                intent.setClass(context,GradeActivity.class);
                LessonPagerActivity.this.finish();
                context.startActivity(intent);
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
    }
}
