package com.example.a79875.todaynews.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.MesureWidgetHeight;
import com.example.a79875.todaynews.adapter.CommentRecyclerViewAdapter;
import com.example.a79875.todaynews.app.MyApplication;
import com.example.a79875.todaynews.enity.CommentBean;
import com.example.a79875.todaynews.enity.Video;
import com.example.a79875.todaynews.litepal.CollectNews;
import com.example.a79875.todaynews.litepal.LitePalUtil;
import com.example.a79875.todaynews.widegt.CommentDialog;
import com.example.a79875.todaynews.widegt.SuccessTipDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class VideoContentActivity extends SwipeBackActivity {

    @BindView(R.id.jiao_zi_video_video_content)
    JZVideoPlayerStandard jiaoZiVideoVideoContent;
    @BindView(R.id.tv_video_author)
    TextView tvVideoAuthor;
    @BindView(R.id.tv_video_description)
    TextView tvVideoDescription;
    @BindView(R.id.iv_expand_description)
    ImageView ivExpandDescription;
    @BindView(R.id.recycler_view_comment_video)
    RecyclerView recyclerViewCommentVideo;
    @BindView(R.id.nested_scroll_view_video)
    NestedScrollView nestedScrollViewVideo;
    @BindView(R.id.layout_comment_soft_input_video)
    LinearLayout layoutCommentSoftInputVideo;
    @BindView(R.id.iv_expression_soft_input_video)
    ImageView ivExpressionSoftInputVideo;
    @BindView(R.id.iv_comment_location_video)
    ImageView ivCommentLocationVideo;
    @BindView(R.id.checkbox_collect_video)
    CheckBox checkboxCollectVideo;
    @BindView(R.id.iv_share_1_video)
    ImageView ivShare1Video;
    RelativeLayout layoutAuthorInfo;

    Context context;
    CommentDialog commentDialog;
    Video video;
    CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    List<CommentBean> commentList = new ArrayList<>();
    LinearLayoutManager manager;
    private boolean isExpand;
    // 展开介绍的runnable
    Runnable resumeRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_content);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        layoutAuthorInfo = (RelativeLayout) findViewById(R.id.layout_author_info);

        this.context = this;
        video = (Video) getIntent().getSerializableExtra("videoInfo");
        commentDialog = new CommentDialog();
        setList();

        if (LitePalUtil.searchSingle(video.getVideoUrl())) {
            checkboxCollectVideo.setChecked(true);
        }else {
            checkboxCollectVideo.setChecked(false);
        }

        tvVideoAuthor.setText(video.getVideoAuthor());
        final String title = video.getVideoTitle() + "\n" + "\n";// 介绍在标题1行下面
        final String description = video.getVideoDesctiption();
        tvVideoDescription.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvVideoDescription.setText(title + description);
                resumeRunnable = new LineContent(tvVideoDescription, title, description);
                tvVideoDescription.post(resumeRunnable);
                tvVideoDescription.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(commentList, this);

        recyclerViewCommentVideo.postDelayed(new Runnable() {
            @Override
            public void run() {
                commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(commentList, getApplicationContext());
                manager = new LinearLayoutManager(getApplicationContext());
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerViewCommentVideo.setLayoutManager(manager);
                recyclerViewCommentVideo.setAdapter(commentRecyclerViewAdapter);
            }
        }, 800);// 设置延迟 ，防止评论先出现

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
                recyclerViewCommentVideo.getLocationInWindow(xy);
                nestedScrollViewVideo.smoothScrollBy(xy[0], xy[1] - MyApplication.getAuthorInfoLayoutHeiht()
                        - MyApplication.getVideoViewHeight() - 20);

                commentDialog.dismiss();
            }
        });

        checkboxCollectVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    LitePalUtil.save(video.getVideoTitle(), video.getVideoDesctiption(),
                            video.getVideofacePic(), video.getVideoUrl(),
                            video.getVideoAuthor(), "video");
                } else {
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                    LitePalUtil.deleteSingle(video.getVideoUrl());
                }

            }
        });

        jiaoZiVideoVideoContent.setUp(video.getVideoUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, video.getVideoTitle());
        Glide.with(this)
                .load(video.getVideofacePic())
                .into(jiaoZiVideoVideoContent.thumbImageView);
        jiaoZiVideoVideoContent.startVideo();

        // 测量视频播放器的高度 和 作者信息栏高度
        layoutAuthorInfo.post(new Runnable() {
            @Override
            public void run() {
                MyApplication.setVideoViewHeight(jiaoZiVideoVideoContent);
                MyApplication.setAuthorInfoLayoutHeiht(layoutAuthorInfo);
            }
        });
    }

    // 获取标题加介绍的行数，判断显示是否展开
    private class LineContent implements Runnable {
        TextView textView;
        String title;
        String description;
        String unExpandTitle;

        public LineContent(TextView textView, String title, String description) {
            this.textView = textView;
            this.title = title;
            this.description = description;
        }

        @Override
        public void run() {
            if (null != textView && !TextUtils.isEmpty(title)) {
                GetLintCount();
            }
        }

        private void GetLintCount() {
            // 得到textview的布局
            Layout layout = textView.getLayout();
            // 的到textview显示多少行
            int lineCounts = layout.getLineCount();
            try {
                if (isExpand) {
                    setShowText(title, description, isExpand);// 展开状态 显示全部
                } else {
                    if (lineCounts > 1) {
                        StringBuffer threeLinesContent = new StringBuffer();
                        for (int i = 0; i < 1; i++) {
                            threeLinesContent.append(title.substring(layout.getLineStart(i), layout.getLineEnd(i)));
                            unExpandTitle = threeLinesContent.substring(0, threeLinesContent.length() - 3) + "...";
                            setShowText(unExpandTitle, "", false);// 大于一行，则省略后面的文字，并加上"..."
                            ivExpandDescription.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isExpand) {
                                        isExpand = false;
                                    } else {
                                        isExpand = true;
                                    }
                                    textView.post(resumeRunnable);
                                }
                            });
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 设置 展示的标题和介绍
    private void setShowText(String title, String description, boolean expand) {
        String allContent = title + description;
        SpannableString spannableString = new SpannableString(allContent);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.descriptionColor));
        spannableString.setSpan(foregroundColorSpan, title.length(), allContent.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(sizeSpan, title.length(), allContent.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ivExpandDescription.setImageResource(expand ? R.drawable.up_icon : R.drawable.down_icon);
        tvVideoDescription.setText(spannableString);
    }

    // 组装的评论数据
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

    @OnClick({R.id.iv_expand_description, R.id.layout_comment_soft_input_video, R.id.iv_expression_soft_input_video, R.id.iv_comment_location_video, R.id.iv_share_1_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_comment_soft_input_video:
                commentDialog.show(getSupportFragmentManager(), "softInput_video");
                break;
            case R.id.iv_expression_soft_input_video:
                commentDialog.show(getSupportFragmentManager(), "emojiInput_video");
                break;
            case R.id.iv_comment_location_video:
                int[] xy = new int[2];
                recyclerViewCommentVideo.getLocationInWindow(xy);
                nestedScrollViewVideo.smoothScrollBy(xy[0], xy[1] - MyApplication.getAuthorInfoLayoutHeiht()
                        - MyApplication.getVideoViewHeight() - 20);
                break;
            case R.id.iv_share_1_video:
                Intent intent1 = new Intent(Intent.ACTION_SEND);
                intent1.putExtra(Intent.EXTRA_TEXT, video.getVideoUrl());
                intent1.setType("text/plain");
                startActivity(Intent.createChooser(intent1, "share"));
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

    @Override
    protected void onDestroy() {
        if (null != resumeRunnable) {
            resumeRunnable = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
