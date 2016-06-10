package com.study.robin.managementapp;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.study.robin.asynctask.GetLoginInfoTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private ProgressBar progressBar;
    private EditText mIdText;
    private EditText mPasswordText;
    private Button mLoginButton;
    public static String loginTag;
    private String mId;
    private GetLoginInfoTask getLoginInfoTask;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.login_progressBar);
        mIdText = (EditText)view.findViewById(R.id.login_id);
        mPasswordText = (EditText)view.findViewById(R.id.login_password);
        mLoginButton = (Button)view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoginInfoTask = new GetLoginInfoTask(getContext(), progressBar, getActivity(), 0);
                getLoginInfoTask.execute(mIdText.getText().toString(), mPasswordText.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        if( getLoginInfoTask != null && getLoginInfoTask.getStatus() == AsyncTask.Status.RUNNING){
            getLoginInfoTask.cancel(true);
        }
        super.onPause();
    }
}
