package com.example.a79875.todaynews.Util;

import com.example.a79875.todaynews.gson.NewsList;
import com.google.gson.Gson;

// 将请求到的数据解析成实体类对象
public class Utility {
    public static NewsList parseJsonWithGson(final String  requestText){
        Gson gson = new Gson();
        return gson.fromJson(requestText,NewsList.class);
    }
}
