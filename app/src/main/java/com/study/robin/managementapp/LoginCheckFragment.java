package com.study.robin.managementapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by robin on 2016/5/18.
 */
public class LoginCheckFragment extends Fragment{
    private String mId;
    private String loginTag;
    public LoginCheckFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_check, container, false);

        SharedPreferences mySharedPreferences = getContext().getSharedPreferences("login_Mode",
                Activity.MODE_PRIVATE);
        loginTag = mySharedPreferences.getString("loginTag", "");
        if ( !loginTag.equals("false") && loginTag != ""){
            //Intent intent = new Intent();
            //intent.setClass(getContext(), MainActivity.class);
            //getActivity().finish();
            //getContext().startActivity(intent);
            final Intent localIntent = new Intent(getContext(), MainActivity.class);
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    getActivity().finish();
                    startActivity(localIntent);
                }
            };
            timer.schedule(tast, 1500);
        }
        else{
            //initFragment();
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
                @Override
                public void run() {
                    initFragment();
                }
            };
            timer.schedule(tast, 1500);
        }

        return view;
    }

    public void initFragment(){
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.login_fragment_layout, loginFragment, null);
        fragmentTransaction.commit();
    }
}
