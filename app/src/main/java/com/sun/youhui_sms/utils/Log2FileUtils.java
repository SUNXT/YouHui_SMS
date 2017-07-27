package com.sun.youhui_sms.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * Created by SUN on 2017/6/28.
 */

public class Log2FileUtils {

    public static final String pageName = "Youhui_SMS";
    public static final String path  = Environment.getExternalStorageDirectory().getPath() + "/" + pageName +"/logs/";
    public static final String fileName = "log.txt";

    public static void saveLog2File(Context context, String log){
        Log.i("saveLog：", log);
        saveDataToFile(context, log, fileName);
    }

    private static void saveFileOne(Context context, String log){
        String time = TextUtils.longDateToString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
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
                while (true){
                    len = fileInputStream.read(buffer);
                    if (len == -1){
                        break;
                    }
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
        Log.i("readLog", content);
        return content;
    }

    private static String readFile(){
        StringBuilder content = new StringBuilder();
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(path + fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            if (bufferedReader != null)
            {
                try
                {
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        Log.i("readFile", content.toString());
        return content.toString();
    }

    /**
     * 将数据存到文件中
     *
     * @param context context
     * @param data 需要保存的数据
     * @param fileName 文件名
     */
    private static void saveDataToFile(Context context, String data, String fileName)
    {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try
        {
            /**
             * "data"为文件名,MODE_PRIVATE表示如果存在同名文件则覆盖，
             * 还有一个MODE_APPEND表示如果存在同名文件则会往里面追加内容
             */
            fileOutputStream = context.openFileOutput(fileName,
                    Context.MODE_APPEND);
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(data);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                if (bufferedWriter != null)
                {
                    bufferedWriter.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
