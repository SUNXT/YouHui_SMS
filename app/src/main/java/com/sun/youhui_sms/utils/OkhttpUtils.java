package com.sun.youhui_sms.utils;

import android.net.Uri;
import android.text.TextUtils;


import com.sun.youhui_sms.bean.KeyValue;

import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by SUN on 2017/5/6.
 */
public class OkhttpUtils {

    private static MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static MediaType TYPE_ENCODED = MediaType.parse("application/x-www-form-urlencoded");

    /**
     * 异步post获取数据，回掉操作
     * @param url 访问的链接地址
     * @param jsonParam post参数
     * @param callback 回调
     */

    public static void enqueue(String url, String jsonParam, Callback callback){
        OkHttpClient mClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(TYPE_JSON, jsonParam);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mClient.newCall(request).enqueue(callback);
    }

    public static void enqueue(String url, List<KeyValue> param, Callback callback){
        OkHttpClient mClient = new OkHttpClient();
        url = buildQueryUrl(url, param);
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(callback);
    }

    /**
     *  将键值对转码为 encoded格式
     * @param url
     * @param params
     * @return
     */
    public static String buildQueryUrl(String url, List<KeyValue> params) {
        StringBuilder queryBuilder = new StringBuilder(url);
        if (!url.contains("?")) {
            queryBuilder.append("?");
        } else if (!url.endsWith("?")) {
            queryBuilder.append("&");
        }
        List<KeyValue> queryParams = params;
        if (queryParams != null) {
            for (KeyValue kv : queryParams) {
                String name = kv.key;
                String value = kv.getValueStr();
                if (!TextUtils.isEmpty(name) && value != null) {
                    queryBuilder.append(Uri.encode(name, "utf-8")).append("=").append(Uri.encode(value, "utf-8")).append("&");
                }
            }
        }

        if (queryBuilder.charAt(queryBuilder.length() - 1) == '&') {
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        }

        if (queryBuilder.charAt(queryBuilder.length() - 1) == '?') {
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        }
        return queryBuilder.toString();
    }
}
