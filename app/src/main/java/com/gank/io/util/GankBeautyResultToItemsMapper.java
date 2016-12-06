package com.gank.io.util;

import com.gank.io.model.gank.Gank;
import com.gank.io.model.girl.GankGirlItem;
import com.gank.io.model.girl.GankGirlResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rx.functions.Func1;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GankBeautyResultToItemsMapper implements Func1<GankGirlResult, List<GankGirlItem>> {
    private static final GankBeautyResultToItemsMapper INSTANCE = new GankBeautyResultToItemsMapper();

    private GankBeautyResultToItemsMapper() {
    }

    public static GankBeautyResultToItemsMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<GankGirlItem> call(GankGirlResult gankBeautyResult) {
        List<Gank> gankBeauties = gankBeautyResult.beauties;
        List<GankGirlItem> items = new ArrayList<>(gankBeauties.size());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'", Locale.CHINA);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss", Locale.CHINA);
        for (Gank gank : gankBeauties) {
            GankGirlItem item = new GankGirlItem();
            try {
                Date date = inputFormat.parse(gank.createdAt);
                item.description = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                item.description = "unknown date";
            }
            item.imageUrl = gank.url;
            items.add(item);
        }
        return items;
    }
}