package com.sun.youhui_sms;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SUN on 2017/6/17.
 */
public class TextUtilsTest {

    @Test
    public void testGetCode() throws Exception {
        String []content = {"sdufyuis", "23shduh2837482sdu378", "【中国石化】您尾号为667831的加油卡正在办理自足预分配，验证码是911382"};
        for (String string: content) {
            String result = TextUtils.getCode(string);
            if (!TextUtils.isEmpty(result))
                System.out.println(result);
            else
                System.out.println(string + " 找不到");
        }
    }
}