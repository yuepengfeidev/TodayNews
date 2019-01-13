package com.example.a79875.todaynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.enity.Video;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by 你是我的 on 2019/1/4.
 */

// 视频 recyclerview的适配器
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Video> videoList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private ItemOnClickListener itemOnClickListener;

    public VideoRecyclerViewAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.video_recycler_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Video video = videoList.get(i);
        viewHolder.videoAuthorName.setText(video.getVideoAuthor());
        viewHolder.jiaoZiVideo.setUp(video.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, video.getVideoTitle());
        Glide.with(context)
                .load(video.getVideofacePic())
                .into(viewHolder.jiaoZiVideo.thumbImageView);

        viewHolder.layoutVideoContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.clickInVideoContent(i);
            }
        });
        viewHolder.ivVideoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.clickShareVideo(i);
            }
        });
        viewHolder.ivCommentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.clickInVideoContentCommentLocation(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard jiaoZiVideo;
        TextView videoAuthorName;
        ImageView ivCommentLocation;
        ImageView ivVideoShare;
        RelativeLayout layoutVideoContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.jiaoZiVideo = itemView.findViewById(R.id.jiao_zi_video);
            this.ivCommentLocation = itemView.findViewById(R.id.iv_comment_video);
            this.videoAuthorName = itemView.findViewById(R.id.tv_video_author_name);
            this.ivVideoShare = itemView.findViewById(R.id.iv_share_video);
            this.layoutVideoContent = itemView.findViewById(R.id.layout_video_content);
        }
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    public interface ItemOnClickListener {
        void clickInVideoContent(int position);// 点击layout进入video内容活动

        void clickInVideoContentCommentLocation(int position);// 点击评论按钮 进入video内容活动 同时定位到评论

        void clickShareVideo(int position);// 点击分享该video
    }
}
