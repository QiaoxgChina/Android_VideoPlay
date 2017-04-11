package com.ywkd.admin.videoplayproject.bean;

/**
 * Created by admin on 2017/3/13.
 */

public class VideoFile {

    private int id;
    private String videoTitle;//视频标题
    private String videoDownloadUrl;//视频的下载地址
    private String localPath;//视频在本地的存放位置
    private int QRCodeId;

    public int getQRCodeId() {
        return QRCodeId;
    }

    public void setQRCodeId(int QRCodeId) {
        this.QRCodeId = QRCodeId;
    }

    public String getVideoDownloadUrl() {
        return videoDownloadUrl;
    }

    public void setVideoDownloadUrl(String videoDownloadUrl) {
        this.videoDownloadUrl = videoDownloadUrl;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

}
