package com.gank.io.model.gank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyingjie on 16/9/5.
 */

public class GankDayContentItem extends Gank implements GankDayItem {

    private GankDayContentItem(Gank gank){
        this._id = gank._id;
        this.createdAt = gank.createdAt;
        this.desc = gank.desc;
        this.publishedAt = gank.publishedAt;
        this.type = gank.type;
        this.url = gank.url;
        this.used = gank.used;
        this.who = gank.who;
    }

    public static List<GankDayContentItem> items(List<Gank> ganks){
        List<GankDayContentItem> items = new ArrayList<>(10);
        for (Gank gank: ganks){
            items.add(new GankDayContentItem(gank));
        }
        return items;
    }
}
