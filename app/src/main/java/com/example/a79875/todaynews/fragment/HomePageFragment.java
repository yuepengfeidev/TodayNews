package com.example.a79875.todaynews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.ListDataSaveWithSharedPreferences;
import com.example.a79875.todaynews.adapter.MsgContentFragmentAdapter;
import com.example.a79875.todaynews.app.MyApplication;
import com.example.a79875.todaynews.widegt.BottomMoreDialog;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

// 首页碎片
public class HomePageFragment extends Fragment implements View.OnClickListener {
    private List<String> categoryNames = new ArrayList<>();
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private MsgContentFragmentAdapter msgContentFragmentAdapter;
    private RelativeLayout layoutMore;
    BottomMoreDialog bottomMoreDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        bottomMoreDialog = new BottomMoreDialog();
        if (ListDataSaveWithSharedPreferences.getIsFirst(getActivity())) {
            List<String> strings = new ArrayList<>();
            strings.add("社会");
            strings.add("国际");
            strings.add("娱乐");
            strings.add("科技");
            strings.add("体育");
            strings.add("健康");
            strings.add("旅游");
            strings.add("IT");
            strings.add("军事");
            strings.add("NBA");
            strings.add("足球");
            strings.add("区块链");
            strings.add("国内");
            ListDataSaveWithSharedPreferences.saveIsFirst(getActivity());// 设置不是第一次
            ListDataSaveWithSharedPreferences.saveList(getActivity(), "selectedChannel", strings);// 存储已选频道
        }
        categoryNames = ListDataSaveWithSharedPreferences.getList(getActivity(), "selectedChannel");// 获取已选频道
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        tabLayout = view.findViewById(R.id.home_page_tab_layout);
        viewPager = view.findViewById(R.id.home_page_view_pager);
        layoutMore = view.findViewById(R.id.layout_more);
        toolbar = view.findViewById(R.id.toolbar_home_page);

        // 设置 toolbar 和 tablayout 的全局变量，方便后面获取
        MyApplication.setTablayoutHeight(tabLayout);
        MyApplication.setToolbarHeight(toolbar);

        msgContentFragmentAdapter = new MsgContentFragmentAdapter(getChildFragmentManager());
        // 绑定
        viewPager.setAdapter(msgContentFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

// 更新适配器类别标题数据列表
        msgContentFragmentAdapter.setList(categoryNames);

        // 初始化tablayout类别字体样式
        for (int i = 0; i < categoryNames.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
                if (i == 0) {
                    ((TextView) tab.getCustomView()).setTextColor(Color.RED);
                    ((TextView) tab.getCustomView()).setTextSize(21);
                } else {
                    ((TextView) tab.getCustomView()).setTextSize(18);
                    ((TextView) tab.getCustomView()).setTextColor(Color.BLACK);
                }
            }
        }


        /*tabLayout.getTabAt(0).getCustomView().setSelected(true);// 默认选择第一个*/
        // tablayout 监听事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view1 = tab.getCustomView();
                if (view1 instanceof TextView) {
                    ((TextView) view1).setTextSize(21);
                    ((TextView) view1).setTextColor(Color.RED);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view1 = tab.getCustomView();
                if (view1 instanceof TextView) {
                    ((TextView) view1).setTextSize(18);
                    ((TextView) view1).setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view1 = tab.getCustomView();
                if (view1 instanceof TextView) {
                    ((TextView) view1).setTextSize(21);
                    ((TextView) view1).setTextColor(Color.RED);
                }
            }
        });

        bottomMoreDialog.setUpdateTablayout(new BottomMoreDialog.UpdateTablayout() {
            @Override
            public void updateTablayout(List<String> myChannelList, List<String> otherChannelList) {
                categoryNames.clear();
                categoryNames.addAll(myChannelList);
                // 需要线通知fragment适配器更新 才能设置tablayout格式
                msgContentFragmentAdapter.setList(categoryNames);
                msgContentFragmentAdapter.notifyDataSetChanged();



                // 初始化tablayout类别字体样式
                for (int i = 0; i < categoryNames.size(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    if (tab != null) {
                        tab.setCustomView(getTabView(i));
                        if (i == 0) {
                            ((TextView) tab.getCustomView()).setTextColor(Color.RED);
                            ((TextView) tab.getCustomView()).setTextSize(21);
                        } else {
                            ((TextView) tab.getCustomView()).setTextSize(18);
                            ((TextView) tab.getCustomView()).setTextColor(Color.BLACK);
                        }
                    }
                }
                tabLayout.getTabAt(0).getCustomView().setSelected(true);

            }
        });

        layoutMore.setOnClickListener(this);

        return view;
    }

    /**
     * 自定义Tab的View
     *
     * @param currentPosition
     * @return
     */
    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_text, null);
        TextView textView = view.findViewById(R.id.tab_item_textview);
        textView.setText(categoryNames.get(currentPosition));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_more:
                bottomMoreDialog.show(getChildFragmentManager(), "Dialog");
                break;
        }
    }


}
