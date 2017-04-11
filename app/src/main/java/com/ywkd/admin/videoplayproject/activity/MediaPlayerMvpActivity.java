package com.ywkd.admin.videoplayproject.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ywkd.admin.videoplayproject.BaseActivity;
import com.ywkd.admin.videoplayproject.MyConstants;
import com.ywkd.admin.videoplayproject.R;
import com.ywkd.admin.videoplayproject.bean.PadInfor;
import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.presenter.MediaPlayerPresenter;
import com.ywkd.admin.videoplayproject.utils.ToastUtils;
import com.ywkd.admin.videoplayproject.utils.UIUtils;
import com.ywkd.admin.videoplayproject.view.IMediaPlayerView;

/**
 * Created by admin on 2017/3/15.
 */

public class MediaPlayerMvpActivity extends BaseActivity implements IMediaPlayerView, SurfaceHolder.Callback, View.OnClickListener {

    private static final int MSG_HIDE_CONTROL_LAYOUT = 0;
    private static final int MSG_PLAY_PRE_VIDEO = 1;
    private static final int MSG_PLAY_NEXT_VIDEO = 2;
    private static final int MSG_PLAY_OR_STOP_VIDEO = 3;
    private static final int MSG_REQUEST_PAD_INFO = 4;
    private static final int MSG_UPDAT_PADINFOR = 5;
    private static final int MSG_UPDAT_VIDEO_TITLE = 6;

    private static final String TAG = "MediaPlayerMvpActivity";

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private MediaPlayerPresenter mPresenter;
    private boolean isCanPlayNext = true;
    private long mCurrentPlayPosition = 0;
    private VideoFile mCurrenteVideoFile;

    private LinearLayout mControlLl;
    private Button mStopOrStartBtn, mPreBtn, mNextBtn;
    private TextView mImeiTv, mLocationTv, mVideoTitleTv;
    private LinearLayout mProgressbar, mInforLl;
    private ImageView mQRCodeIv;
    private View mMainView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_HIDE_CONTROL_LAYOUT:
                    UIUtils.showWidget(mControlLl, false);
                    break;
                case MSG_PLAY_NEXT_VIDEO:
                    Log.d(TAG, "handleMessage: play next!");
                    mPresenter.playNextVideo(0, mSurfaceHolder);
                    break;
                case MSG_PLAY_OR_STOP_VIDEO:
                    boolean isStop = mPresenter.playOrStopVideo();
                    Log.d(TAG, "handleMessage: isStop is " + isStop);
                    if (isStop) {
                        mStopOrStartBtn.setBackgroundResource(R.drawable.music_play_ic);
                    } else {
                        mStopOrStartBtn.setBackgroundResource(R.drawable.music_puase_ic);
                    }
                    break;
                case MSG_PLAY_PRE_VIDEO:
                    mPresenter.playPreVideo(0, mSurfaceHolder);
                    break;
                case MSG_REQUEST_PAD_INFO:
                    mPresenter.requestPadInfor();
                    break;
                case MSG_UPDAT_PADINFOR:
                    if (msg.obj instanceof PadInfor) {
                        UIUtils.showWidget(mProgressbar, false);
                        UIUtils.showWidget(mImeiTv, true);
                        UIUtils.showWidget(mLocationTv, true);
                        PadInfor pi = (PadInfor) msg.obj;
                        mImeiTv.setText(pi.getIMEICode());
                        if (msg.arg1 == 1) {
                            mLocationTv.setText(pi.getPositionLatLng().getAddrStr());
                        } else {
                            mLocationTv.setText("定位失败");
                        }
                    }
                    break;
                case MSG_UPDAT_VIDEO_TITLE:
                    if (msg.obj instanceof VideoFile) {
                        mCurrenteVideoFile = (VideoFile) msg.obj;
                        Log.e(TAG, "handleMessage: current play videoTilte is " + mCurrenteVideoFile.getVideoTitle());
                        mVideoTitleTv.setText(mCurrenteVideoFile.getVideoTitle());
                        if (mCurrenteVideoFile.getQRCodeId() == 1) {
                            mQRCodeIv.setImageResource(R.drawable.one);
                        } else if (mCurrenteVideoFile.getQRCodeId() == 2) {
                            mQRCodeIv.setImageResource(R.drawable.tow);
                        } else if (mCurrenteVideoFile.getQRCodeId() == 3) {
                            mQRCodeIv.setImageResource(R.drawable.three);
                        } else if (mCurrenteVideoFile.getQRCodeId() == 4) {
                            mQRCodeIv.setImageResource(R.drawable.four);
                        } else {
                            mQRCodeIv.setImageResource(R.drawable.three);
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private Handler mCheackSystemActionBar = new Handler();
    Runnable mMyRunable = new Runnable() {
        public void run() {
            UIUtils.hideSystemActionBar(MediaPlayerMvpActivity.this);
            mCheackSystemActionBar.postDelayed(mMyRunable, 2000);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        UIUtils.hideSystemTitleBar(this);
        UIUtils.hideSystemActionBar(MediaPlayerMvpActivity.this);
        super.onCreate(savedInstanceState);
        mMainView = getLayoutInflater().inflate(R.layout.activity_video_play_mediaplayer, null);
        setContentView(mMainView);
        initPresenter();
        initView();
        mHandler.sendEmptyMessage(MSG_REQUEST_PAD_INFO);
        mCheackSystemActionBar.postDelayed(mMyRunable, 2000);
        requestPermission();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionTypeCode = event.getAction();
        switch (actionTypeCode){
            case MotionEvent.ACTION_DOWN:
                ToastUtils.showToast("按下屏幕",MediaPlayerMvpActivity.this);
                break;
            case MotionEvent.ACTION_UP:
                ToastUtils.showToast("离开屏幕",MediaPlayerMvpActivity.this);
                break;
        }
        return true;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int actionTypeCode = ev.getAction();
//        switch (actionTypeCode){
//            case MotionEvent.ACTION_DOWN:
//                ToastUtils.showToast("dispatchTouchEvent按下屏幕",MediaPlayerMvpActivity.this);
//                break;
//            case MotionEvent.ACTION_UP:
//                ToastUtils.showToast("dispatchTouchEvent离开屏幕",MediaPlayerMvpActivity.this);
//                break;
//        }
//        return ;
//    }


    /**
     * 适配android6.0权限变更
     */
    private void requestPermission() {
        mPresenter.requestPermission(MyConstants.PERMISSIONS, MyConstants.REQUEST_PERMISSION_);
    }

    private void initPresenter() {
        mPresenter = new MediaPlayerPresenter(this);
        mPresenter.initVideoFileData();
    }

    private void initView() {
        mInforLl = (LinearLayout) findViewById(R.id.padInforLl);
        UIUtils.showWidget(mInforLl, true);

        mControlLl = (LinearLayout) findViewById(R.id.controlLl);
        mPreBtn = (Button) findViewById(R.id.preBtn);
        mPreBtn.setOnClickListener(this);
        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mNextBtn.setOnClickListener(this);
        mStopOrStartBtn = (Button) findViewById(R.id.stopOrStartBtn);
        mStopOrStartBtn.setOnClickListener(this);
        mSurfaceView = (SurfaceView) findViewById(R.id.videoPlay_surfaceview);
        mSurfaceView.setOnClickListener(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mImeiTv = (TextView) findViewById(R.id.imeiTv);
        mLocationTv = (TextView) findViewById(R.id.locationTv);
        mVideoTitleTv = (TextView) findViewById(R.id.videoTitleTv);

        mProgressbar = (LinearLayout) findViewById(R.id.waitingLl);

        mQRCodeIv = (ImageView) findViewById(R.id.qrCodeIv);
        mQRCodeIv.setOnClickListener(this);

        mMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("MainActivity Clicked!", MediaPlayerMvpActivity.this);
            }
        });

    }

    @Override
    public void playVideoError(VideoFile vf, String errorMsg) {
        ToastUtils.showToast(errorMsg, MediaPlayerMvpActivity.this);
    }

    @Override
    public void currentPlayingVideo(VideoFile vf) {
        Message msg = new Message();
        msg.what = MSG_UPDAT_VIDEO_TITLE;
        msg.obj = vf;
        mHandler.sendMessage(msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void playFinish() {
        if (isCanPlayNext) {
            mPresenter.playNextVideo(0, mSurfaceHolder);
        }
    }

    @Override
    public void requestPadInforSuccess(PadInfor pi) {
        Message msg = new Message();
        msg.obj = pi;
        msg.arg1 = 1;
        msg.what = MSG_UPDAT_PADINFOR;
        mHandler.sendMessage(msg);
    }

    @Override
    public void requestLocationFail(String errorMsg, PadInfor pi) {
        Message msg = new Message();
        msg.obj = pi;
        msg.arg1 = 0;
        msg.what = MSG_UPDAT_PADINFOR;
        mHandler.sendMessage(msg);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isCanPlayNext = true;
        Log.e(TAG, "surfaceCreated: mCurrentPlayPosition is " + mCurrentPlayPosition);
        mPresenter.playVideoFile(mCurrentPlayPosition, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isCanPlayNext = false;
        mCurrentPlayPosition = mPresenter.getPlayPosition();
        mPresenter.destory(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCanPlayNext = false;
        mPresenter.destory(true);
        if (mCheackSystemActionBar != null) {
            mCheackSystemActionBar.removeCallbacks(mMyRunable);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.videoPlay_surfaceview:
            case R.layout.activity_video_play_mediaplayer:
                UIUtils.showWidget(mControlLl, true);
                if (mHandler.hasMessages(MSG_HIDE_CONTROL_LAYOUT)) {
                    mHandler.removeMessages(MSG_HIDE_CONTROL_LAYOUT);
                }
                mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL_LAYOUT, 3000);//3秒后隐藏控制栏
                break;
            case R.id.preBtn:
                mHandler.sendEmptyMessage(MSG_PLAY_PRE_VIDEO);
                break;
            case R.id.nextBtn:
                mHandler.sendEmptyMessage(MSG_PLAY_NEXT_VIDEO);
                break;
            case R.id.stopOrStartBtn:
                mHandler.sendEmptyMessage(MSG_PLAY_OR_STOP_VIDEO);
                break;
            case R.id.qrCodeIv:
                if (mCurrenteVideoFile != null) {
                    showVideoFileInfoDialog(mCurrenteVideoFile);
//                    ToastUtils.showToast(mCurrenteVideoFile.getVideoTitle(), MediaPlayerMvpActivity.this);
                }
                break;
//            case R.id.fullScreenLayout:
//                ToastUtils.showToast("FullScreen",MediaPlayerMvpActivity.this);
//                UIUtils.showWidget(mControlLl, true);
//                if (mHandler.hasMessages(MSG_HIDE_CONTROL_LAYOUT)) {
//                    mHandler.removeMessages(MSG_HIDE_CONTROL_LAYOUT);
//                }
//                mHandler.sendEmptyMessageDelayed(MSG_HIDE_CONTROL_LAYOUT, 3000);//3秒后隐藏控制栏
//                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showVideoFileInfoDialog(VideoFile vf) {
        final Dialog dialog = new Dialog(this, R.style.dialog_video_infor);
        dialog.setContentView(R.layout.dialog_video_infor);
        TextView contentTv = (TextView) dialog.findViewById(R.id.titleTv);
        contentTv.setText(vf.getVideoTitle());
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
