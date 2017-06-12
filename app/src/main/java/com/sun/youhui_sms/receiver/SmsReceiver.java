package com.sun.youhui_sms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by SUN on 2017/6/12.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //判断广播消息
        if (SMS_RECEIVED_ACTION.equals(action)){
            Bundle data = intent.getExtras();
            //如果不为空
            if (data != null){
                //将pdus里面的内容转化成Object[]数组
                Object pdusData[] = (Object[]) data.get("pdus");// pdus ：protocol data unit  ：
                //解析短信
                SmsMessage[] msg = new SmsMessage[pdusData.length];
                for (int i = 0;i < msg.length;i++){
                    byte pdus[] = (byte[]) pdusData[i];
                    msg[i] = SmsMessage.createFromPdu(pdus);
                }
                StringBuffer content = new StringBuffer();//获取短信内容
                StringBuffer phoneNumber = new StringBuffer();//获取地址
                //分析短信具体参数
                for (SmsMessage temp : msg){
                    content.append(temp.getMessageBody());
                    phoneNumber.append(temp.getOriginatingAddress());
                }
                Log.d("SUN_SMS", "发送者号码："+phoneNumber.toString()+"  短信内容："+content.toString());
            }
        }
    }
}
