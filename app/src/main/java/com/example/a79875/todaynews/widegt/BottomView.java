package com.example.a79875.todaynews.widegt;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


import com.example.a79875.todaynews.enity.BotBean;

import java.util.ArrayList;


// 该控件为地图导航栏图标的载体
public class BottomView extends LinearLayout {

    private ViewPager vp;
    private BottomPageChangeListener bottomPageChangeListener;

    public BottomView(Context context) {
        super(context);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // 同tablayout用法相似，与ViewPager进行绑定
    public void setViewPager(ViewPager viewPager, ArrayList<BotBean> botBeans,
                             final BottomPageChangeListener bottomPageChangeListener){
        if (viewPager == null){
            return;
        }

        vp = viewPager;
        this.bottomPageChangeListener = bottomPageChangeListener;
        initTabView(botBeans);

        // 设置viewPager的点击事件
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            public void onPageSelected(int position){
                for (int i = 0;i < getChildCount(); i++){// 设置被选择的为true
                    getChildAt(i).setSelected((position == i) ? true : false);
                }
                if (bottomPageChangeListener != null){
                    bottomPageChangeListener.onBottomPageChangeListener(position);
                }
            }
        });
    }

    // 初始化底部导航栏，viewpager有几页，就创建几个图标
    private void initTabView(ArrayList<BotBean> botBeans) {
        setGravity(HORIZONTAL);// 设置为水平
        for (int i = 0; i < botBeans.size(); i++){
            BotBean bean = botBeans.get(i);
            TabView tabView = new TabView(getContext(),bean);
            LayoutParams params = new LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT,
                    ViewPager.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            params.gravity = Gravity.CENTER;
            tabView.setLayoutParams(params);

            // 为每个view设置点击事件,点击view就会跳到相应的页面上
            final int  finalI = i;
            tabView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    vp.setCurrentItem(finalI,false);// false为取消滑动效果
                }
            });
            // 设置第一个view一开始就是选中状态
            if (i == 0) {
                tabView.setSelected(true);
                // 因为初始化时，onPageSelected没有得到执行，所以主动去调用回调方法
                if (bottomPageChangeListener != null){
                    bottomPageChangeListener.onBottomPageChangeListener(i);
                }
            }
            addView(tabView);
        }
    }

    // 提供接口回调，每次滑动通知外界
    public interface BottomPageChangeListener{
        void onBottomPageChangeListener(int position);
    }

}
