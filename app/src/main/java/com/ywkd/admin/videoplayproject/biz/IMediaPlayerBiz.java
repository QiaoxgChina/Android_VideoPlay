package com.ywkd.admin.videoplayproject.biz;

import android.view.SurfaceHolder;

import com.ywkd.admin.videoplayproject.bean.PadInfor;
import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.callback.RequestPadInforCallBack;
import com.ywkd.admin.videoplayproject.callback.VideoPlayCallBack;

import java.util.List;

/**
 * Created by admin on 2017/3/15.
 */

public interface IMediaPlayerBiz {

    /**
     * 播放视频
     *
     * @param vf
     */
    void playVideoFile(long playPosition,SurfaceHolder holder,VideoFile vf, VideoPlayCallBack callBack);

    /**
     * 重置mediaplayer
     */
    void resetMediaPlayer(boolean isActivityDestory);

    /**
     * 准备videoFile数据
     * @return
     */
    List<VideoFile> initVideoFileData();

    /**
     *
     * @return
     */
    long getPlayPosition();

    /**
     *
     */
    boolean playOrStopVideo();

    /**
     * 获取设备信息
     * @param callBack
     * @return
     */
    void requestPadInforReasult(RequestPadInforCallBack callBack);
}

