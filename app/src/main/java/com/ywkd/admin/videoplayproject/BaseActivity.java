package com.ywkd.admin.videoplayproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

/**
 * Created by admin on 2017/3/13.
 */

public class BaseActivity extends AppCompatActivity {

    public int mWidth;
    public int mHeigth;
    public boolean mIsActivityDestory = false;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mWidth = getWindowManager().getDefaultDisplay().getWidth();//获得屏幕的宽
        mHeigth = getWindowManager().getDefaultDisplay().getHeight();//获取屏幕的高
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsActivityDestory = true;
    }
}
