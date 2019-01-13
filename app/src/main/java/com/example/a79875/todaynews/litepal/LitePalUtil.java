package com.example.a79875.todaynews.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 你是我的 on 2019/1/8.
 */

// litepal数据库操作 工具类
public class LitePalUtil {

    // 存储 收藏
    public static void save(String title, String description, String facePicUrl,
                            String url, String authorName, String type) {
        CollectNews collect = new CollectNews();
        collect.setAuthorName(authorName);
        collect.setDescription(description);
        collect.setFacePicUrl(facePicUrl);
        collect.setTitle(title);
        collect.setUrl(url);
        collect.setType(type);
        collect.save();
    }

    // 搜索单个 通过是否存入数据库 判断 是否收藏
    public static boolean searchSingle(String url) {
        List<CollectNews> collectNews = new ArrayList<>();
        if (url != null) {
            collectNews = LitePal.where("Url = ?", url).find(CollectNews.class);
            if (collectNews.size() == 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    // 查询所有收藏 用于显示
    public static List<CollectNews> searchAll(){
        List<CollectNews> collectNews = new ArrayList<>();
        collectNews = LitePal.findAll(CollectNews.class);
        return collectNews;
    }

    // 删除指定收藏
    public static void deleteChoose(List<CollectNews> collectNews){
        for (CollectNews collect : collectNews){
            deleteSingle(collect.getUrl());
        }
    }

    // 删除单个收藏
    public static void deleteSingle(String url) {
        LitePal.deleteAll(CollectNews.class, "Url = ?", url);
    }

    // 删除所有收藏
    public static void deleteAll(){
        LitePal.deleteAll(CollectNews.class);
    }
}
