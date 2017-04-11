package com.ywkd.admin.videoplayproject.view;

import com.ywkd.admin.videoplayproject.bean.PadInfor;
import com.ywkd.admin.videoplayproject.bean.VideoFile;

/**
 * Created by admin on 2017/3/15.
 */

public interface IMediaPlayerView {

    /**
     * 播放发生错误
     * @param vf
     * @param errorMsg
     */
    void playVideoError(VideoFile vf, String errorMsg);

    /**
     * 当前播放的视频
     * @param vf
     */
    void currentPlayingVideo(VideoFile vf);

    /**
     * 显示等待框
     */
    void showLoading();

    /**
     * 等待框消失
     */
    void hideLoading();

    /**
     * 播放下一个视频
     */
//    void playNextVideo();

    /**
     * 播放上一个视频
     */
//    void playPreVideo();

    /**
     * 播放结束
     */
    void playFinish();

    /**
     * 获取设备信息成功
     */
    void requestPadInforSuccess(PadInfor pi);

    /**
     * 获取定位信息失败
     * @param errorMsg
     * @param pi
     */
    void requestLocationFail(String errorMsg, PadInfor pi);
}
