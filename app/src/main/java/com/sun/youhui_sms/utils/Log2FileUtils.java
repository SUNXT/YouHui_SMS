package com.sun.youhui_sms.utils;

import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by SUN on 2017/6/28.
 */

public class Log2FileUtils {

    public static final String pageName = "Youhui_SMS_TEXT_LIN";
    public static void saveLog2File(String log){
        Log.i("saveLog：", log);
        String time = TextUtils.longDateToString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        String fileName = "smsLog.log";
        String path  = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + pageName +"/logs/";
        File dir = new File(path);
        if (!dir.exists()){
            if (dir.mkdirs()){
                Log.d("Sms", "创建目录成功： " + path);
            }else {
                Log.d("Sms", "创建目录失败： " + path);
            }
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(path + fileName);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(readLog());
            stringBuffer.append(time);
            stringBuffer.append(":");
            stringBuffer.append(log + '\n');
            try {
                outputStream.write(stringBuffer.toString().getBytes());
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String readLog(){
        String content = "";
        String fileName = "smsLog.log";
        String path  = Environment.getExternalStorageDirectory().getPath() + "/Android/data/Youhui_SMS/logs/";
        File dir = new File(path);
        if (!dir.exists()){
            if (dir.mkdirs()){
                Log.d("Sms", "创建目录成功： " + path);
            }else {
                Log.d("Sms", "创建目录失败： " + path);
            }
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(path + fileName);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2014];
            int len = 0;
            try {
                while ((len = fileInputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                content = byteArrayOutputStream.toString();
                fileInputStream.close();
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return content;
    }
}
