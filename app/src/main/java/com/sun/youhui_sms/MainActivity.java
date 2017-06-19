package com.sun.youhui_sms;

import android.Manifest;
import android.content.Intent;

import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sun.youhui_sms.sms.SmsService;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {

//    public static final int MESSAGE_CODE = 1;
//    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTextMessage = (TextView) findViewById(R.id.tv_sms_content);
//        Uri uri = Uri.parse("content://sms");
//        getContentResolver().registerContentObserver(uri, true, mSmsObserve);
//        registerBroadcastReceiver();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (PERMISSION_GRANTED != checkSelfPermission(Manifest.permission_group.SMS)){
//                //动态申请
//                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 1);
//            }
//        }
        startService(new Intent(getApplicationContext(), SmsService.class));
    }

}
