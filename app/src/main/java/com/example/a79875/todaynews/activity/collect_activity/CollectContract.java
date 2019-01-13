package com.example.a79875.todaynews.activity.collect_activity;

import android.content.Intent;

import com.example.a79875.todaynews.activity.BasePresenter;
import com.example.a79875.todaynews.activity.BaseView;
import com.example.a79875.todaynews.litepal.CollectNews;

import java.util.List;

/**
 * Created by 你是我的 on 2019/1/11.
 */
public interface CollectContract {
    interface View extends BaseView<Presenter>{
        void hideChoose();// 隐藏勾选视图
        void showChoose();// 显示勾选视图
        void startContentActivity(Intent intent);// 打开内容活动
        void setCollectNews(List<CollectNews> collectNews);// 设置收藏列表数据
        void openShare(Intent intent);// 打开分享
        void setTvDeleteCancelText(String content);// 设置右上角删除按钮内容
        void setTvDeleteText(String content,String color, boolean clickAble);// 设置右下角选择删除按钮内容
        void notifyChangeAdapter();// 提示adapter数据更新
        void changeChooseType(boolean isChoose);// 改变 选择状态
    }

    interface Presenter extends BasePresenter{
        void cancel();// 取消选择
        void itemClick(int position,String type);// 收藏列表 item点击响应
        void share(int position);// 分享
        void itemChoose(int position, boolean isChecked);// 选择item 的响应
        void tvDeleteCancel(String content);// 右上角 删除/取消 点击变化
        void clearAll();// 删除所有收藏
        void deleteChoose();// 删除选中
        void searchAll(boolean isUpdate);// 查询所有收藏. isUpdate 为是否更新 adapter，初次查询不需要更新
    }

    interface Model{
        void deleteAll();// 删除所有收藏
        void deleteChoose(List<CollectNews> collectNewsList);// 删除所有选中数据
        List<CollectNews> searchAll();// 查找所有收藏数据
    }
}
