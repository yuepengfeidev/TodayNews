package com.example.a79875.todaynews.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

// 请求数据
public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
