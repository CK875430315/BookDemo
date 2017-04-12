package com.dianzhi.bookdemo.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by CK on 2016/10/21.
 */
public class MyApplication extends Application {
    public static int Fragment_Index = 0;
    public static Map<String, Long> map;
    public static List<AppCompatActivity> ACTIVITY_ARRAY = new ArrayList<>();
    public static Context Instandce;
    @Override
    public void onCreate() {
        super.onCreate();
        Instandce=this;
        MultiDex.install(this);
    }

}
