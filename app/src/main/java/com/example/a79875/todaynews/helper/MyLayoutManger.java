package com.example.a79875.todaynews.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by 你是我的 on 2019/1/5.
 */

// 自定义LinearLayoutManager
public class MyLayoutManger extends LinearLayoutManager implements RecyclerView.OnChildAttachStateChangeListener {
    private int mDrift;// 位移，判断移动方向,大于0往左滑，小于0往右滑

    private PagerSnapHelper pagerSnapHelper;// 实现和viewpage一样的效果，每次滑动一个item
    private OnViewPagerListener onViewPagerListener;
    int position = 0;

    public MyLayoutManger(Context context) {
        super(context);
    }

    public MyLayoutManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        pagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        view.addOnChildAttachStateChangeListener(this);
        pagerSnapHelper.attachToRecyclerView(view);// 用于捆绑view，辅助view自动对齐布局边缘
        super.onAttachedToWindow(view);
    }

    // 当item添加进来会调用这个方法
    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
        // 为了第一次进入时，没进行滑动时，让第一个item播放
        position = getPosition(view);
        if (0 == position) {
            if (onViewPagerListener != null) {
                onViewPagerListener.onPageSelected(getPosition(view), false);
            }
        }
    }

    public void setOnViewPagerListener(OnViewPagerListener onViewPagerListener) {
        this.onViewPagerListener = onViewPagerListener;
    }

    // item滑动时会调用这个方法
    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {
        // 暂停播放操作
        if (mDrift >= 0) {// 向左滑
            if (onViewPagerListener != null) {
                onViewPagerListener.onPageRelease(true, getPosition(view));
            }
        } else {// 向右滑
            if (onViewPagerListener != null) {
                onViewPagerListener.onPageRelease(false, getPosition(view));
            }
        }
    }

    /*对滑动状态监听，主要是手指释放时，找到惯性滑动停止后的item*/
    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:// item 移动停止后 执行
                View view = pagerSnapHelper.findSnapView(this);
                position = getPosition(view);
                if (onViewPagerListener != null) {
                    onViewPagerListener.onPageSelected(position, position == getItemCount() - 1);
                }
                break;
            default:// 这是直接点进去后没有切换视频，右滑退出
                View view1 = pagerSnapHelper.findSnapView(this);
                position = getPosition(view1);
                if (position == 0){// 为第一个item 回调退出
                    onViewPagerListener.exitVideo();
                }
        }

        super.onScrollStateChanged(state);
    }

    /*@Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }*/

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dx;
        View view = pagerSnapHelper.findSnapView(this);
        position = getPosition(view);
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

   /* @Override
    public boolean canScrollVertically() {
        return true;
    }*/

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

}


