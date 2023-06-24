package com.app.wetravel.pushservice;

import android.content.Context;
import android.util.Log;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushReceiver extends JPushMessageReceiver {

    private static final String TAG = "PushReceiver";
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);

        Log.d(TAG, "onMessage: "+customMessage.message);
    }
}
