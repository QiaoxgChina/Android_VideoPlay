package com.ywkd.admin.videoplayproject.bean;


import com.baidu.location.BDLocation;

/**
 * Created by admin on 2017/3/16.
 */

public class PadInfor {
    private String IMEICode;//设备唯一标识码
    private BDLocation positionLatLng;//经纬度信息

    public String getIMEICode() {
        return IMEICode;
    }

    public void setIMEICode(String IMEICode) {
        this.IMEICode = IMEICode;
    }

    public BDLocation getPositionLatLng() {
        return positionLatLng;
    }

    public void setPositionLatLng(BDLocation localPositionLatLng) {
        this.positionLatLng = localPositionLatLng;
    }

}
