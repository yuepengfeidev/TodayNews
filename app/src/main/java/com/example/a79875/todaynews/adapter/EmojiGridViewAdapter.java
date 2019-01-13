package com.example.a79875.todaynews.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.EmojiUtil;

import java.util.List;

/**
 * Created by 你是我的 on 2018/12/28.
 */

/// Emoji的GridView适配器
public class EmojiGridViewAdapter extends ArrayAdapter<String> {
    private int type = 0;

    public EmojiGridViewAdapter(int type, Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.type = type;
    }

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(getContext(), R.layout.item_row_emoji,null);
        }

        ImageView imageView = convertView.findViewById(R.id.iv_emoji);
        String fileName = getItem(position);
        Integer resId = EmojiUtil.getEmoJiMap(type).get(fileName);
        if (resId != null){
            Drawable drawable = getContext().getResources().getDrawable(resId);
            drawable.setBounds(0,0,drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
            imageView.setImageResource(resId);
        }
        return convertView;
    }
}
