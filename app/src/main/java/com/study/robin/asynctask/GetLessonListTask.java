package com.study.robin.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.study.robin.managementapp.GradeFragment;
import com.study.robin.managementapp.GradeLesson;
import com.study.robin.managementapp.Lesson;
import com.study.robin.managementapp.R;
import com.study.robin.managementapp.SQLServerOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import layout.LessonFragment;

/**
 * Created by robin on 2016/6/3.
 */
public class GetLessonListTask extends AsyncTask<String, Integer, ArrayList> {
    private Context context;
    private int tag;
    private Activity activity;
    private ListView listView;
    private ArrayList<Lesson> mLessons = new ArrayList<>();
    private ArrayList<GradeLesson> mGradeLessons = new ArrayList<>();
    public GetLessonListTask(Context context, Activity activity, ListView listView, int tag){
        this.context = context;
        this.activity = activity;
        this.listView = listView;
        this.tag = tag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        ArrayList mLessonList = SQLServerOpenHelper.getLessonList(params[0], params[1], Integer.parseInt(params[2]), context);
        if (params[2] != "8") {
            for (int i = 0; i < mLessonList.size() / 9; i++) {
                Lesson lesson = new Lesson();
                lesson.setmId(mLessonList.get(i * 9).toString());
                lesson.setmName(mLessonList.get(i * 9 + 1).toString());
                lesson.setmWeekday(Integer.parseInt(mLessonList.get(i * 9 + 2).toString()));
                lesson.setmDaytime(Integer.parseInt(mLessonList.get(i * 9 + 3).toString()));
                lesson.setmStart(Integer.parseInt(mLessonList.get(i * 9 + 4).toString()));
                lesson.setmFinish(Integer.parseInt(mLessonList.get(i * 9 + 5).toString()));
                lesson.setmTeacher(mLessonList.get(i * 9 + 6).toString());
                lesson.setmClassId(mLessonList.get(i * 9 + 7).toString());
                lesson.setmClassRoom(mLessonList.get(i * 9 + 8).toString());
                mLessons.add(lesson);
            }
        }
        else {
            for (int i = 0; i < mLessonList.size() / 6; i++) {
                GradeLesson gradeLesson = new GradeLesson();
                gradeLesson.setmLessonId(mLessonList.get(i * 6).toString());
                gradeLesson.setmLessonName(mLessonList.get(i * 6 + 1).toString());
                gradeLesson.setmClassId(mLessonList.get(i * 6 + 2).toString());
                gradeLesson.setmClassDepartment(mLessonList.get(i * 6 + 3).toString());
                gradeLesson.setmClassMajor(mLessonList.get(i * 6 + 4).toString());
                gradeLesson.setmClass(mLessonList.get(i * 6 + 5).toString());
                mGradeLessons.add(gradeLesson);
            }
        }
        return mLessonList;
    }

    @Override
    protected void onPostExecute(ArrayList arrayList){
        if (tag == 0){
            LessonFragment.mLessons1 = mLessons;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            Date curDate = new Date(System.currentTimeMillis());
            LessonFragment.mLessonTimeText.setText("今天是" + formatter.format(curDate));
            LessonAdapter lessonAdapter = new LessonAdapter(mLessons);
            listView.setAdapter(lessonAdapter);
        }
        else if(tag == 1) {
            LessonFragment.mLessons2 = mLessons;
            LessonAdapter lessonAdapter = new LessonAdapter(mLessons);
            listView.setAdapter(lessonAdapter);
        }
        else if(tag == 2){
            GradeFragment.mGradeLessons = mGradeLessons;
            GradeLessonAdapter gradeLessonAdapter = new GradeLessonAdapter(mGradeLessons);
            listView.setAdapter(gradeLessonAdapter);
        }
        super.onPostExecute(arrayList);
    }

    public class LessonAdapter extends ArrayAdapter<Lesson> {
        public LessonAdapter(ArrayList<Lesson> mLessons){
            super(activity, 0, mLessons);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = activity.getLayoutInflater()
                        .inflate(R.layout.lesson_list_item, null);
            }
            Lesson lesson = getItem(position);
            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.lesson_item_name);
            nameTextView.setText("课程：" + lesson.getmName());
            String[] strings = context.getResources().getStringArray(R.array.lesson_daytime);
            TextView timeTextView =
                    (TextView)convertView.findViewById(R.id.lesson_item_time);
            timeTextView.setText("时间：" + strings[lesson.getmDaytime()-1]);
            TextView classroomTextView =
                    (TextView)convertView.findViewById(R.id.lesson_item_classroom);
            classroomTextView.setText("教室：" + lesson.getmClassRoom());
            TextView weekTextView =
                    (TextView)convertView.findViewById(R.id.lesson_item_week);
            weekTextView.setText("起止周数：" + String.valueOf(lesson.getmStart()) + "-" + String.valueOf(lesson.getmFinish()));
            return convertView;
        }
    }

    public class GradeLessonAdapter extends ArrayAdapter<GradeLesson>{
        public GradeLessonAdapter(ArrayList<GradeLesson> mGradeLessons){
            super(activity, 0, mGradeLessons);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = activity.getLayoutInflater()
                        .inflate(R.layout.grade_lesson_list_item, null);
            }
            GradeLesson gradeLesson = getItem(position);
            TextView nameTextView =
                    (TextView)convertView.findViewById(R.id.grade_lesson_list_name);
            nameTextView.setText(gradeLesson.getmLessonName());
            TextView classTextView =
                    (TextView)convertView.findViewById(R.id.grade_lesson_list_class);
            classTextView.setText(gradeLesson.getmClassDepartment() + gradeLesson.getmClassMajor() +
            gradeLesson.getmClass() + "班");
            return convertView;
        }
    }
}
