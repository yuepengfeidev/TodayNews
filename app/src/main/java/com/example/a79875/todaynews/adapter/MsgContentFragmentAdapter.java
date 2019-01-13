package com.example.a79875.todaynews.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.a79875.todaynews.fragment.HomePageCategoryFragment;

import java.util.ArrayList;
import java.util.List;

// 首页每个新闻类别的碎片适配器
public class MsgContentFragmentAdapter extends FragmentPagerAdapter {
    private List<String> categoryNames;

    public MsgContentFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.categoryNames = new ArrayList<>();
    }

    // 首页类别名称更新
    public  void setList(List<String> categoryNames){
        this.categoryNames.clear();
        this.categoryNames.addAll(categoryNames);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        HomePageCategoryFragment homePageCategoryFragment = new HomePageCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryName",categoryNames.get(i));
        homePageCategoryFragment.setArguments(bundle);
        return  homePageCategoryFragment;
    }

    @Override
    public int getCount() {
        return categoryNames.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String categoryName = categoryNames.get(position);
        if (categoryName == null){
            categoryName = "";
        }else if (categoryName.length() > 15){
            categoryName = categoryName.substring(0,15) + "...";
        }
        return categoryName;
    }
}
