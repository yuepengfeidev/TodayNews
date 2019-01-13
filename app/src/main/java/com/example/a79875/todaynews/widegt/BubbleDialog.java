package com.example.a79875.todaynews.widegt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.MesureWidgetHeight;

// 使用气泡dialog 开源控件
public class BubbleDialog extends com.xujiaji.happybubble.BubbleDialog implements View.OnClickListener {
    View view;
    RelativeLayout layoutUnInterest;
    OnClickListener onClickListener;

    public BubbleDialog(Context context) {
        super(context);

        setPosition(Position.TOP);
        view = LayoutInflater.from(context).inflate(R.layout.bubble_dialog, null);
        layoutUnInterest = view.findViewById(R.id.layout_uninterest);
        layoutUnInterest.setOnClickListener(this);

        addContentView(view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_uninterest:
                onClickListener.onClick();
                break;
        }
    }

    // 手动测量自定义控件的高度
    public int getHeight() {
        int height = MesureWidgetHeight.mesureHeight(view);

        return height;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener{
        void onClick();
    }
}
