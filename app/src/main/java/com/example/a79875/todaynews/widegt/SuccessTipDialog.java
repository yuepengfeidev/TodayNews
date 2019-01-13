package com.example.a79875.todaynews.widegt;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.a79875.todaynews.R;

/**
 * Created by 你是我的 on 2019/1/1.
 */

// 收藏点击成功显示的提示dialog
public class SuccessTipDialog extends Dialog {
    TextView tvTip;
    String tipContent;

    public SuccessTipDialog(Context context, int themeResId, String tipContent) {
        super(context, themeResId);
        setContentView(R.layout.collect_tip_dialog);
        this.tipContent = tipContent;

        tvTip = findViewById(R.id.tv_tip);

        tvTip.setText(tipContent);
    }

}
