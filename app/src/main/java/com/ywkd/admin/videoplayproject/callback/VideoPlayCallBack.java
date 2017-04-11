package com.ywkd.admin.videoplayproject.callback;

import com.ywkd.admin.videoplayproject.bean.VideoFile;

/**
 * Created by admin on 2017/3/15.
 */

public interface VideoPlayCallBack {

    /**
     * 开始播放
     * @param vf
     */
    void playBegin(VideoFile vf);

    /**
     * 播放错误
     * @param vf
     * @param errorMsg
     */
    void playError(VideoFile vf, String errorMsg);

    /**
     * 播放结束
     */
    void playFinish();
}
