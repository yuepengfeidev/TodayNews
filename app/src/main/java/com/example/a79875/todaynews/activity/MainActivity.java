package com.example.a79875.todaynews.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.Util.MesureWidgetHeight;
import com.example.a79875.todaynews.app.MyApplication;
import com.example.a79875.todaynews.enity.BotBean;
import com.example.a79875.todaynews.fragment.HomePageFragment;
import com.example.a79875.todaynews.fragment.MeFragment;
import com.example.a79875.todaynews.fragment.SmallVideoFragment;
import com.example.a79875.todaynews.fragment.VideoFragment;
import com.example.a79875.todaynews.widegt.BottomView;
import com.example.a79875.todaynews.widegt.MyViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<BotBean> itemIcon = new ArrayList<>();
    BottomView bottomView;
    MyViewPager myViewPager;

    public static final int INLAND = 1;
    public static final int SOCIAL = 2;
    public static final int INTERNATIONAL = 3;
    public static final int RECREATION = 4;
    public static final int SCIENCE = 5;
    public static final int SPORT = 6;
    public static final int HEALTH = 7;
    public static final int TRAVEL = 8;
    public static final int IT = 9;
    public static final int MILITARY = 10;
    public static final int NBA = 11;
    public static final int FOOTBALL = 12;
    public static final int BLOCKCHAIN = 13;

    /* private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<>();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        fragments.add(new HomePageFragment());
        fragments.add(new VideoFragment());
        fragments.add(new SmallVideoFragment());
        fragments.add(new MeFragment());

        itemIcon.add(new BotBean("首页", R.mipmap.home_page_icon));
        itemIcon.add(new BotBean("视频", R.mipmap.video_icon));
        itemIcon.add(new BotBean("小视频", R.mipmap.small_video_icon));
        itemIcon.add(new BotBean("我的", R.mipmap.me_icon));

        myViewPager = findViewById(R.id.my_view_pager);
        myViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));

        bottomView = findViewById(R.id.bottom);
        bottomView.setViewPager(myViewPager, itemIcon, new BottomView.BottomPageChangeListener() {
            @Override
            public void onBottomPageChangeListener(int position) {

            }
        });

        // 设置 bottomview高度的全局变量
        MesureWidgetHeight.mesureHeight(bottomView);

        // 由于在oncreate会出现没加载完毕就测量报空指针的情况
        bottomView.post(new Runnable() {
            @Override
            public void run() {
                MyApplication.setBottomViewHeight(bottomView.getHeight());
                MyApplication.setSoftInputHeight(MainActivity.this);// 设置软键盘高度的全局变量
            }
        });

    }

    // 不同新闻类型返回各自的请求地址
    public static String responseNew(int item) {
        // 初始化为天行数据的社会新闻请求地址,num=50：50条数据,rand=1:随机刷新
        String address = "http://api.tianapi.com/social/?key=0d04e5f2e6e958023505eb156bc56ef9&num=50&rand=1";
        switch (item) {
            case SOCIAL:
                address = address.replace("social", "social");// 更换为体育新闻请求地址
                break;
            case INLAND:
                address = address.replace("social", "guonei");
                break;
            case INTERNATIONAL:
                address = address.replace("social", "world");
                break;
            case RECREATION:
                address = address.replace("social", "huabian");
                break;
            case SCIENCE:
                address = address.replace("social", "keji");
                break;
            case SPORT:
                address = address.replace("social", "tiyu");
                break;
            case HEALTH:
                address = address.replace("social", "health");
                break;
            case TRAVEL:
                address = address.replace("social", "travel");
                break;
            case IT:
                address = address.replace("social", "it");
                break;
            case MILITARY:
                address = address.replace("social", "military");
                break;
            case NBA:
                address = address.replace("social", "nba");
                break;
            case FOOTBALL:
                address = address.replace("social", "football");
                break;
            case BLOCKCHAIN:
                address = address.replace("social", "blockchain");
                break;
            default:
                break;
        }
        return address;
    }


    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
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

}
