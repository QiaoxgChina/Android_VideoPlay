package com.ywkd.admin.videoplayproject.utils;

import com.ywkd.admin.videoplayproject.MyConstants;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by admin on 2017/3/15.
 */

public class VideoFileFilter implements FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory())
            return false;
        else {
            String name = f.getName();
            if (name.endsWith(MyConstants.VIDEO_FILE_MP4_SUFFIX) || name.endsWith(MyConstants.VIDEO_FILE_MKV_SUFFIX))
                return true;
            else
                return false;
        }
    }
}
