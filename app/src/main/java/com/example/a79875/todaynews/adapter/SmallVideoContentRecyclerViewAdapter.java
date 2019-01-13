package com.example.a79875.todaynews.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.enity.SmallVideo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 你是我的 on 2019/1/7.
 */

// 小视频内容recyclerview 适配器
public class SmallVideoContentRecyclerViewAdapter extends
        RecyclerView.Adapter<SmallVideoContentRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<SmallVideo> smallVideos = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private OnClickListener onClickListener;
    SmallVideo smallVideo;

    public SmallVideoContentRecyclerViewAdapter(List<SmallVideo> smallVideos, Context context) {
        this.smallVideos = smallVideos;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.small_video_content_view_pager_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        smallVideo = smallVideos.get(i);
        Glide.with(context)
                .load(smallVideo.getSmallVideoUrl())
                .apply(RequestOptions.centerCropTransform())
                .into(viewHolder.ivThumb);
        viewHolder.videoView.setVideoURI(Uri.parse("android.resource://" + context.getPackageName()
                + "/" + smallVideo.getSmallVideoUrl()));
        viewHolder.tvSmallVideoTitle.setText(smallVideo.getSmallVideoTitle());
        viewHolder.ivExitSmallVideoContent.setOnClickListener(this);
        viewHolder.ivSmallVideoShare.setOnClickListener(this);
        viewHolder.ivSmallVideoShareMore.setOnClickListener(this);
        viewHolder.layoutSmallVideoComment.setOnClickListener(this);
        viewHolder.ivSmallVideoComment.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return smallVideos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_view)
        VideoView videoView;
        @BindView(R.id.iv_thumb)
        ImageView ivThumb;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
        @BindView(R.id.check_box_like)
        CheckBox checkBoxLike;
        @BindView(R.id.iv_small_video_share_more)
        ImageView ivSmallVideoShareMore;
        @BindView(R.id.iv_small_video_comment)
        ImageView ivSmallVideoComment;
        @BindView(R.id.iv_small_video_share)
        ImageView ivSmallVideoShare;
        @BindView(R.id.iv_exit_small_video_content)
        ImageView ivExitSmallVideoContent;
        @BindView(R.id.tv_small_video_content_title)
        TextView tvSmallVideoTitle;
        @BindView(R.id.layout_small_video_comment)
        LinearLayout layoutSmallVideoComment;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_exit_small_video_content:
                onClickListener.onExitClick();
                break;
            case R.id.iv_small_video_share:
            case R.id.iv_small_video_share_more:
                onClickListener.onShareClick(smallVideo);
                break;
            case R.id.layout_small_video_comment:
                onClickListener.onCommentAndInputClick();
                break;
            case R.id.iv_small_video_comment:
                onClickListener.onCommentClick();
                break;
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onExitClick();// 退出

        void onShareClick(SmallVideo smallVideo);// 分享

        void onCommentClick();// 显示评论

        void onCommentAndInputClick();// 显示评论和软键盘
    }
}
