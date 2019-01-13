package com.example.a79875.todaynews.enity;

import java.io.Serializable;

/**
 * Created by 你是我的 on 2019/1/6.
 */
public class SmallVideo implements Serializable {
    int smallVideoUrl;
    String smallVideoTitle;
    int smallVideoFacePic;

    public int getSmallVideoUrl() {
        return smallVideoUrl;
    }

    public void setSmallVideoUrl(int smallVideoUrl) {
        this.smallVideoUrl = smallVideoUrl;
    }

    public String getSmallVideoTitle() {
        return smallVideoTitle;
    }

    public void setSmallVideoTitle(String smallVideoTitle) {
        this.smallVideoTitle = smallVideoTitle;
    }

    public int getSmallVideoFacePic() {
        return smallVideoFacePic;
    }

    public void setSmallVideoFacePic(int smallVideoFacePic) {
        this.smallVideoFacePic = smallVideoFacePic;
    }
}
