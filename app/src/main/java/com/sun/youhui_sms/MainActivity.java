package com.sun.youhui_sms;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sun.youhui_sms.receiver.SmsReceiver;

public class MainActivity extends AppCompatActivity {

    public static final int MESSAGE_CODE = 1;
    private TextView mTextMessage;

    SmsReceiver smsReceiver = new SmsReceiver();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (MESSAGE_CODE == msg.what){
                String content = (String) msg.obj;
                mTextMessage.setText(content);
            }
        }
    };

    private SmsObserve mSmsObserve = new SmsObserve(this, mHandler);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.tv_sms_content);
        Uri uri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(uri, true, mSmsObserve);
//        registerBroadcastReceiver();
    }

    private void registerBroadcastReceiver(){
        IntentFilter myIntentFilter = new IntentFilter();
        String ACTION_NAME = SmsReceiver.SMS_RECEIVED_ACTION;
        myIntentFilter.addAction(ACTION_NAME);
        registerReceiver(smsReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(smsReceiver);
        getContentResolver().unregisterContentObserver(mSmsObserve);
    }
}
