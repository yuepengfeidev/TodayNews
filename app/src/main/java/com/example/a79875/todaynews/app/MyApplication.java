package com.example.a79875.todaynews.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;

import com.example.a79875.todaynews.Util.MesureWidgetHeight;

import org.litepal.LitePal;

import cn.jzvd.JZVideoPlayerStandard;

// 自定义的application
public class MyApplication extends Application {

    private static Context context;
    private static int tablayoutHeight;
    private static int bottomViewHeight;
    private static int toolbarHeight;
    private static int taskHeight;// 手机任务栏的高度
    private static int softInputHeight;// 软键盘的高度
    private static int videoViewHeight;// 视频播放器高度
    private static int authorInfoLayoutHeiht;// 作者信息栏高度

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(context);
        // 默认设置为日间模式
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);

        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            taskHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
    }

    public static Context getContext() {
        return context;
    }

    public static int getTablayoutHeight() {
        return tablayoutHeight;
    }

    public static int getToolbarHeight() {
        return toolbarHeight;
    }

    public static void setTablayoutHeight(TabLayout tablayout) {
        MyApplication.tablayoutHeight =  MesureWidgetHeight.mesureHeight(tablayout);;
    }

    public static void setToolbarHeight(Toolbar toolbar) {
        MyApplication.toolbarHeight = MesureWidgetHeight.mesureHeight(toolbar);
    }

    public static int getBottomViewHeight() {
        return bottomViewHeight;
    }

    public static void setBottomViewHeight(int bottomViewHeight) {
        MyApplication.bottomViewHeight = bottomViewHeight;
    }

    public static int getTaskHeight() {
        return taskHeight;
    }

    // 获取手机软键盘高度
    public static void setSoftInputHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int screenHeight = activity.getWindow().getDecorView().getRootView().getHeight();
        softInputHeight = screenHeight - rect.bottom;
        if (Build.VERSION.SDK_INT >= 20){
            softInputHeight= softInputHeight - getSoftButtonsBarHeight(activity);
        }
    }

    // 当有底部虚拟按键时，获取其高度
    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics( metrics);
        int usableHeight = metrics.heightPixels;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight){
            return realHeight - usableHeight;
        }
        return 0;
    }

    public static int getSoftInputHeight() {
        return softInputHeight;
    }

    public static int getVideoViewHeight() {
        return videoViewHeight;
    }

    public static void setVideoViewHeight(JZVideoPlayerStandard jzVideoPlayerStandard) {
        MyApplication.videoViewHeight = MesureWidgetHeight.mesureHeight(jzVideoPlayerStandard);
    }

    public static int getAuthorInfoLayoutHeiht() {
        return authorInfoLayoutHeiht;
    }

    public static void setAuthorInfoLayoutHeiht(RelativeLayout authorInfoLayout) {
        MyApplication.authorInfoLayoutHeiht = MesureWidgetHeight.mesureHeight(authorInfoLayout);
    }
}
