package com.ywkd.admin.videoplayproject.impl;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.ywkd.admin.videoplayproject.MyApplication;

/**
 * Created by admin on 2017/3/16.
 */

public class PublicMethodImpl {

    /**
     * 获取设备唯一标识码IMEI
     * @return
     */
    public static String getPadIMEI() {
        Context context = MyApplication.getContext();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Activity.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            // android pad
            imei = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return imei;
    }
}
