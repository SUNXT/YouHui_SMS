package com.sun.youhui_sms;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SUN on 2017/6/17.
 */
public class TextUtils {

    /**
     * 获取文本中的验证码
     * @param content
     * @return
     */
    public static String getCode(String content){
        Pattern pattern = Pattern.compile("\\d{4,6}(?!\\d)");
        Matcher matcher = pattern.matcher(content);
        String result = "";
        while (matcher.find()){
//            System.out.println(matcher.group());
            result = matcher.group();
        }
        return result;
    }

    public static boolean isEmpty(String str){
        return  (str == null) || ("".equals(str.trim()));
    }
}
