package com.sun.youhui_sms.utils;

import android.util.Patterns;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public static ArrayList<String> getNumList(String content){
        Pattern pattern = Pattern.compile("\\d{4,6}(?!\\d)");
        Matcher matcher = pattern.matcher(content);
        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()){
//            System.out.println(matcher.group());
            result.add(matcher.group());
        }
        return result;
    }

    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static String longDateToString(long date, String format){
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(d);
        return str;
    }

    public static boolean isEmpty(String str){
        return  (str == null) || ("".equals(str.trim()));
    }
}
