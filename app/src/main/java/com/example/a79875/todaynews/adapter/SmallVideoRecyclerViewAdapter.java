package com.example.a79875.todaynews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.enity.SmallVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 你是我的 on 2019/1/7.
 */
public class SmallVideoRecyclerViewAdapter extends RecyclerView.Adapter<SmallVideoRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<SmallVideo> smallVideos = new ArrayList<>();
    LayoutInflater layoutInflater;
    OnItemClickListener onItemClickListener;

    public SmallVideoRecyclerViewAdapter(Context context, List<SmallVideo> smallVideos) {
        this.context = context;
        this.smallVideos = smallVideos;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.small_video_recycler_view_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        SmallVideo smallVideo = smallVideos.get(i);
        viewHolder.smallVideoTitle.setText(smallVideo.getSmallVideoTitle());
        Glide.with(context)
                .load(smallVideo.getSmallVideoFacePic())
                .apply(RequestOptions.centerCropTransform())
                .thumbnail(0.1f)
                .into(viewHolder.smallVideoPic);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,i);
            }
        });
        viewHolder.deleteSmallVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onDeleteClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return smallVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView smallVideoPic;
        TextView smallVideoTitle;
        ImageView deleteSmallVideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            smallVideoPic = itemView.findViewById(R.id.iv_small_video_face_pic);
            smallVideoTitle = itemView.findViewById(R.id.video_title);
            deleteSmallVideo = itemView.findViewById(R.id.iv_delete_small_video);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onDeleteClick(int position);
    }
}
