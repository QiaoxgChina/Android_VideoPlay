package com.ywkd.admin.videoplayproject.biz;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.ywkd.admin.videoplayproject.MyApplication;
import com.ywkd.admin.videoplayproject.bean.PadInfor;
import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.callback.RequestPadInforCallBack;
import com.ywkd.admin.videoplayproject.callback.VideoPlayCallBack;
import com.ywkd.admin.videoplayproject.data.VideoFileData;
import com.ywkd.admin.videoplayproject.impl.PublicMethodImpl;
import com.ywkd.admin.videoplayproject.utils.FileUtils;
import com.ywkd.admin.videoplayproject.utils.LBSUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2017/3/15.
 */

public class MediaPlayerBiz implements IMediaPlayerBiz {

    private static final String TAG = "MediaPlayerBiz";
    private MediaPlayer mMediaPlayer;

    @Override
    public void playVideoFile(final long playPosition, SurfaceHolder holder, final VideoFile vf, final VideoPlayCallBack callBack) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        } else {
            mMediaPlayer.reset();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置声音输出通道
        mMediaPlayer.setDisplay(holder);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                callBack.playFinish();
            }
        });

        try {
            mMediaPlayer.setDataSource(vf.getLocalPath());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (playPosition == 0) {
                        callBack.playBegin(vf);
                    }
                    mMediaPlayer.start();
                    mMediaPlayer.seekTo((int) playPosition);//跳到指定位置开始播放
                    mMediaPlayer.setLooping(true);//设置单个视频循环播放
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "initMediaPlayer: error is " + e.getMessage());
            callBack.playError(vf, e.getMessage());
        }
    }

    @Override
    public void resetMediaPlayer(boolean isActivityDestory) {
        if (mMediaPlayer != null) {
            if (isActivityDestory) {
                mMediaPlayer.stop();
                mMediaPlayer.reset();
                mMediaPlayer.release();
                mMediaPlayer = null;
            } else {
                mMediaPlayer.pause();
            }
        }
    }

    @Override
    public List<VideoFile> initVideoFileData() {
        return VideoFileData.scanFolderVideoFiles(FileUtils.getLocalVideoDir());
    }

    @Override
    public long getPlayPosition() {
        if (mMediaPlayer == null)
            return 0;
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public boolean playOrStopVideo() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                return false;
            } else {
                mMediaPlayer.start();
                return true;
            }
        }
        return false;
    }

    @Override
    public void requestPadInforReasult(final RequestPadInforCallBack callback) {
        final PadInfor padInfor = new PadInfor();
        String imeiCode = PublicMethodImpl.getPadIMEI();
        padInfor.setIMEICode(imeiCode);
        final LocationClient locationClient = new LocationClient(MyApplication.getContext());
        LBSUtils.initLocation(locationClient);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                int reasultCode = location.getLocType();
                boolean isSuccess = false;
                if (reasultCode == BDLocation.TypeGpsLocation || reasultCode == BDLocation.TypeNetWorkLocation || reasultCode == BDLocation.TypeOffLineLocation) {
                    // 离线定位成功，离线定位结果也是有效的");
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
                if (isSuccess) {
                    padInfor.setPositionLatLng(location);
                    callback.requestSuccess(padInfor);
                    locationClient.stop();//定位成功之后停止定位
                } else {
                    callback.requestLocationFail("定位失败", padInfor);
                }

            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        });
        locationClient.start();//需要调用此方法开始定位
    }
}
