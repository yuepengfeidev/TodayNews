package com.example.a79875.todaynews.widegt;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a79875.todaynews.enity.BotBean;


// 图标 + 文字的 底部按钮
public class TabView extends LinearLayout {
    BotBean botBean;
    private TextView iconName;
    private ImageView iconImage;
    public TabView(Context context, BotBean botBean) {
        super(context);
        this.botBean = botBean;
        initView();
    }

    private void initView() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        // 添加小图标
        iconImage = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(45,45);// 定义布局的宽高
        iconImage.setLayoutParams(layoutParams);// 设置图标的大小
        iconImage.setImageResource(botBean.getUnCheckedIcon());// 获取图片

        // 设置的图标为灰色
        Drawable drawable = getContext().getResources().getDrawable(botBean.getUnCheckedIcon());
        Drawable wrapDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrapDrawable, ColorStateList.valueOf(Color.GRAY));
        iconImage.setImageDrawable(wrapDrawable);
        addView(iconImage);// 添加图标图片

        // 设置图标标题并添加
        iconName = new TextView(getContext());
        LayoutParams titleParams = new LayoutParams(ViewGroup
        .LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        iconName.setLayoutParams(titleParams);
        iconName.setText(botBean.getIconName());
        addView(iconName);
    }

    // 判断选择状态改变图标，供外部调用
    public void setSelected(boolean isSelected){
        if (botBean == null){
            return;
        }

        if (isSelected){
            if (iconImage != null){
                // 使用颜色过滤器，改变选中时的颜色为红色
                Drawable drawable = getContext().getResources().getDrawable(botBean.getUnCheckedIcon());
                Drawable wrapDrawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTintList(wrapDrawable,ColorStateList.valueOf(Color.RED));
                iconImage.setImageDrawable(wrapDrawable);

                iconName.setTextColor(Color.RED);
            }
        }else {// 没选中的图标为黑色，标题为灰色
            if (iconName != null){
                Drawable drawable = getContext().getResources().getDrawable(botBean.getUnCheckedIcon());
                Drawable wrapDrawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTintList(wrapDrawable,ColorStateList.valueOf(Color.GRAY));
                iconImage.setImageDrawable(wrapDrawable);

                iconName.setTextColor(Color.GRAY);
            }
        }
    }
}
