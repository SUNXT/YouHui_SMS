package com.sun.youhui_sms;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SUN on 2017/6/15.
 */
public class SmsObserve extends ContentObserver {

    private Context mContext;
    private Handler mHandler;

    public SmsObserve(Context context,Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        if (uri.toString().equals("content://sms//raw")){
            return;
        }

        Uri queryUri = Uri.parse("content://sms/inbox");
        Cursor cursor = mContext.getContentResolver().query(queryUri, null, null, null, "date desc");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String message = cursor.getString(cursor.getColumnIndex("body"));
                long l_date = cursor.getLong(cursor.getColumnIndex("date"));
                Date date = new Date(l_date);
                StringBuffer buffer = new StringBuffer();
                buffer.append("发送者：");
                buffer.append(address+'\n');
                buffer.append("发送时间：");
                buffer.append(date.toString()+'\n');
                buffer.append("短信内容：");
                buffer.append(message);
//                Log.d(getClass().getSimpleName(), buffer.toString());
//                String content = address + ": " + message;
                //判断是否为中石化平台发过来的验证短信，判断时间是否为两分钟内发送的
                if (Contact.FILTER_NUMBER.equals(address) && (System.currentTimeMillis() - l_date) < 120 * 1000){
                    mHandler.obtainMessage(Contact.SMS_SERVICE_CODE, message).sendToTarget();
                }
//                mHandler.obtainMessage(Contact.SMS_SERVICE_CODE, message).sendToTarget();

//                Log.i(getClass().getSimpleName(), buffer.toString());
            }
            cursor.close();
        }
    }
}
