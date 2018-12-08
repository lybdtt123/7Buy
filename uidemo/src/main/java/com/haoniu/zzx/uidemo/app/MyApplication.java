package com.haoniu.zzx.uidemo.app;

import android.app.Application;

/**
 * Created by zzx on 2017/11/9 0009.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Bugly.init(getApplicationContext(), "ea3381ab45", false);
    }
}
