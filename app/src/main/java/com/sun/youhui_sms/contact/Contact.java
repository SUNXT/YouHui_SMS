package com.sun.youhui_sms.contact;

/**
 * Created by SUN on 2017/6/17.
 */
public class Contact {
    public static final int SMS_SERVICE_CODE = 1;//sms service中handler的返回码

    // TODO: 2017/6/19 发布时需要改为平台的号码
    public static final String FILTER_NUMBER = "106575650318";//中石化平台发过来的验证码的手机号码
//    public static final String FILTER_NUMBER = "15217299275";//中石化平台发过来的验证码的手机号码


//    public static final String  URL_BASE = "http://10.242.1.53:8099/"; //lc macbook pro IP
    public static final String  URL_BASE = "http://123.207.13.220:8099/";//服务器
    public static final String  URL_SEND_MESSAGE = URL_BASE + "card/sendMessage";//请求地址（发送短信）
    public static final String  MESSAGE_CODE = "messageCode";//验证码
    public static final String  TAIL_NUM = "tailNum";//尾号
}
