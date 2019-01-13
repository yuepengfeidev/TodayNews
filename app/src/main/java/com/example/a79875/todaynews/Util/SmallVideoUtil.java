package com.example.a79875.todaynews.Util;

import com.example.a79875.todaynews.R;
import com.example.a79875.todaynews.enity.SmallVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 你是我的 on 2019/1/6.
 */

// 小视频数据
public class SmallVideoUtil {
    List<SmallVideo> smallVideoList = new ArrayList<>();
    List<Integer> smallVideoUrls = new ArrayList<>();
    List<String> smallVideoTitles = new ArrayList<>();
    List<Integer> smallVideoFacePics = new ArrayList<>();

    public SmallVideoUtil() {
        setSmallVideoFacePics();
        setSmallVideoUrls();
        setSmallVideoTitles();
        setSmallVideoList();
    }

    public List<SmallVideo> getSmallVideoList() {
        return smallVideoList;
    }

    public void setSmallVideoList() {
        smallVideoList.clear();
        for (int i =0 ; i < smallVideoUrls.size(); i++){
            SmallVideo smallVideo = new SmallVideo();
            smallVideo.setSmallVideoUrl(smallVideoUrls.get(i));
            smallVideo.setSmallVideoTitle(smallVideoTitles.get(i));
            smallVideo.setSmallVideoFacePic(smallVideoFacePics.get(i));
            smallVideoList.add(smallVideo);
        }
    }

    public List<Integer> getSmallVideoUrls() {
        return smallVideoUrls;
    }

    public void setSmallVideoUrls() {
        smallVideoUrls.add(R.raw.sv_1);
        smallVideoUrls.add(R.raw.sv_2);
        smallVideoUrls.add(R.raw.sv_3);
        smallVideoUrls.add(R.raw.sv_4);
        smallVideoUrls.add(R.raw.sv_5);
        smallVideoUrls.add(R.raw.sv_6);
        smallVideoUrls.add(R.raw.sv_7);
        smallVideoUrls.add(R.raw.sv_8);
        smallVideoUrls.add(R.raw.sv_1);
        smallVideoUrls.add(R.raw.sv_2);
        smallVideoUrls.add(R.raw.sv_3);
        smallVideoUrls.add(R.raw.sv_4);
        smallVideoUrls.add(R.raw.sv_5);
        smallVideoUrls.add(R.raw.sv_6);
        smallVideoUrls.add(R.raw.sv_7);
        smallVideoUrls.add(R.raw.sv_8);
    }

    public List<String> getSmallVideoTitles() {
        return smallVideoTitles;
    }

    public void setSmallVideoTitles() {
        smallVideoTitles.add("7点直播，八年级期末总复习，九年级啃骨头");
        smallVideoTitles.add("#岳云鹏# 没有你们不会的！");
        smallVideoTitles.add("狗妹真是萌哭了");
        smallVideoTitles.add("卸载王者和吃鸡，读完这五本书");
        smallVideoTitles.add("托尼老师剪的造型");
        smallVideoTitles.add("Java零基础入门");
        smallVideoTitles.add("斗地主斗到怀疑人生");
        smallVideoTitles.add("计算机大神会修电脑吗？");
        smallVideoTitles.add("7点直播，八年级期末总复习，九年级啃骨头");
        smallVideoTitles.add("#岳云鹏# 没有你们不会的！");
        smallVideoTitles.add("狗妹真是萌哭了");
        smallVideoTitles.add("卸载王者和吃鸡，读完这五本书");
        smallVideoTitles.add("托尼老师剪的造型");
        smallVideoTitles.add("Java零基础入门");
        smallVideoTitles.add("斗地主斗到怀疑人生");
        smallVideoTitles.add("计算机大神会修电脑吗？");
    }

    public List<Integer> getSmallVideoFacePics() {
        return smallVideoFacePics;
    }

    public void setSmallVideoFacePics() {
        smallVideoFacePics.add(R.mipmap.fp_11);
        smallVideoFacePics.add(R.mipmap.fp_2);
        smallVideoFacePics.add(R.mipmap.fp_3);
        smallVideoFacePics.add(R.mipmap.fp_4);
        smallVideoFacePics.add(R.mipmap.fp_5);
        smallVideoFacePics.add(R.mipmap.fp_6);
        smallVideoFacePics.add(R.mipmap.fp_7);
        smallVideoFacePics.add(R.mipmap.fp_8);
        smallVideoFacePics.add(R.mipmap.fp_11);
        smallVideoFacePics.add(R.mipmap.fp_2);
        smallVideoFacePics.add(R.mipmap.fp_3);
        smallVideoFacePics.add(R.mipmap.fp_4);
        smallVideoFacePics.add(R.mipmap.fp_5);
        smallVideoFacePics.add(R.mipmap.fp_6);
        smallVideoFacePics.add(R.mipmap.fp_7);
        smallVideoFacePics.add(R.mipmap.fp_8);
    }
}
