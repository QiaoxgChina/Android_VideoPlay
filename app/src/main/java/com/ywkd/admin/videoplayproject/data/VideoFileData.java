package com.ywkd.admin.videoplayproject.data;

import android.text.TextUtils;
import android.util.Log;

import com.ywkd.admin.videoplayproject.bean.VideoFile;
import com.ywkd.admin.videoplayproject.utils.FileUtils;
import com.ywkd.admin.videoplayproject.utils.VideoFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/3/14.
 */

public class VideoFileData {

    private static final String TAG = "VideoFileData";

    private static List<VideoFile> mVideoFileList;

    /**
     * 准备videofile数据
     *
     * @return
     */
    public static List<VideoFile> initVideoFile() {

        if (mVideoFileList == null || mVideoFileList.size() <= 0) {
            mVideoFileList = new ArrayList<>();
//            VideoFile vf1 = new VideoFile();
//            vf1.setId(1);
//            vf1.setVideoTitle("VideoOne");
//            vf1.setVideoDownloadUrl("");
//            vf1.setLocalPath(FileUtils.getLocalVideoDir() + "v1.mp4");
//            mVideoFileList.add(vf1);

            VideoFile vf2 = new VideoFile();
            vf2.setId(2);
            vf2.setVideoTitle("VideoTow");
            vf2.setVideoDownloadUrl("");
            vf2.setLocalPath(FileUtils.getLocalVideoDir() + "v2.mp4");
            mVideoFileList.add(vf2);

            VideoFile vf3 = new VideoFile();
            vf3.setId(3);
            vf3.setVideoTitle("VideoThree");
            vf3.setVideoDownloadUrl("");
            vf3.setLocalPath(FileUtils.getLocalVideoDir() + "v3.mp4");
            mVideoFileList.add(vf3);

            VideoFile vf4 = new VideoFile();
            vf4.setId(4);
            vf4.setVideoTitle("VideoFour");
            vf4.setVideoDownloadUrl("");
            vf4.setLocalPath(FileUtils.getLocalVideoDir() + "v4.mp4");
            mVideoFileList.add(vf4);
        }
        return mVideoFileList;
    }

    /**
     * 扫描指定目录下的视频文件（.mp4）
     * @param folderPath
     * @return
     */
    public static List<VideoFile> scanFolderVideoFiles(String folderPath) {
        if (TextUtils.isEmpty(folderPath))
            return null;

//        if (mVideoFileList != null && mVideoFileList.size() > 0){
//            return mVideoFileList;
//        }
        mVideoFileList = new ArrayList<>();
        File f = new File(folderPath);
        if (f.isDirectory()) {
            File[] files = f.listFiles(new VideoFileFilter());
            int length = files.length;
            for (int i = 0; i < length; i++) {
                VideoFile vf = new VideoFile();
                vf.setLocalPath(files[i].getPath());
                vf.setId(i+1);
                vf.setQRCodeId(i+1);
                vf.setVideoTitle(files[i].getName());
                mVideoFileList.add(vf);
            }
            return mVideoFileList;
        }
        return null;
    }

}
