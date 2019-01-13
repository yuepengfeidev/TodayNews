package com.example.a79875.todaynews.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.adapter.SmallVideoContentRecyclerViewAdapter;
import com.example.a79875.todaynews.enity.CommentBean;
import com.example.a79875.todaynews.enity.SmallVideo;
import com.example.a79875.todaynews.helper.MyLayoutManger;
import com.example.a79875.todaynews.helper.OnViewPagerListener;
import com.example.a79875.todaynews.widegt.CommentDialog;
import com.example.a79875.todaynews.widegt.SmallVideoCommentContentBottomDialog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// 类似viewpager的小视频内容 活动
public class SmallVideoContentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SmallVideoContentRecyclerViewAdapter smallVideoContentRecyclerViewAdapter;
    List<SmallVideo> smallVideos = new ArrayList<>();
    MyLayoutManger myLayoutManger;
    float oldX = 0;
    float currentX = 0;
    SmallVideoCommentContentBottomDialog smallVideoCommentContentBottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_video_content);

        smallVideos.addAll((List<SmallVideo>) getIntent().getSerializableExtra("smallVideos"));

        initView();
        initListener();
    }

    private void initListener() {
        myLayoutManger.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                int index = 0;
                if (isNext) {// 有下一个则释放当前视频
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onPageSelected(int position, boolean isBottom) {
                palyVideo(0);
            }

            @Override
            public void exitVideo() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (currentX - oldX > 200) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }
                }).start();

            }
        });

    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view_small_video_content);
        /*myLayoutManger = new MyLayoutManger(this,OrientationHelper.VERTICAL,false);*/// 上下滑动
        myLayoutManger = new MyLayoutManger(this, OrientationHelper.HORIZONTAL, false);// 左右滑动
        smallVideoContentRecyclerViewAdapter = new SmallVideoContentRecyclerViewAdapter(smallVideos, this);
        recyclerView.setLayoutManager(myLayoutManger);
        recyclerView.setAdapter(smallVideoContentRecyclerViewAdapter);


        smallVideoContentRecyclerViewAdapter.setOnClickListener(new SmallVideoContentRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onExitClick() {
                out();
            }

            @Override
            public void onShareClick(SmallVideo smallVideo) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, smallVideo.getSmallVideoUrl()
                );
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "share"));
            }

            @Override
            public void onCommentClick() {
              smallVideoCommentContentBottomDialog =
                        new SmallVideoCommentContentBottomDialog();
                smallVideoCommentContentBottomDialog.show(getSupportFragmentManager(),"dialog");
            }

            @Override
            public void onCommentAndInputClick() {
                smallVideoCommentContentBottomDialog =
                        new SmallVideoCommentContentBottomDialog();

                smallVideoCommentContentBottomDialog.show(getSupportFragmentManager(),"DialogAndInput");

            }
        });
    }

    private void releaseVideo(int index) {
        View itemView = recyclerView.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.iv_thumb);
        final ImageView imgPaly = itemView.findViewById(R.id.iv_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPaly.animate().alpha(0f).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void palyVideo(int position) {
        View itemView = recyclerView.getChildAt(position);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.iv_thumb);
        final ImageView imgPaly = itemView.findViewById(R.id.iv_play);
        final MediaPlayer[] mediaPlayers = new MediaPlayer[1];
        videoView.start();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayers[0] = mp;
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        imgPaly.setOnClickListener(new View.OnClickListener() {
            boolean isPalying = true;

            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    imgPaly.animate().alpha(1).start();
                    videoView.animate().alpha(1f).start();
                    videoView.pause();
                    isPalying = false;
                } else {
                    imgPaly.animate().alpha(0).start();
                    videoView.start();
                    isPalying = true;
                }
            }
        });
    }

    // 退出动画
    public void out() {
        finish();
        overridePendingTransition(0, R.anim.out);
    }

    // 拦截 获取 滑动距离
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = ev.getX();// 点击时的坐标
                break;
            case MotionEvent.ACTION_UP:
                currentX = ev.getX();// 抬起时的坐标
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            out();
            return false;
        }
    });
}
