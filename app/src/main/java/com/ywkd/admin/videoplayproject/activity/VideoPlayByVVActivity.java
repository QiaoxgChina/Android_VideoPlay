package com.ywkd.admin.videoplayproject.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

import com.ywkd.admin.videoplayproject.BaseActivity;
import com.ywkd.admin.videoplayproject.R;
import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.data.VideoFileData;
import com.ywkd.admin.videoplayproject.utils.FileUtils;
import com.ywkd.admin.videoplayproject.utils.FullScreenVideoView;
import com.ywkd.admin.videoplayproject.utils.ToastUtils;
import com.ywkd.admin.videoplayproject.utils.UIUtils;

import java.util.List;

import static com.ywkd.admin.videoplayproject.MyConstants.PARAM_VIDEO_LOCAL_PATH;

/**
 * Created by admin on 2017/3/14.
 */

public class VideoPlayByVVActivity extends BaseActivity {

    private static final String TAG = "VideoPlayByVideoView";

    private FullScreenVideoView mVideoView;
    private List<VideoFile> mVideoFileList;
    private int mCurrVideoIndx = 0;
    private TextView mEmptyLayout;
    private boolean mIsPlayOneVideo;
    private String mVideoPath;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置全屏且隐藏标题栏
        UIUtils.hideSystemTitleBar(this);
        UIUtils.hideSystemActionBar(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        initIntentData();
        initView();
        if (!mIsPlayOneVideo) {
            initVideoFiles();
        }
        playVideoFile(0);
    }

    private void initIntentData() {
        Intent i = getIntent();
        mVideoPath = i.getStringExtra(PARAM_VIDEO_LOCAL_PATH);
        if (TextUtils.isEmpty(mVideoPath)) {
            mIsPlayOneVideo = false;
        } else {
            mIsPlayOneVideo = true;
        }
    }

    /**
     * 播放视频文件
     */
    private void playVideoFile(int position) {
        if (!mIsPlayOneVideo) {
            mVideoPath = mVideoFileList.get(mCurrVideoIndx).getLocalPath();
        }
//        String videoPath = mVideoFileList.get(mCurrVideoIndx).getLocalPath();
        Log.d(TAG, "playVideoFile: path is" + mVideoPath);
        boolean isExist = FileUtils.isFileExistByPath(mVideoPath);
        if (isExist) {
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mIsActivityDestory) {
                        return;
                    }
                    mCurrVideoIndx = mCurrVideoIndx + 1;
                    if (mCurrVideoIndx >= mVideoFileList.size()) {
                        mCurrVideoIndx = 0;
                    }
                    playVideoFile(0);
                }
            });
            mVideoView.start();
            if (position != 0) {
                mVideoView.seekTo(position);
            }
        } else {
            ToastUtils.showToast("视频文件不存在", VideoPlayByVVActivity.this);
        }

    }

    /**
     * 准备视频文件数据
     */
    private void initVideoFiles() {
        mVideoFileList = VideoFileData.scanFolderVideoFiles(FileUtils.getLocalVideoDir());
    }


    /**
     * 初始化此Activity当中的View控件
     */
    private void initView() {
        mEmptyLayout = (TextView) findViewById(R.id.emptyLayout);
        mEmptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast("VideoView clicked!", VideoPlayByVVActivity.this);
//                if (mVideoView.isPlaying()) {
//                    mVideoView.pause();
//                } else {
//                    mVideoView.start();
//                }
                mediaController.show();
            }
        });

        mVideoView = (FullScreenVideoView) findViewById(R.id.videoPlay_videoview);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoView);
        mediaController.setKeepScreenOn(true);
        mVideoView.setMediaController(mediaController);
    }

    private int mPlayPosition;

    @Override
    protected void onPause() {
        mPlayPosition = mVideoView.getCurrentPosition();//在app进入后台运行前记录播放进度
        super.onPause();
    }

    @Override
    protected void onResume() {
        //当app返回前台运行的时候，从记录的播放进度播放
        if (mVideoView != null && mPlayPosition != 0) {
            playVideoFile(mPlayPosition);
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
