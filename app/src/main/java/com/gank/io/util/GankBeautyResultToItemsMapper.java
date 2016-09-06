package com.gank.io.util;

import com.gank.io.model.GankGirl;
import com.gank.io.model.gank.GankGirlItem;
import com.gank.io.model.GankGirlResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GankBeautyResultToItemsMapper implements Func1<GankGirlResult, List<GankGirlItem>> {
    private static GankBeautyResultToItemsMapper INSTANCE = new GankBeautyResultToItemsMapper();

    private GankBeautyResultToItemsMapper() {
    }

    public static GankBeautyResultToItemsMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<GankGirlItem> call(GankGirlResult gankBeautyResult) {
        List<GankGirl> gankBeauties = gankBeautyResult.beauties;
        List<GankGirlItem> items = new ArrayList<>(gankBeauties.size());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        for (GankGirl girl : gankBeauties) {
            GankGirlItem item = new GankGirlItem();
            try {
                Date date = inputFormat.parse(girl.createdAt);
                item.description = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                item.description = "unknown date";
            }
            item.imageUrl = girl.url;
            items.add(item);
        }
        return items;
    }
}