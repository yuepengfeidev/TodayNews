package com.example.a79875.todaynews.enity;

import java.io.Serializable;

/**
 * Created by 你是我的 on 2019/1/3.
 */

// 视频实体类
public class Video implements Serializable {

    String videoUrl;
    String videoTitle;
    String videofacePic;
    String videoAuthor;
    String videoDesctiption;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideofacePic() {
        return videofacePic;
    }

    public void setVideofacePic(String videofacePic) {
        this.videofacePic = videofacePic;
    }

    public String getVideoAuthor() {
        return videoAuthor;
    }

    public void setVideoAuthor(String videoAuthor) {
        this.videoAuthor = videoAuthor;
    }

    public String getVideoDesctiption() {
        return videoDesctiption;
    }

    public void setVideoDesctiption(String videoDesctiption) {
        this.videoDesctiption = videoDesctiption;
    }
}
