package com.example.a79875.todaynews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Callback;
import okhttp3.Response;

import com.example.a79875.todaynews.activity.MainActivity;
import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.HttpUtil;
import com.example.a79875.todaynews.Util.Utility;
import com.example.a79875.todaynews.activity.NewsContentActivity;
import com.example.a79875.todaynews.adapter.MsgContentRecyclerViewAdapter;
import com.example.a79875.todaynews.app.MyApplication;
import com.example.a79875.todaynews.enity.Title;
import com.example.a79875.todaynews.gson.News;
import com.example.a79875.todaynews.gson.NewsList;
import com.example.a79875.todaynews.widegt.BubbleDialog;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.constraint.Constraints.TAG;

// 首页新闻类别内容碎片
public class HomePageCategoryFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    String categoryName;
    ArrayList<Title> titleList = new ArrayList<>();
    MsgContentRecyclerViewAdapter msgContentRecyclerViewAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        categoryName = bundle.getString("categoryName");
        if (categoryName == null) {
            categoryName = "非法参数";
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page_category, container, false);
        swipeRefreshLayout = view.findViewById(R.id.home_page_category_recycler_view_swipe_refresh);
        recyclerView = view.findViewById(R.id.home_page_category_recycler_view);

        // 设置recyclerview格式为处置布局
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        msgContentRecyclerViewAdapter = new MsgContentRecyclerViewAdapter(getActivity(), titleList);
        recyclerView.setAdapter(msgContentRecyclerViewAdapter);
        msgContentRecyclerViewAdapter.setOnItemClickListenter(new MsgContentRecyclerViewAdapter.OnItemClickListenter() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), NewsContentActivity.class);
                intent.putExtra("Title", (Serializable) titleList.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemDelete(final int position, View view, int currentY) {
                final BubbleDialog happyBubbleDialog = new BubbleDialog(getContext());
                // 当前点击位置y坐标 - toolbar的高度 - tablayout的高度 - 任务栏高度 > 0 则存在可以容下bubbledialog的位置
                // 或者 屏幕总高度 - 点给钱点击的y坐标 - bottomview的高度 < 0 则点击位置下方不能容下bubbledialog
                if (currentY - MyApplication.getToolbarHeight()
                        - MyApplication.getTablayoutHeight()
                        - happyBubbleDialog.getHeight()
                        - MyApplication.getTaskHeight() > 0 ||
                        getActivity().getWindowManager().getDefaultDisplay().getHeight()
                                - MyApplication.getBottomViewHeight() - currentY < 0) {// 如果距tablayout能放的下 bubbledialog 则位于按钮上方
                    happyBubbleDialog
                            .setPosition(com.xujiaji.happybubble.BubbleDialog.Position.TOP)
                            .setClickedView(view)
                            .setOffsetY(-10)// bubbledialog显示位置的y轴偏移量
                    ;
                } else {
                    happyBubbleDialog
                            .setPosition(com.xujiaji.happybubble.BubbleDialog.Position.BOTTOM)
                            .setClickedView(view)
                            .setOffsetY(-38)
                    ;
                }
                happyBubbleDialog.show();

                // 不感兴趣 按钮点击事件
                happyBubbleDialog.setOnClickListener(new BubbleDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        titleList.remove(titleList.get(position));
                        msgContentRecyclerViewAdapter.notifyDataSetChanged();
                        happyBubbleDialog.dismiss();

                        // 显示自定义toast
                        Toast toast = new Toast(getActivity());
                        View layout = View.inflate(getActivity(), R.layout.toast_layout, null);
                        toast.setView(layout);
                        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP,
                                0, MyApplication.getTablayoutHeight()
                        + MyApplication.getToolbarHeight() - 15);
                        toast.show();
                    }
                });
            }
        });

        // 刷新当前类别的新闻
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                requestNew(categoryName);
            }
        });
        requestNew(categoryName);
        return view;
    }


    private String switchCategoryName(String categoryName) {
        int name = 0;
        String address = null;
        switch (categoryName) {
            case "国内":
                name = MainActivity.INLAND;
                break;
            case "社会":
                name = MainActivity.SOCIAL;
                break;
            case "国际":
                name = MainActivity.INTERNATIONAL;
                break;
            case "娱乐":
                name = MainActivity.RECREATION;
                break;
            case "科技":
                name = MainActivity.SCIENCE;
                break;
            case "体育":
                name = MainActivity.SPORT;
                break;
            case "健康":
                name = MainActivity.HEALTH;
                break;
            case "旅游":
                name = MainActivity.TRAVEL;
                break;
            case "IT":
                name = MainActivity.IT;
                break;
            case "军事":
                name = MainActivity.MILITARY;
                break;
            case "NBA":
                name = MainActivity.NBA;
                break;
            case "足球":
                name = MainActivity.FOOTBALL;
                break;
            case "区块链":
                name = MainActivity.BLOCKCHAIN;
                break;
        }
        address = MainActivity.responseNew(name);
        return address;
    }

    /**
     * 请求处理数据
     */
    public void requestNew(String itemName) {

        // 根据返回到的 URL 链接进行申请和返回数据
        String address = switchCategoryName(itemName);    // key
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 通过添加recyclerview 的 item 来提示信息
                        tip("刷新失败");
                    }
                });
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull Response response) throws IOException {
                assert response.body() != null;
                final String responseText = response.body().string();
                final NewsList newlist = Utility.parseJsonWithGson(responseText);
                final int code = newlist.code;
                if (code == 200) {
                    titleList.clear();
                    for (News news : newlist.newsList) {
                        Title title = new Title(news.title, news.description, news.picUrl, news.url);
                        titleList.add(title);
                    }

                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            msgContentRecyclerViewAdapter.notifyDataSetChanged();
                            recyclerView.scrollToPosition(0);
                            swipeRefreshLayout.setRefreshing(false);

                            tip("刷新成功");
                        }

                    });

                } else {
                    Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);

                            tip("返回数据错误");
                        }
                    });
                }
            }
        });
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            titleList.remove(titleList.get(0));
            msgContentRecyclerViewAdapter.notifyDataSetChanged();
            return false;
        }
    });

    public void tip(String tipContent) {
        Title title = new Title("header", tipContent, null, null);
        List<Title> titles = new ArrayList<>();
        titles.addAll(titleList);

        titleList.clear();
        titleList.add(title);
        titleList.addAll(titles);
        msgContentRecyclerViewAdapter.notifyDataSetChanged();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();

    }
}
