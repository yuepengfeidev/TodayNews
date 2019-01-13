package com.example.a79875.todaynews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.example.a79875.todaynews.fragment.EmojiFragment;

import java.util.List;

/**
 * Created by 你是我的 on 2018/12/30.
 */
public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    private final List<EmojiFragment> fragments;

    public FragmentPagerAdapter(FragmentManager fm, List<EmojiFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
