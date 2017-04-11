package com.ywkd.admin.videoplayproject.callback;

import com.ywkd.admin.videoplayproject.bean.PadInfor;

/**
 * Created by admin on 2017/3/16.
 */

public interface RequestPadInforCallBack {

    void requestSuccess(PadInfor pi);

    void requestLocationFail(String errorMsg, PadInfor pi);
}
