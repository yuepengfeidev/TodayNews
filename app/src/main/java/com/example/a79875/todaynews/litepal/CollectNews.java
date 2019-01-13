package com.example.a79875.todaynews.litepal;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

/**
 * Created by 你是我的 on 2019/1/1.
 */

// 收藏存储
public class CollectNews extends LitePalSupport {
    private String title;
    private String description;
    private String facePicUrl;// 封面图片
    private String Url;// 新闻url 或者 视频url
    private String type;// video / news
    private String authorName;// 视频作者 / 新闻来源

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacePicUrl() {
        return facePicUrl;
    }

    public void setFacePicUrl(String facePicUrl) {
        this.facePicUrl = facePicUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
