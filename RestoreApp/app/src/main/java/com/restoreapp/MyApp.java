package com.restoreapp;

import android.app.Application;

import com.restoreapp.sharedpreference.AbstractSharedPreference;
import com.restoreapp.sharedpreference.SPAppInner;
import com.restoreapp.sharedpreference.SharedPreferenceFactory;

/**
 * Created by wuyajun on 16/8/2.
 */
public class MyApp extends Application {
    /* 设置数据帮助类 */
    public static AbstractSharedPreference asp;

    @Override
    public void onCreate() {
        super.onCreate();
        /* 初始化偏好设置帮助类 */
        asp = SharedPreferenceFactory.getSharedPreference(this, SPAppInner.class);
    }
}
