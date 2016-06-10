package layout;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.study.robin.asynctask.GetLessonListTask;
import com.study.robin.managementapp.ClassPagerActivity;
import com.study.robin.managementapp.Lesson;
import com.study.robin.managementapp.LessonChangeActivity;
import com.study.robin.managementapp.LessonPagerActivity;
import com.study.robin.managementapp.R;

import java.util.ArrayList;
import java.util.Calendar;


public class LessonFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private Boolean networkTag;
    private String mUserId;
    private String mLoginTag;
    private String mLessonName;
    private String mTeacherName;
    private TabHost tabHost;
    public static TextView mLessonTimeText;
    private ListView mLessonList1;
    private ListView mLessonList;
    private Button mSearchButton;
    private Button mInsertButton;
    private EditText mNameEdit;
    private EditText mTeacherEdit;
    public static ArrayList<Lesson> mLessons1 = new ArrayList<>();
    public static ArrayList<Lesson> mLessons2 = new ArrayList<>();
    private Lesson mLesson;
    //private ArrayList<Lesson> mDayLessons = new ArrayList<>();

    public static final String EXTRA_DAY_LIST=
            "com.study.robin.managementapp.lesson_day_list";
    public static final String SELECT_POSITION =
            "com.study.robin.managementapp.select_lesson_position";
    public static final String LESSON_LIST =
            "com.study.robin.managementapp.lesson_list";

    public LessonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        sharedPreferences = view.getContext().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        networkTag = sharedPreferences.getBoolean("networkTag", true);
        mUserId = sharedPreferences.getString("mId", "");
        mLoginTag = sharedPreferences.getString("loginTag","0");
        Calendar calendar = Calendar.getInstance();
        mLessonList1 = (ListView)view.findViewById(R.id.lesson_list_view);
        mSearchButton = (Button)view.findViewById(R.id.lesson_search_button);
        mLessonList = (ListView)view.findViewById(R.id.lesson_list_2);
        mNameEdit = (EditText)view.findViewById(R.id.lesson_search_name);
        mTeacherEdit = (EditText)view.findViewById(R.id.lesson_search_teacher);
        mInsertButton = (Button)view.findViewById(R.id.lesson_insert_button);
        if (mLoginTag == "0"){
            mInsertButton.setVisibility(View.GONE);
        }
        else{
            mInsertButton.setVisibility(View.VISIBLE);
        }
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mLessonName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTeacherEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTeacherName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tabHost = (TabHost)view.findViewById(R.id.lesson_tab_host);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab_list").setIndicator("课程表",null).setContent(R.id.tab_lesson_list));
        tabHost.addTab(tabHost.newTabSpec("tab_search").setIndicator("搜索",null).setContent(R.id.tab_lesson_search));
        mLessonTimeText = (TextView)view.findViewById(R.id.lesson_time_text);
        mLessonList1 = (ListView)view.findViewById(R.id.lesson_list_view);
        getLessonList(mUserId, "", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)-1), mLessonList1, 0);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLessonList(mTeacherName, mLessonName, "0", mLessonList, 1);
            }
        });

        mInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), LessonChangeActivity.class);
                intent.putExtra("tag", 0);
                startActivity(intent);
            }
        });

        mLessonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), LessonPagerActivity.class);
                i.putExtra(LESSON_LIST, mLessons2);
                i.putExtra(SELECT_POSITION, position);
                startActivity(i);
            }
        });
        mLessonList1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), LessonPagerActivity.class);
                i.putExtra(LESSON_LIST, mLessons1);
                i.putExtra(SELECT_POSITION, position);
                startActivity(i);
            }
        });


        return view;
    }
    public void getLessonList(String mUserId, String mName, String mWeekday, ListView listView, int tag){
        GetLessonListTask getLessonListTask = new GetLessonListTask(getContext(), getActivity(), listView, tag);
        getLessonListTask.execute(mUserId, mName, mWeekday);
    }


    public static LessonFragment newInstance(ArrayList<Lesson> mDayLessons){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DAY_LIST, mDayLessons);
        LessonFragment lessonFragment = new LessonFragment();
        lessonFragment.setArguments(args);
        return lessonFragment;
    }

}
