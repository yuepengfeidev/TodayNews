package com.example.a79875.todaynews.activity.collect_activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.adapter.CollectRecyclerViewAdapter;
import com.example.a79875.todaynews.litepal.CollectNews;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

// 收藏  使用to_do mvp
public class CollectActivity extends SwipeBackActivity implements CollectContract.View{

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_choose_delete)
    TextView tvChooseDelete;
    @BindView(R.id.recycler_view_collect)
    RecyclerView recyclerViewCollect;
    @BindView(R.id.tv_collect_clear_all)
    TextView tvCollectClearAll;
    @BindView(R.id.tv_collect_delete_choose)
    TextView tvCollectDeleteChoose;
    @BindView(R.id.layout_delete_collect_click)
    RelativeLayout layoutDeleteCollectClick;
    CollectRecyclerViewAdapter collectRecyclerViewAdapter;
    List<CollectNews> collectNews = new ArrayList<>();
    LinearLayoutManager manager;

    CollectContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);

        mPresenter = new CollectPresenter(this,this);
        mPresenter.start();

        mPresenter.searchAll(false);// 先查询所有 收藏数据

        collectRecyclerViewAdapter = new CollectRecyclerViewAdapter(collectNews, this);
        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewCollect.setAdapter(collectRecyclerViewAdapter);
        recyclerViewCollect.setLayoutManager(manager);

        collectRecyclerViewAdapter.setOnClickListener(new CollectRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onNewsItemClick(int position) {
                mPresenter.itemClick(position,"News");
            }

            @Override
            public void onVideoItemClick(int position) {
               mPresenter.itemClick(position,"Video");
            }

            @Override
            public void onShareClick(int position) {
               mPresenter.share(position);
            }

            @Override
            public void onChooseClick(int position, boolean isChecked) {
                mPresenter.itemChoose(position,isChecked);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_choose_delete, R.id.tv_collect_clear_all, R.id.tv_collect_delete_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                mPresenter.cancel();
                break;
            case R.id.tv_choose_delete:
                mPresenter.tvDeleteCancel(tvChooseDelete.getText().toString());
                break;
            case R.id.tv_collect_clear_all:
                mPresenter.clearAll();
                break;
            case R.id.tv_collect_delete_choose:
               mPresenter.deleteChoose();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       mPresenter.searchAll(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
       mPresenter.cancel();
    }

    @Override
    public void hideChoose() {
        layoutDeleteCollectClick.setVisibility(View.GONE);
    }

    @Override
    public void showChoose() {
        layoutDeleteCollectClick.setVisibility(View.VISIBLE);
    }

    @Override
    public void startContentActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void setCollectNews(List<CollectNews> collectNews) {
        this.collectNews = collectNews;
    }

    @Override
    public void openShare(Intent intent) {
        startActivity(Intent.createChooser(intent,"分享"));
    }

    @Override
    public void setTvDeleteCancelText(String content) {
        tvChooseDelete.setText(content);
    }

    @Override
    public void setTvDeleteText(String content, String color, boolean clickAble) {
        tvCollectDeleteChoose.setText(content);
        tvCollectDeleteChoose.setTextColor(Color.parseColor(color));
        tvCollectDeleteChoose .setClickable(clickAble);
    }

    @Override
    public void notifyChangeAdapter() {
        collectRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void changeChooseType(boolean isChoose) {
        collectRecyclerViewAdapter.isChoose = isChoose;
    }

    @Override
    public void setPresenter(CollectContract.Presenter presenter) {
        this.mPresenter = presenter;
    }
}
