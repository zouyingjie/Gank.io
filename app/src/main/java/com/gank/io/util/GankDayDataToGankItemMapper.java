package com.gank.io.util;

import com.gank.io.model.GankDayContentItem;
import com.gank.io.model.GankDayData;
import com.gank.io.model.GankDayItem;
import com.gank.io.model.GankDayTitleItem;
import com.gank.io.constant.GankResourceType;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by zouyingjie on 16/9/5.
 */

public class GankDayDataToGankItemMapper implements Func1<GankDayData, List<GankDayItem>> {
    private static GankDayDataToGankItemMapper INSTANCE = new GankDayDataToGankItemMapper();

    private GankDayDataToGankItemMapper() {
    }

    public static GankDayDataToGankItemMapper getInstance() {
        return INSTANCE;
    }

    public List<GankDayItem> call(GankDayData dayData) {

        GankDayData.Result result = dayData.results;
        List<GankDayItem> ganks = new ArrayList<>(10);

        if (result.iosList != null && result.iosList.size() > 0){
            ganks.add(new GankDayTitleItem(GankResourceType.IOS));
            ganks.addAll(GankDayContentItem.items(result.iosList));
        }

        if (result.androidList != null && result.androidList.size() > 0){
            ganks.add(new GankDayTitleItem(GankResourceType.ANDROID));
            ganks.addAll(GankDayContentItem.items(result.androidList));
        }

        if (result.frontEndList != null && result.frontEndList.size() > 0){
            ganks.add(new GankDayTitleItem(GankResourceType.FRONTEND));
            ganks.addAll(GankDayContentItem.items(result.frontEndList));
        }

        if (result.casualList != null && result.casualList.size() > 0){
            ganks.add(new GankDayTitleItem(GankResourceType.CASUAL));
            ganks.addAll(GankDayContentItem.items(result.casualList));
        }

        if (result.extraList != null && result.extraList.size() > 0){
            ganks.add(new GankDayTitleItem(GankResourceType.EXTRA));
            ganks.addAll(GankDayContentItem.items(result.extraList));
        }

        if (result.appList != null && result.appList.size() > 0){
            ganks.add(new GankDayTitleItem(GankResourceType.APP));
            ganks.addAll(GankDayContentItem.items(result.appList));
        }

        if (result.videoList != null && result.videoList.size() > 0){
            ganks.add(new GankDayTitleItem(GankResourceType.VIDEO));
            ganks.addAll(GankDayContentItem.items(result.videoList));
        }

        if (result.welfareList != null && result.welfareList.size() > 0){
            ganks.addAll(GankDayContentItem.items(result.welfareList));
        }
        return ganks;
    }
}
