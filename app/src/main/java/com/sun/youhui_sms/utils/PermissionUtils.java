package com.sun.youhui_sms.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by SUN on 2017/6/27.
 */
public class PermissionUtils {
    public static void checkReadSmsPermission(Context mContext){
        Uri queryUri = Uri.parse("content://sms/inbox");
        Cursor cursor = mContext.getContentResolver().query(queryUri, null, null, null, "date desc");
    }
}
