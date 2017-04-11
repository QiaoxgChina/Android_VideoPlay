package com.ywkd.admin.videoplayproject;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.ywkd.admin.videoplayproject.impl.PublicMethodImpl;

/**
 * Created by admin on 2017/3/14.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
