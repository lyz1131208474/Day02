package com.example.administrator.day2_demo.view;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2018/5/10.
 */

public class MyApp extends Application {
    private static MyApp mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        Fresco.initialize(this);


    }
    public static MyApp getInstance(){
        return mInstance;
    };
}
