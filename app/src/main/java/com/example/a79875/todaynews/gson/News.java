package com.example.a79875.todaynews.gson;

import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("citme")
    public String time;
    public String title;
    public String description;
    public String picUrl;
    public String url;
}
