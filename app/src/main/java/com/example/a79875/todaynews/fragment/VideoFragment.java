package com.example.a79875.todaynews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.VideoUtil;
import com.example.a79875.todaynews.activity.VideoContentActivity;
import com.example.a79875.todaynews.adapter.VideoRecyclerViewAdapter;
import com.example.a79875.todaynews.enity.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {
    List<Video> videoList = new ArrayList<>();
    RecyclerView recyclerView;
    VideoRecyclerViewAdapter videoRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_video, container, false);

        recyclerView = view.findViewById(R.id.video_recycler_view);
        VideoUtil videoUtil = new VideoUtil();
        videoList = videoUtil.getVideoList();

        // 设置recyclerview格式为处置布局
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(getActivity(),videoList);
        recyclerView.setAdapter(videoRecyclerViewAdapter);

        videoRecyclerViewAdapter.setItemOnClickListener(new VideoRecyclerViewAdapter.ItemOnClickListener() {
            @Override
            public void clickInVideoContent(int position) {
                Intent intent = new Intent(getContext(),VideoContentActivity.class);
                intent.putExtra("videoInfo",videoList.get(position));
                startActivity(intent);

            }

            @Override
            public void clickInVideoContentCommentLocation(int position) {
                Intent intent = new Intent(getContext(),VideoContentActivity.class);
                intent.putExtra("videoInfo",videoList.get(position));
                startActivity(intent);
            }

            @Override
            public void clickShareVideo(int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, videoList.get(position).getVideoUrl());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "share"));
            }
        });
        return view;
    }

}
