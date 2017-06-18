package com.sun.youhui_sms;

import com.sun.youhui_sms.utils.TextUtils;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by SUN on 2017/6/17.
 */
public class TextUtilsTest {

    @Test
    public void testGetCode() throws Exception {
        String []content = {"sdufyuis", "23shduh2837482sdu378", "【中国石化】您尾号为667831的加油卡正在办理自足预分配，验证码是911382"};
        for (String string: content) {
            ArrayList<String> result = TextUtils.getNumList(string);
            for (String item: result){
                System.out.println(item);
            }
        }
    }
}