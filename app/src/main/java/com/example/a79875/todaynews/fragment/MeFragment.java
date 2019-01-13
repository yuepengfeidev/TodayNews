package com.example.a79875.todaynews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.activity.collect_activity.CollectActivity;

public class MeFragment extends Fragment {
    LinearLayout layoutCollect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_me, container, false);
        layoutCollect = view.findViewById(R.id.layout_collect);

        layoutCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CollectActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
