package com.sun.youhui_sms.sms;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.sun.youhui_sms.R;
import com.sun.youhui_sms.contact.Contact;
import com.sun.youhui_sms.utils.Log2FileUtils;
import com.sun.youhui_sms.utils.OkhttpUtils;
import com.sun.youhui_sms.utils.SharedUtils;
import com.sun.youhui_sms.utils.TextUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

public class SmsService extends Service {

    private String TAG = getClass().getSimpleName();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == Contact.SMS_SERVICE_CODE){
                String content = (String) msg.obj;
                ArrayList<String> numList = TextUtils.getNumList(content);
                if (numList.size() >= 2){
                    String oldTailNum = SharedUtils.readStr(SmsService.this, Contact.TAIL_NUM);
                    String oldMessageCode = SharedUtils.readStr(SmsService.this, Contact.MESSAGE_CODE);
                    String newTailNum = numList.get(0);
                    String newMessageCode = numList.get(1);

                    //判断是否已经是请求过的验证码
                    if (oldTailNum.equals(newTailNum) && oldMessageCode.equals(newMessageCode)){
                        return;
                    }

                    //保存tailNum 和 messageCode
                    SharedUtils.writeStr(SmsService.this, Contact.TAIL_NUM, newTailNum);
                    SharedUtils.writeStr(SmsService.this, Contact.MESSAGE_CODE, newMessageCode);

                    //进行网络请求
                    Map<String, String> map = new HashMap<>();
                    map.put(Contact.TAIL_NUM, newTailNum);
                    map.put(Contact.MESSAGE_CODE, newMessageCode);
                    final JSONObject jsonObject = new JSONObject(map);
                    Log2FileUtils.saveLog2File(getApplicationContext(), "进行网络请求：参数：" + jsonObject.toString());
                    OkhttpUtils.enqueue(Contact.URL_SEND_MESSAGE, jsonObject.toString(), new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i(TAG, "请求失败！" + e.getMessage());
                            Log2FileUtils.saveLog2File(getApplicationContext(), "网络请求失败！ 参数："+ jsonObject.toString() + "OkHttp onFailure()--" + e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()){
                                Log.i(TAG, "请求成功！");
                                Log2FileUtils.saveLog2File(getApplicationContext(), "网络请求成功！参数："+ jsonObject.toString());
                            }else {
                                Log.i(TAG, "请求失败！");
                                Log2FileUtils.saveLog2File(getApplicationContext(), "网络请求失败！(连接成功) 参数："+ jsonObject.toString());
                            }
                        }
                    });

                    Toast.makeText(SmsService.this, "卡尾号："+ newTailNum + " 验证码：" + newMessageCode, Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    private SmsObserve mSmsObserve = new SmsObserve(this, mHandler);

    public SmsService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initSmsObserve();
        initNotification();
    }

    private void initSmsObserve(){
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, mSmsObserve);
    }

    private void initNotification(){
        Notification.Builder builder = new Notification.Builder(this);
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(getString(R.string.notification_title));
        builder.setContentText(getString(R.string.notification_text));
        builder.setSmallIcon(R.drawable.logo_512);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo_512));
        startForeground(1, builder.getNotification());
    }


    /**
    保证service不被杀掉

    onStartCommand方法，返回START_STICKY

    StartCommond几个常量参数简介：
            1、START_STICKY
    在运行onStartCommand后service进程被kill后，那将保留在开始状态，但是不保留那些传入的intent。不久后service就会再次尝试重新创建，因为保留在开始状态，在创建     service后将保证调用onstartCommand。如果没有传递任何开始命令给service，那将获取到null的intent。
            2、START_NOT_STICKY
    在运行onStartCommand后service进程被kill后，并且没有新的intent传递给它。Service将移出开始状态，并且直到新的明显的方法（startService）调用才重新创建。因为如果没有传递任何未决定的intent那么service是不会启动，也就是期间onstartCommand不会接收到任何null的intent。
            3、START_REDELIVER_INTENT
    在运行onStartCommand后service进程被kill后，系统将会再次启动service，并传入最后一个intent给onstartCommand。直到调用stopSelf(int)才停止传递intent。如果在被kill后还有未处理好的intent，那被kill后服务还是会自动启动。因此onstartCommand不会接收到任何null的intent。
    */
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        flags = START_STICKY;//确保Service被Kill掉还能自动启动
//        return super.onStartCommand(intent, flags, startId);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mSmsObserve);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
