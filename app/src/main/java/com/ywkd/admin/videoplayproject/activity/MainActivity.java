package com.ywkd.admin.videoplayproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.ywkd.admin.videoplayproject.BaseActivity;
import com.ywkd.admin.videoplayproject.R;
import com.ywkd.admin.videoplayproject.utils.ToastUtils;
import com.ywkd.admin.videoplayproject.utils.UIUtils;

/**
 * Created by admin on 2017/3/14.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Button videoViewBtn = (Button) findViewById(R.id.videoView_btn);
        videoViewBtn.setOnClickListener(this);
        Button mediaPlayerBtn = (Button) findViewById(R.id.mediaPlayer_btn);
        mediaPlayerBtn.setOnClickListener(this);
        Button mvpBtn = (Button) findViewById(R.id.mediaPlayerMVP_btn);
        mvpBtn.setOnClickListener(this);
        findViewById(R.id.playMoreOneVideoAtOnce_btn).setOnClickListener(this);

        findViewById(R.id.playMutipleVideoAtOnce_btn).setOnClickListener(this);

        findViewById(R.id.verticalScreen_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent();
        switch (v.getId()) {
            case R.id.videoView_btn:
                i.setClass(MainActivity.this, VideoPlayByVVActivity.class);
                break;
            case R.id.mediaPlayer_btn:
                i.setClass(MainActivity.this, VideoPlayByMPActivity.class);
                break;
            case R.id.mediaPlayerMVP_btn:
                i.setClass(MainActivity.this, MediaPlayerMvpActivity.class);
                break;
            case R.id.playMoreOneVideoAtOnce_btn:
                i.setClass(MainActivity.this, PlayMultipleVideoByVVActivity.class);
                break;
            case R.id.playMutipleVideoAtOnce_btn:
                i.setClass(MainActivity.this, PlayMultipleVideoByMPActivity.class);
                break;
            case R.id.verticalScreen_btn:
                i.setClass(MainActivity.this, VerticalScreenActivity.class);
                break;
            default:
                break;
        }
        startActivity(i);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionTypeCode = event.getAction();
        switch (actionTypeCode) {
            case MotionEvent.ACTION_DOWN:
                ToastUtils.showToast("按下屏幕", MainActivity.this);
                break;
            case MotionEvent.ACTION_UP:
                ToastUtils.showToast("离开屏幕", MainActivity.this);
                break;
            case MotionEvent.ACTION_MOVE:
                ToastUtils.showToast("滑动屏幕", MainActivity.this);
                break;
        }
        return super.onTouchEvent(event);
    }

}
