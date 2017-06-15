package com.sun.youhui_sms;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

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
        String code = "";
        Cursor cursor = mContext.getContentResolver().query(queryUri, null, null, null, "date desc");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String message = cursor.getString(cursor.getColumnIndex("body"));
                String content = address + ": " + message;
                mHandler.obtainMessage(MainActivity.MESSAGE_CODE, content).sendToTarget();
                Log.e(getClass().getSimpleName(), address+": "+ message);
            }

            cursor.close();
        }
    }
}
