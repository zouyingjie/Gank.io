package com.gank.io.model;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GankGirlItem implements GankDayItem{
    public String description;
    public String imageUrl;

    public GankGirlItem(){}

    public GankGirlItem(Gank gank){
        this.imageUrl = gank.url;
        this.description = gank.desc;
    }
}
