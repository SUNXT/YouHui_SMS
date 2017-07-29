package com.sun.youhui_sms;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.example.crashhandler.CrashHandler;
import com.example.crashhandler.CrashInfo;
import com.sun.youhui_sms.bean.YouHuiSmsCrashInfo;

import java.io.File;
import java.util.HashMap;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sunxuedian on 2017/7/29.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "5b30fc8414d43b08361f91f8353be5a6");
        CrashHandler crashHandler = CrashHandler.getInstance(Environment.getExternalStorageDirectory().getPath() + File.separator + "YouHui_SMS");
        crashHandler.init(this);
        crashHandler.setOnCrashUploader(new CrashHandler.OnCrashUploader() {
            @Override
            public void uploadCrashInfo(HashMap<String, String> hashMap) {
                YouHuiSmsCrashInfo info = new YouHuiSmsCrashInfo();
                info.setBuildInfo(hashMap.get(CrashInfo.BUILD_INFO));
                info.setExceptionInfo(hashMap.get(CrashInfo.EXCEPTION_INFO));
                info.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            Log.d("Bmob save: ", "上传成功");
                        }else {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
