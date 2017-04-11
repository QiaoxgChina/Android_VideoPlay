package com.ywkd.admin.videoplayproject.activity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

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

public class PlayMultipleVideoByVVActivity extends BaseActivity implements View.OnClickListener {

    private static final int MSG_SHOW_PROGRESSDIALOG = 0;
    private static final int MSG_HIDE_PROGRESSDIALOG = 1;

    private FullScreenVideoView mVideoView1, mVideoView2, mVideoView3, mVideoView4;
    private List<VideoFile> mVideoFiles;
    private ImageButton mSettingBtn;
    private RelativeLayout mRl1, mRl2, mRl3, mRl4;

    private LinearLayout.LayoutParams mLayoutParam;
    private LinearLayout mVideoViewLl2, mVideoViewLl1, mWaitingLayout;

    private View mView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_HIDE_PROGRESSDIALOG:
                    UIUtils.showWidget(mWaitingLayout, false);

                    break;
                case MSG_SHOW_PROGRESSDIALOG:
                    UIUtils.showWidget(mWaitingLayout, true);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UIUtils.hideSystemTitleBar(this);
        super.onCreate(savedInstanceState);
        mView = getLayoutInflater().inflate(R.layout.activity_play_multiple_video, null);
        setContentView(mView);
        mHandler.sendEmptyMessage(MSG_SHOW_PROGRESSDIALOG);
        initView();
        initVideoFiles();
        playVideoByVideoView();
        mHandler.sendEmptyMessage(MSG_HIDE_PROGRESSDIALOG);
    }

    private void playVideoByVideoView() {
        if (mVideoFiles == null || mVideoFiles.size() <= 0)
            return;
        //TODO 需要判断videofile的数量
        playVideoOnVideoView1(0);

        playOnVideoView2(0);

        playOnVideoView3(0);

        playOnVideoView4(0);
    }

    private void playOnVideoView4(int position) {
        mVideoView4.setVideoPath(mVideoFiles.get(3).getLocalPath());
        mVideoView4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mIsActivityDestory) {
                    return;
                }
                mVideoView4.setVideoPath(mVideoFiles.get(3).getLocalPath());
                mVideoView4.start();
            }
        });
        mVideoView4.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
                mVideoView4.start();
            }
        });
        if (position != 0) {
            mVideoView4.seekTo(position);
        }
    }

    private void playOnVideoView3(int position) {
        mVideoView3.setVideoPath(mVideoFiles.get(2).getLocalPath());
        mVideoView3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mIsActivityDestory) {
                    return;
                }
                mVideoView3.setVideoPath(mVideoFiles.get(2).getLocalPath());
                mVideoView3.start();
            }
        });
        mVideoView3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
                mVideoView3.start();
            }
        });
        mVideoView3.start();
        if (position != 0) {
            mVideoView3.seekTo(position);
        }
    }

    private void playOnVideoView2(int position) {
        mVideoView2.setVideoPath(mVideoFiles.get(1).getLocalPath());
        mVideoView2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mIsActivityDestory) {
                    return;
                }
                mVideoView2.setVideoPath(mVideoFiles.get(1).getLocalPath());
                mVideoView2.start();
            }
        });
        mVideoView2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
                mVideoView2.start();
            }
        });
        if (position != 0) {
            mVideoView2.seekTo(position);
        }
    }

    private void playVideoOnVideoView1(int position) {
        mVideoView1.setVideoPath(mVideoFiles.get(0).getLocalPath());
        mVideoView1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mIsActivityDestory) {
                    return;
                }
                mVideoView1.setVideoPath(mVideoFiles.get(0).getLocalPath());
                mVideoView1.start();
            }
        });
        mVideoView1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0, 0);
                mVideoView1.start();
            }
        });

        if (position != 0) {
            mVideoView1.seekTo(position);
        }
    }

    private int mVideoViewPosition1 = 0;
    private int mVideoViewPosition2 = 0;
    private int mVideoViewPosition3 = 0;
    private int mVideoViewPosition4 = 0;

    @Override
    protected void onPause() {
        mVideoViewPosition1 = mVideoView1.getCurrentPosition();
        mVideoViewPosition2 = mVideoView2.getCurrentPosition();
        mVideoViewPosition3 = mVideoView3.getCurrentPosition();
        mVideoViewPosition4 = mVideoView4.getCurrentPosition();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mVideoView1 != null && mVideoViewPosition1 != 0) {
            playVideoOnVideoView1(mVideoViewPosition1);
        }

        if (mVideoView2 != null && mVideoViewPosition2 != 0) {
            playOnVideoView2(mVideoViewPosition2);
        }

        if (mVideoView3 != null && mVideoViewPosition3 != 0) {
            playOnVideoView3(mVideoViewPosition3);
        }

        if (mVideoView4 != null && mVideoViewPosition4 != 0) {
            playOnVideoView4(mVideoViewPosition4);
        }
        super.onResume();
    }

    private void initVideoFiles() {
        mVideoFiles = VideoFileData.scanFolderVideoFiles(FileUtils.getLocalVideoDir());
    }

    private void initView() {
        mWaitingLayout = (LinearLayout) findViewById(R.id.waitingLayout);

        mVideoView1 = (FullScreenVideoView) findViewById(R.id.videoView1);
        mVideoView2 = (FullScreenVideoView) findViewById(R.id.videoView2);
        mVideoView3 = (FullScreenVideoView) findViewById(R.id.videoView3);
        mVideoView4 = (FullScreenVideoView) findViewById(R.id.videoView4);
        mSettingBtn = (ImageButton) findViewById(R.id.settingBtn);

        mRl1 = (RelativeLayout) findViewById(R.id.videoView1Rl);
        mRl2 = (RelativeLayout) findViewById(R.id.videoView2Rl);
        mRl3 = (RelativeLayout) findViewById(R.id.videoView3Rl);
        mRl4 = (RelativeLayout) findViewById(R.id.videoView4Rl);

        mVideoViewLl2 = (LinearLayout) findViewById(R.id.videoViewLl2);
        mVideoViewLl1 = (LinearLayout) findViewById(R.id.videoViewLl1);

        mVideoView1.setOnClickListener(this);
        mVideoView2.setOnClickListener(this);
        mVideoView3.setOnClickListener(this);
        mVideoView4.setOnClickListener(this);
        mSettingBtn.setOnClickListener(this);
        mRl1.setOnClickListener(this);
        mRl2.setOnClickListener(this);
        mRl3.setOnClickListener(this);
        mRl4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.settingBtn:
                showSelectPlayModeDialog();
                break;
            case R.id.videoView1Rl:
                goToPlayVideo(1);
                break;
            case R.id.videoView2Rl:
                goToPlayVideo(2);
                break;
            case R.id.videoView3Rl:
                goToPlayVideo(3);
                break;
            case R.id.videoView4Rl:
                goToPlayVideo(4);
                break;
        }

    }

    private void goToPlayVideo(int videoIndx) {
        Intent i = new Intent(PlayMultipleVideoByVVActivity.this, VideoPlayByVVActivity.class);
        i.putExtra(PARAM_VIDEO_LOCAL_PATH, mVideoFiles.get(videoIndx).getLocalPath());
        startActivity(i);
    }

    private int mPlayType = 0;

    private void showSelectPlayModeDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog_video_infor);
        dialog.setContentView(R.layout.dialog_select_play_mode);
        RadioGroup mRadioGroup = (RadioGroup) dialog.findViewById(R.id.mRadioGroup);
        RadioButton towRb = (RadioButton) dialog.findViewById(R.id.towRb);
        RadioButton fourRb = (RadioButton) dialog.findViewById(R.id.fourRb);
        if (mPlayType == 0) {
            fourRb.setChecked(true);
            towRb.setChecked(false);
        } else {
            fourRb.setChecked(false);
            towRb.setChecked(true);
        }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                dialog.dismiss();
                switch (checkedId) {
                    case R.id.towRb:
                        mPlayType = 1;
                        mLayoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        mVideoViewLl1.setLayoutParams(mLayoutParam);
                        UIUtils.showWidget(mVideoViewLl2, false);
                        break;
                    case R.id.fourRb:
                        mHandler.sendEmptyMessage(MSG_SHOW_PROGRESSDIALOG);
                        mPlayType = 0;
                        //TODO 待改进
                        setContentView(R.layout.activity_play_multiple_video);
                        initView();
                        playVideoByVideoView();
                        mHandler.sendEmptyMessage(MSG_HIDE_PROGRESSDIALOG);
                        break;
                }
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
