package com.ywkd.admin.videoplayproject.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.ywkd.admin.videoplayproject.MyConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by admin on 2017/3/14.
 */

public class FileUtils {

    /**
     * 获取手机内置存储路径
     * @return
     */
    public static String getExternalStorageDirectory(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 根据路径判断文件是否存在
     * @param path
     * @return
     */
    public static boolean isFileExistByPath(String path){
        if (TextUtils.isEmpty(path)){
            return false;
        }
        File f = new File(path);
        if (f.exists() && f.isFile()){
            return true;
        }
        return false;
    }

    /**
     * 获得本地保存视频的路径
     * @return
     */
    public static String getLocalVideoDir() {
        String result = null;
        File videoDirFile = null;
        boolean error = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            result = getExternalStorageDirectory();
            result += File.separator + MyConstants.LOCAL_VIDEO_FILE_PATH;
            videoDirFile = new File(result);
            if (!videoDirFile.exists() && !videoDirFile.mkdirs()) {
                error = true;
            }
        }
        if (error)
            return null;
        return result;
    }

    /**
     * 获得系统的路径
     * @return
     */
    public static String getLocalDCIMDir() {
        String result = null;
        File videoDirFile = null;
        boolean error = false;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            result = getExternalStorageDirectory();
            result += File.separator + MyConstants.DCIM_CAMERA_PATH;
            videoDirFile = new File(result);
            if (!videoDirFile.exists() && !videoDirFile.mkdirs()) {
                error = true;
            }
        }
        if (error)
            return null;
        return result;
    }

}
