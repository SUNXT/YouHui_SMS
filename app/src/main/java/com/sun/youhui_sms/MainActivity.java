package com.sun.youhui_sms;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sun.youhui_sms.receiver.SmsReceiver;

public class MainActivity extends AppCompatActivity {

    SmsReceiver smsReceiver = new SmsReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        String ACTION_NAME = SmsReceiver.SMS_RECEIVED_ACTION;
        myIntentFilter.addAction(ACTION_NAME);
        registerReceiver(smsReceiver, myIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
