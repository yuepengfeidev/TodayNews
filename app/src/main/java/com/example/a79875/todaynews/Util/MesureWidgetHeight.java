package com.example.a79875.todaynews.Util;

import android.view.View;

// 手动测量控件高度
public class MesureWidgetHeight {
    public static int mesureHeight(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();

        return height;
    }

}
