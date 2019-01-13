package com.example.a79875.todaynews.activity.collect_activity;

import com.example.a79875.todaynews.litepal.CollectNews;
import com.example.a79875.todaynews.litepal.LitePalUtil;

import java.util.List;

/**
 * Created by 你是我的 on 2019/1/12.
 */
public class CollectModel implements CollectContract.Model {
    @Override
    public void deleteAll() {
        LitePalUtil.deleteAll();
    }

    @Override
    public void deleteChoose(List<CollectNews> collectNewsList) {
        LitePalUtil.deleteChoose(collectNewsList);
    }

    @Override
    public List<CollectNews> searchAll() {
        List<CollectNews> list = LitePalUtil.searchAll();
        return list;
    }
}
