package com.study.robin.managementapp;

import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by robin on 2016/6/4.
 */
public class Utils {
    private static HashMap<Integer, Fragment> objMaps = new LinkedHashMap<Integer, Fragment>();
    public static void setFragmentForPosition(Fragment f, int position){
        objMaps.put(Integer.valueOf(position), f);
    }
}
