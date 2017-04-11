package com.ywkd.admin.videoplayproject.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by admin on 2017/3/15.
 */

public class UIUtils {

    private static ProgressDialog mProgressDialog;

    /**
     * 显示等待框
     * @param context
     */
    public static void showProgressDialog(Activity context){
        if (mProgressDialog == null){
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setTitle("Loading...");
            mProgressDialog.setMessage("Loading...");
        }
        mProgressDialog.show();
    }

    /**
     * 隐藏等待框
     */
    public static void hideProgressDialog(){
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    /**
     * 显示Or隐藏view
     *
     * @param v
     * @param show
     */
    public static void showWidget(View v, boolean show) {
        if (null == v)
            return;
        boolean toDo = false;
        int visivility = View.VISIBLE;
        if (show) {
            toDo = (v.getVisibility() == View.VISIBLE) ? false : true;
        } else {
            visivility = View.GONE;
            toDo = (v.getVisibility() == View.VISIBLE) ? true : false;
        }
        if (toDo)
            v.setVisibility(visivility);
    }

    /**
     * 隐藏系统的标题栏和状态栏
     *
     * @param activity
     */
    public static void hideSystemTitleBar(Activity activity) {
        //设置全屏且隐藏标题栏
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 隐藏系统的虚拟按键
     *
     * @param activity
     */
    public static void hideSystemActionBar(Activity activity) {
        int status = activity.getWindow().getDecorView().getSystemUiVisibility();
        if (status == View.SYSTEM_UI_FLAG_LOW_PROFILE || status == View.SYSTEM_UI_FLAG_VISIBLE) {
            //虚拟按键被隐藏，但占一定空间
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

}
