package com.sun.youhui_sms.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by SUN on 2017/6/27.
 */
public class ToastUtils {
    public static void showToastL(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
