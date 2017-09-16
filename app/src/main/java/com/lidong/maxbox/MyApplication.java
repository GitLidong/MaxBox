package com.lidong.maxbox;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by ubuntu on 17-9-6.
 */

public class MyApplication extends Application {
    protected static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LitePal.initialize(this);
    }


}
