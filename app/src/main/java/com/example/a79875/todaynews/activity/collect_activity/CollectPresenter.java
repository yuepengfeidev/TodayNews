package com.example.a79875.todaynews.activity.collect_activity;

import android.content.Context;
import android.content.Intent;

import com.example.a79875.todaynews.activity.NewsContentActivity;
import com.example.a79875.todaynews.activity.VideoContentActivity;
import com.example.a79875.todaynews.enity.Title;
import com.example.a79875.todaynews.enity.Video;
import com.example.a79875.todaynews.litepal.CollectNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 你是我的 on 2019/1/12.
 */
public class CollectPresenter implements CollectContract.Presenter {
    private Context context;
    private CollectContract.View view;
    private  CollectContract.Model model;
    private List<CollectNews> isChooseItemList = new ArrayList<>();// 存放 选中的item 信息列表
    private  int chooseCount = 0;// 选中的数量
    private  List<CollectNews> collectNews = new ArrayList<>();// 所有收藏的内容

    CollectPresenter(Context context, CollectContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void cancel() {
        chooseCount = 0;
        isChooseItemList.clear();
        view.changeChooseType(false);
        view.setTvDeleteCancelText("删除");
        view.notifyChangeAdapter();
        view.hideChoose();
    }

    @Override
    public void itemClick(int position, String type) {
        switch (type) {
            case "News":
                Title title = new Title(collectNews.get(position).getTitle()
                        , collectNews.get(position).getDescription(),
                        collectNews.get(position).getFacePicUrl(),
                        collectNews.get(position).getUrl());
                Intent intent = new Intent(context, NewsContentActivity.class);
                intent.putExtra("Title", title);
                view.startContentActivity(intent);
                break;
            case "Video":
                Video video = new Video();
                video.setVideoTitle(collectNews.get(position).getTitle());
                video.setVideoDesctiption(collectNews.get(position).getDescription());
                video.setVideoAuthor(collectNews.get(position).getAuthorName());
                video.setVideoUrl(collectNews.get(position).getUrl());
                video.setVideofacePic(collectNews.get(position).getFacePicUrl());
                Intent intent1 = new Intent(context, VideoContentActivity.class);
                intent1.putExtra("videoInfo", video);
                view.startContentActivity(intent1);
                break;
        }
    }

    @Override
    public void share(int position) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, collectNews.get(position).getUrl());
        intent.setType("text/plain");
        view.openShare(intent);
    }

    @Override
    public void itemChoose(int position, boolean isChecked) {
        if (isChecked) {// 选中，则选择数量加1
            chooseCount++;
            isChooseItemList.add(collectNews.get(position));// 添加到选择列表
        } else {
            if (chooseCount != 0) {// 取消选择，且此时选择数量不为0，则选择数量减1
                chooseCount--;
                isChooseItemList.remove(collectNews.get(position));
            }
        }

        if (chooseCount == 0) {// 选择数量为0时， 灰色 ， 不可点击
            view.setTvDeleteText("删除", "#a19c9c", false);
        } else {
            view.setTvDeleteText("删除" + "(" + chooseCount + ")", "#f44545", true);
        }
    }

    @Override
    public void tvDeleteCancel(String content) {
        if (content.equals("删除")) {
            view.changeChooseType(true);
            view.setTvDeleteCancelText("取消");
            view.showChoose();
            view.notifyChangeAdapter();
        } else {
            cancel();
        }
    }

    @Override
    public void clearAll() {
        collectNews.clear();
        model.deleteAll();
        cancel();
    }

    @Override
    public void deleteChoose() {
        for (CollectNews collect : isChooseItemList) {
            collectNews.remove(collect);
        }
        model.deleteChoose(isChooseItemList);
        view.setCollectNews(collectNews);
        cancel();
    }

    @Override
    public void searchAll(boolean isUpdate) {
        collectNews.clear();
        // 倒叙，最新收藏放在上面
        List<CollectNews> list = model.searchAll();
        for (int i = list.size() - 1; i >= 0; i--) {
            collectNews.add(list.get(i));
        }
        view.setCollectNews(collectNews);
        if (isUpdate){// 是否更新 adapter
            view.notifyChangeAdapter();
        }

    }

    @Override
    public void start() {
        model = new CollectModel();
    }
}
