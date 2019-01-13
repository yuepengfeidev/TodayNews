package com.example.a79875.todaynews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.SmallVideoUtil;
import com.example.a79875.todaynews.activity.SmallVideoContentActivity;
import com.example.a79875.todaynews.adapter.SmallVideoRecyclerViewAdapter;
import com.example.a79875.todaynews.enity.SmallVideo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SmallVideoFragment extends Fragment {
    List<SmallVideo> smallVideos = new ArrayList<>();
    RecyclerView recyclerView;
    SmallVideoRecyclerViewAdapter smallVideoRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SmallVideoUtil smallVideoUtil = new SmallVideoUtil();
        smallVideos.addAll(smallVideoUtil.getSmallVideoList());

        View view = inflater.inflate(R.layout.fragment_small_video, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_small_video);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        smallVideoRecyclerViewAdapter = new SmallVideoRecyclerViewAdapter(getActivity(),smallVideos);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(smallVideoRecyclerViewAdapter);

        smallVideoRecyclerViewAdapter.setOnItemClickListener(new SmallVideoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<SmallVideo> currentList = new ArrayList<>();
                for (int i = position; i < smallVideos.size(); i ++){
                    currentList.add(smallVideos.get(i));
                }
                Intent intent = new Intent(getActivity(),SmallVideoContentActivity.class);
                intent.putExtra("smallVideos", (Serializable) currentList);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                smallVideos.remove(position);
                smallVideoRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

}
