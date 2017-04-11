package com.ywkd.admin.videoplayproject.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.ywkd.admin.videoplayproject.R;
import com.ywkd.admin.videoplayproject.utils.FileUtils;
import com.ywkd.admin.videoplayproject.utils.ToastUtils;
import com.ywkd.admin.videoplayproject.utils.UIUtils;

public class VerticalScreenActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private ImageView mLogoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UIUtils.hideSystemTitleBar(this);
        UIUtils.hideSystemActionBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_screen);
        mVideoView = (VideoView) findViewById(R.id.verticalScreen);
//        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        int picHeight = height * 33 / 1440;
        ToastUtils.showToast("height is " + picHeight, this);
        int picWidth = picHeight * 7 / 2;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(picWidth, picHeight);
        mLogoIv = (ImageView) findViewById(R.id.logoIv);
        mLogoIv.setLayoutParams(params);
        playVideo();
    }

    private void playVideo() {
        String path = FileUtils.getLocalVideoDir() + "08.mkv";
//        ToastUtils.showToast("path is " + path, VerticalScreenActivity.this);
        mVideoView.setVideoPath(path);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playVideo();
            }
        });
        mVideoView.start();

    }
}
