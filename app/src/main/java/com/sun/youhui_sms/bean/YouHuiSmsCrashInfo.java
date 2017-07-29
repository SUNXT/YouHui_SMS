package com.sun.youhui_sms.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by sunxuedian on 2017/7/29.
 */

public class YouHuiSmsCrashInfo extends BmobObject{
    private String exceptionInfo;
    private String buildInfo;

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public String getBuildInfo() {
        return buildInfo;
    }

    public void setBuildInfo(String buildInfo) {
        this.buildInfo = buildInfo;
    }
}
