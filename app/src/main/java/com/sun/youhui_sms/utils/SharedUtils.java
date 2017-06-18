package com.sun.youhui_sms.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SUN on 2017/6/19.
 */
public class SharedUtils {

    public static String NAME = "Youhui_SMS";

    public static String readStr(Context context, String tagName){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(tagName, "");
        return value;
    }
    public static void writeStr(Context context, String tagName, String value){
        if (context == null){
            return;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(tagName,value);
        editor.commit();
    }
}
