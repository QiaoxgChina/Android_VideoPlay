package com.ywkd.admin.videoplayproject.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by admin on 2017/3/13.
 */

public class ToastUtils {

    private static final String TAG = "ToastUtils";

    private static Toast mToast;

    /**
     * 显示吐司提示用户
     *
     * @param msg
     * @param context
     */
    public static void showToast(String msg, Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }

        mToast.show();

    }


}
