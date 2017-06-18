package com.sun.youhui_sms;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sun.youhui_sms.sms.SmsService;


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
        startService(new Intent(getApplicationContext(), SmsService.class));
    }

}
