package com.app.wetravel;

import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

public class travelApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        // 初始化 JPush
        JPushInterface.init(this);

    }
}
