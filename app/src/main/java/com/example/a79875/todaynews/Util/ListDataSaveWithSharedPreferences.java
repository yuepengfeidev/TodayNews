package com.example.a79875.todaynews.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


// SharedPreferences存储新闻类别记录
public class ListDataSaveWithSharedPreferences {

    // 存储list
    public static void saveList(Context context, String key,  List<String> list){
        SharedPreferences.Editor editor = context.getSharedPreferences(key,MODE_PRIVATE).edit();
        editor.putInt(key+"nums" , list.size());// 存入大小
        for (int i = 0 ; i < list.size(); i++){// 一次存入list中string
            editor.putString(key+ i,list.get(i));
        }
        editor.apply();
    }

    // 获取存储的list
    public static List<String> getList(Context context, String key){
        List<String> list = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,MODE_PRIVATE);
        int nums = sharedPreferences.getInt(key+"nums" ,0);
        for (int i = 0; i < nums; i++){
            String s = sharedPreferences.getString(key+i,null);
            list.add(s);
        }
        return list;
    }

    // 存储是否时第一次，是第一次，则存list数据进sharedpreferences，不是则从中读取list数据
    public static void saveIsFirst(Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("isFirst",MODE_PRIVATE).edit();
        editor.putBoolean("isFirst",false);
        editor.apply();
    }

    public static boolean getIsFirst(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("isFirst",MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean("isFirst",true);// 如果取不到值则为true，为第一次
        return isFirst;
    }
}
