package com.example.a79875.todaynews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.litepal.CollectNews;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by 你是我的 on 2019/1/7.
 */

// 收藏recyclerview 的适配器
public class CollectRecyclerViewAdapter extends RecyclerView.Adapter<CollectRecyclerViewAdapter.ViewHolder> {
    List<CollectNews> collectNews = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    OnClickListener onClickListener;
    public  boolean isChoose = false;// 默认状态为false 不选择 状态 / true 选择状态

    public CollectRecyclerViewAdapter(List<CollectNews> collectNews, Context context) {
        this.collectNews = collectNews;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("yue", "getItemViewType: " + collectNews.size());
        if (collectNews.get(position).getType().equals("video")){
            return 0;
        }else {
            return 1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case 0:
                view = layoutInflater.inflate(R.layout.collect_video_item, viewGroup, false);
                break;
            case 1:
                view = layoutInflater.inflate(R.layout.collect_news_item, viewGroup, false);
                break;
        }
        assert view != null;
        return new ViewHolder(view,i);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        CollectNews collect = collectNews.get(i);
        switch (getItemViewType(i)) {
            case 0:
                if (isChoose){// 选择状态显示 选择checkbox
                    viewHolder.linearLayoutVideo.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.linearLayoutVideo.setVisibility(View.GONE);
                }
                viewHolder.videoTitle.setText(collect.getTitle());
                viewHolder.jiaoziVideo.setUp(collect.getUrl(),
                        JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                        "");
                Glide.with(context)
                        .load(collect.getFacePicUrl())
                        .into(viewHolder.jiaoziVideo.thumbImageView);
                viewHolder.chooseVideo.setChecked(false);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onVideoItemClick(i);
                    }
                });
                viewHolder.chooseVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onClickListener.onChooseClick(i,isChecked);
                    }
                });
                break;
            case 1:
                if (isChoose){// 选择状态显示 选择checkbox
                    viewHolder.linearLayoutNews.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.linearLayoutNews.setVisibility(View.GONE);
                }
                viewHolder.newsTitle.setText(collect.getTitle());
                Glide.with(context)
                        .load(collect.getFacePicUrl())
                        .apply(RequestOptions.centerCropTransform())
                        .into(viewHolder.newsFacePic);
                viewHolder.chooseNews.setChecked(false);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.onNewsItemClick(i);
                    }
                });
                viewHolder.chooseNews.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        onClickListener.onChooseClick(i,isChecked);
                    }
                });
                break;
        }
        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onShareClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView videoTitle;
        JZVideoPlayerStandard jiaoziVideo;
        LinearLayout linearLayoutVideo;// 显示隐藏 选择checkbox 的 父布局
        CheckBox chooseVideo;// 选择checkbox

        TextView newsTitle;
        ImageView newsFacePic;
        LinearLayout linearLayoutNews;
        CheckBox chooseNews;

        ImageView share;// 分享

        public ViewHolder(@NonNull View itemView,int type) {
            super(itemView);
            switch (type) {
                case 0:
                    videoTitle = itemView.findViewById(R.id.tv_collect_video_title);
                    jiaoziVideo = itemView.findViewById(R.id.jiao_zi_video_collect);
                    linearLayoutVideo = itemView.findViewById(R.id.linearLayout5);
                    chooseVideo = itemView.findViewById(R.id.check_box_choose2);
                    break;
                case 1:
                    newsTitle = itemView.findViewById(R.id.tv_collect_news_title);
                    newsFacePic = itemView.findViewById(R.id.iv_collect_new_face_pic);
                    linearLayoutNews = itemView.findViewById(R.id.linearLayout4);
                    chooseNews = itemView.findViewById(R.id.check_box_choose);
                    break;

            }
            share = itemView.findViewById(R.id.iv_collect_share);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onNewsItemClick(int position);

        void onVideoItemClick(int position);

        void onShareClick(int position);

        void onChooseClick(int position, boolean isChecked);
    }
}
