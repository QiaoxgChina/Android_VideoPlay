package com.ywkd.admin.videoplayproject.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.ywkd.admin.videoplayproject.BaseActivity;
import com.ywkd.admin.videoplayproject.R;
import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.data.VideoFileData;
import com.ywkd.admin.videoplayproject.utils.FileUtils;
import com.ywkd.admin.videoplayproject.utils.ToastUtils;
import com.ywkd.admin.videoplayproject.utils.UIUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2017/3/14.
 */

public class VideoPlayByMPActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = "VideoPlayByMediaPlayer";

    private static final int MSG_PLAY_NEXT_VIDEO = 0;
    private static final int MSG_PLAY_VIDEO = 1;

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;

    private List<VideoFile> mVideoFileList;
    private int mCurrentVideoIndex = 0;

    private View mMainView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_PLAY_NEXT_VIDEO:
                    mCurrentVideoIndex = mCurrentVideoIndex + 1;
                    if (mCurrentVideoIndex >= mVideoFileList.size()) {
                        mCurrentVideoIndex = 0;
                    }
                    mHandler.sendEmptyMessage(MSG_PLAY_VIDEO);
                    break;
                case MSG_PLAY_VIDEO:
                    if (mVideoFileList == null || mVideoFileList.size() <= 0) {
                        ToastUtils.showToast("没有视频可播放", VideoPlayByMPActivity.this);
                        return;
                    }
                    initMediaPlayer(mVideoFileList.get(mCurrentVideoIndex));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        UIUtils.hideSystemTitleBar(this);
        UIUtils.hideSystemActionBar(this);

        super.onCreate(savedInstanceState);
        mMainView = getLayoutInflater().inflate(R.layout.activity_video_play_mediaplayer, null);
        setContentView(mMainView);

        initVideoFiles();
        initView();
    }

    /**
     * 准备视频文件数据
     */
    private void initVideoFiles() {
//        mVideoFileList = VideoFileData.initVideoFile();
        mVideoFileList = VideoFileData.scanFolderVideoFiles(FileUtils.getLocalVideoDir());
    }

    /**
     * 初始化MediaPlayer，播放视频
     *
     * @param vf
     */
    private void initMediaPlayer(final VideoFile vf) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        } else {
            mMediaPlayer.reset();
        }
        mMediaPlayer.setDisplay(mSurfaceHolder);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mHandler.sendEmptyMessage(MSG_PLAY_NEXT_VIDEO);
            }
        });

        try {
            mMediaPlayer.setDataSource(vf.getLocalPath());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    ToastUtils.showToast("开始播放第:" + vf.getId() + "个视频", VideoPlayByMPActivity.this);
                    mMediaPlayer.start();
                    mMediaPlayer.setLooping(true);
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "initMediaPlayer: error is " + e.getMessage());
        }
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.videoPlay_surfaceview);
        mSurfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        UIUtils.showWidget(findViewById(R.id.padInforLl), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler = null;
        }
        retMediaPlayer();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHandler.sendEmptyMessage(MSG_PLAY_VIDEO);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        retMediaPlayer();
    }

    /**
     * resetMediaPlayer
     */
    private void retMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
