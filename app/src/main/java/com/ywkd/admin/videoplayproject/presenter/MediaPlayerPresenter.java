package com.ywkd.admin.videoplayproject.presenter;

import android.view.SurfaceHolder;

import com.ywkd.admin.videoplayproject.bean.PadInfor;
import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.biz.IMediaPlayerBiz;
import com.ywkd.admin.videoplayproject.biz.MediaPlayerBiz;
import com.ywkd.admin.videoplayproject.callback.RequestPadInforCallBack;
import com.ywkd.admin.videoplayproject.callback.VideoPlayCallBack;
import com.ywkd.admin.videoplayproject.view.IMediaPlayerView;

import java.util.List;

/**
 * Created by admin on 2017/3/15.
 */

public class MediaPlayerPresenter {

    private IMediaPlayerView iMediaPlayerActivity;
    private IMediaPlayerBiz iMediaPlayerBiz;

    private List<VideoFile> mVideoFileList;
    private int mCurrentVideoIndex = 0;
    private PadInfor mPadInfor;

    public MediaPlayerPresenter(IMediaPlayerView iView) {
        this.iMediaPlayerActivity = iView;
        this.iMediaPlayerBiz = new MediaPlayerBiz();
    }

    /**
     * 准备数据
     */
    public void initVideoFileData() {
        mVideoFileList = iMediaPlayerBiz.initVideoFileData();
    }

    /**
     * 播放视频
     *
     * @param holder
     */
    public void playVideoFile(long playPosition, final SurfaceHolder holder) {
        if (mVideoFileList == null || mVideoFileList.size() <= 0) {
            iMediaPlayerActivity.playVideoError(null, "没有可播放的视频");
            return;
        }
        VideoFile vf = mVideoFileList.get(mCurrentVideoIndex);
        iMediaPlayerBiz.playVideoFile(playPosition, holder, vf, new VideoPlayCallBack() {
            @Override
            public void playBegin(VideoFile vf) {
                iMediaPlayerActivity.currentPlayingVideo(vf);
            }

            @Override
            public void playError(VideoFile vf, String errorMsg) {
                iMediaPlayerActivity.playVideoError(vf, errorMsg);
            }

            @Override
            public void playFinish() {
                iMediaPlayerActivity.playFinish();
//                playNextVideo(holder);
            }
        });
    }

    /**
     * 获取播放进度
     *
     * @return
     */
    public long getPlayPosition() {
        return iMediaPlayerBiz.getPlayPosition();
    }

    /**
     * 播放下一个video
     *
     * @param playPosition
     * @param holder
     */
    public void playNextVideo(long playPosition, SurfaceHolder holder) {
        mCurrentVideoIndex = mCurrentVideoIndex + 1;
        if (mVideoFileList != null && mCurrentVideoIndex >= mVideoFileList.size()) {
            mCurrentVideoIndex = 0;
        }
        playVideoFile(playPosition, holder);
    }

    /**
     * 播放上一个video
     *
     * @param playPosition
     * @param holder
     */
    public void playPreVideo(long playPosition, SurfaceHolder holder) {
        mCurrentVideoIndex = mCurrentVideoIndex - 1;
        if (mVideoFileList != null && mCurrentVideoIndex < 0) {
            mCurrentVideoIndex = mVideoFileList.size() - 1;
        }
        playVideoFile(playPosition, holder);
    }

    /**
     * 释放资源
     *
     * @param isActivityDestory
     */
    public void destory(boolean isActivityDestory) {
        //TODO 释放资源
        if (isActivityDestory) {
            mCurrentVideoIndex = 0;
            if (mVideoFileList != null) {
                mVideoFileList.clear();
                mVideoFileList = null;
            }
        }
        iMediaPlayerBiz.resetMediaPlayer(isActivityDestory);
    }

    /**
     * 暂停或播放
     *
     * @return
     */
    public boolean playOrStopVideo() {
        return iMediaPlayerBiz.playOrStopVideo();
    }

    /**
     * 获取设备的信息：设备号和定位信息
     */
    public void requestPadInfor() {
        if (mPadInfor != null) {
            iMediaPlayerActivity.requestPadInforSuccess(mPadInfor);
            return;
        }
        iMediaPlayerBiz.requestPadInforReasult(new RequestPadInforCallBack() {
            @Override
            public void requestSuccess(PadInfor pi) {
                mPadInfor = pi;
                iMediaPlayerActivity.requestPadInforSuccess(mPadInfor);
            }

            @Override
            public void requestLocationFail(String errorMsg, PadInfor pi) {
                iMediaPlayerActivity.requestLocationFail(errorMsg, pi);
            }
        });
    }

    public void requestPermission(String[] permissions, int requestPermission) {
    }
}
