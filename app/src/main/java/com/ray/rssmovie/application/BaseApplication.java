package com.ray.rssmovie.application;

import android.app.Application;

import com.ray.rssmovie.network.RetrofitWrapper;

/**
 * Created by guolei on 17-4-6.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
