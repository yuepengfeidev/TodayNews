package com.example.a79875.todaynews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.activity.MainActivity;
import com.example.a79875.todaynews.adapter.EmojiViewPagerAdapter;
import com.example.a79875.todaynews.helper.EmojiHelper;
import com.example.a79875.todaynews.widegt.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmojiFragment extends Fragment {
    private int type = 0;
    private EditText editText;
    private CircleIndicator circleIndicator;


    public EmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoji, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       MainActivity mainActivity = (MainActivity) getActivity();
       if (mainActivity != null){
           editText = mainActivity.findViewById(R.id.edit_text);
       }
       initView();
    }

    private void initView() {
        type = getArguments().getInt("EmojiType");
        ViewPager viewPager = (ViewPager)getView().findViewById(R.id.view_pager_1);
        CircleIndicator circleIndicator = getView().findViewById(R.id.circle_indicator_1);
        EmojiHelper emojiHelper = new EmojiHelper(type,getContext(),editText);
        EmojiViewPagerAdapter emojiViewPagerAdapter = new EmojiViewPagerAdapter(emojiHelper.getPagers());
        viewPager.setAdapter(emojiViewPagerAdapter);
        circleIndicator.setViewPager(viewPager);
    }
}
