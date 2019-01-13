package com.example.a79875.todaynews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.adapter.CommentRecyclerViewAdapter;
import com.example.a79875.todaynews.enity.CommentBean;
import com.example.a79875.todaynews.enity.Title;
import com.example.a79875.todaynews.litepal.LitePalUtil;
import com.example.a79875.todaynews.widegt.CommentDialog;
import com.example.a79875.todaynews.widegt.SuccessTipDialog;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class NewsContentActivity extends SwipeBackActivity implements View.OnClickListener {
    SwipeBackLayout swipeBackLayout;
    ImageView ivBack;
    ImageView ivShare;
    WebView webView;
    TextView tvNewTitle;
    Title title;
    RecyclerView recyclerViewComment;
    CheckBox checkBoxCollect;
    ImageView ivCommentLocation;// 定位到第一条评论按钮
    ImageView ivShare2;
    ImageView ivExpressionSoftInput;
    LinearLayout layoutCommentSoftInput;
    CommentDialog commentDialog;
    CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    List<CommentBean> commentList = new ArrayList<>();
    LinearLayoutManager manager;
    NestedScrollView nestedScrollView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        this.context = this;
        // 使用右滑返回框架
        setSwipeBackEnable(true);
        swipeBackLayout = getSwipeBackLayout();
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        swipeBackLayout.setEdgeSize(300);// 设置滑动有效范围

        title = (Title) getIntent().getSerializableExtra("Title");

        initView();

    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back_news_content);
        ivShare = (ImageView) findViewById(R.id.iv_share_news_content);
        webView = (WebView) findViewById(R.id.web_view);
        tvNewTitle = (TextView) findViewById(R.id.tv_title_news_content);
        recyclerViewComment = (RecyclerView) findViewById(R.id.recycler_view_comment);
        checkBoxCollect = (CheckBox) findViewById(R.id.checkbox_collect);
        ivCommentLocation = (ImageView) findViewById(R.id.iv_comment_location);
        ivShare2 = (ImageView) findViewById(R.id.iv_share_1);
        ivExpressionSoftInput = (ImageView) findViewById(R.id.iv_expression_soft_input);
        layoutCommentSoftInput = (LinearLayout) findViewById(R.id.layout_comment_soft_input);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);

        if (LitePalUtil.searchSingle(title.getUri())){
            checkBoxCollect.setChecked(true);
        }else {
            checkBoxCollect.setChecked(false);
        }

        tvNewTitle.setText(title.getDescription());

        webView.getSettings().setJavaScriptEnabled(true);// 设置webview 支持javascript
        webView.setWebViewClient(new WebViewClient());// 网页只会在vebview中显示，而不会通过浏览器打开
        webView.loadUrl(title.getUri());// 加载新闻网页

        ivBack.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        layoutCommentSoftInput.setOnClickListener(this);
        ivShare2.setOnClickListener(this);
        ivCommentLocation.setOnClickListener(this);
        ivExpressionSoftInput.setOnClickListener(this);

        checkBoxCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                    LitePalUtil.save(title.getTitle(),title.getDescription(),
                            title.getImageUrl(),title.getUri(),title.getDescription(),"news");
                } else {
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                    LitePalUtil.deleteSingle(title.getUri());
                }

            }
        });

        commentDialog = new CommentDialog();
        setList();
        recyclerViewComment.postDelayed(new Runnable() {
            @Override
            public void run() {
                commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(commentList, getApplicationContext());
                manager = new LinearLayoutManager(getApplicationContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewComment.setLayoutManager(manager);
                recyclerViewComment.setAdapter(commentRecyclerViewAdapter);
            }
        }, 3000);// 设置延迟 ，防止评论先出现

        // 评论dialog 发送信息回调
        commentDialog.setSendListener(new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                List<CommentBean> list = new ArrayList<>();
                list.addAll(commentList);
                commentList.clear();
                CommentBean commentBean = new CommentBean();
                commentBean.setUserName("你是我的");
                commentBean.setUserComment(inputText);
                commentList.add(commentBean);
                commentList.addAll(list);

                commentRecyclerViewAdapter.notifyDataSetChanged();

                int[] xy = new int[2];
                recyclerViewComment.getLocationInWindow(xy);
                nestedScrollView.smoothScrollBy(xy[0], xy[1] - 100);

                commentDialog.dismiss();
            }
        });

    }

    public List<CommentBean> setList() {
        commentList.clear();
        for (int i = 0; i < 20; i++) {
            CommentBean commentBean = new CommentBean();
            commentBean.setUserName(i + "号选手");
            commentBean.setUserComment((1 + i) + "楼说的真好");
            commentList.add(commentBean);
        }
        return commentList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_news_content:
                finish();
                break;
            case R.id.iv_share_news_content:
            case R.id.iv_share_1:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, title.getUri());
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "share"));
                break;
            case R.id.layout_comment_soft_input:
                commentDialog.show(getSupportFragmentManager(), "softInput");
                break;
            case R.id.iv_expression_soft_input:
                commentDialog.show(getSupportFragmentManager(), "emojiInput");
                break;
            case R.id.iv_comment_location:// 移动到评论区的第一个item位置
                int[] xy = new int[2];
                recyclerViewComment.getLocationInWindow(xy);
                nestedScrollView.smoothScrollBy(xy[0], xy[1] - 100);
                break;
        }
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            final SuccessTipDialog successTipDialog;
            switch (msg.what) {
                case 1:
                    // 此处的context必须要用 该activity的context
                    successTipDialog = new SuccessTipDialog(context,
                            R.style.CustomTipDialog, "收藏成功");
                    successTipDialog.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1300);
                                successTipDialog.dismiss();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                case 2:
                    successTipDialog = new SuccessTipDialog(context,
                            R.style.CustomTipDialog, "取消收藏");
                    successTipDialog.show();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1300);
                                successTipDialog.dismiss();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
            }

            return false;
        }
    });

}