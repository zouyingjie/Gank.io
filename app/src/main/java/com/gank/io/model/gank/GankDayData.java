package com.gank.io.model.gank;

import com.gank.io.constant.GankResourceType;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyingjie on 16/9/5.
 */

public class GankDayData {
    public List<String> category;
    public Result results;

    public static class Result {
        @SerializedName(GankResourceType.ANDROID) public List<Gank> androidList;
        @SerializedName(GankResourceType.IOS) public List<Gank> iosList;
        @SerializedName(GankResourceType.WELFARE) public List<Gank> welfareList;
        @SerializedName(GankResourceType.EXTRA) public List<Gank> extraList;
        @SerializedName(GankResourceType.FRONTEND) public List<Gank> frontEndList;
        @SerializedName(GankResourceType.CASUAL) public List<Gank> casualList;
        @SerializedName(GankResourceType.APP) public List<Gank> appList;
        @SerializedName(GankResourceType.VIDEO) public List<Gank> videoList;
    }


    public  List<GankDayItem> gankDayDataToGankItem() {
        GankDayData.Result result = this.results;
        List<GankDayItem> ganks = new ArrayList<>(10);

        if (result.iosList != null && result.iosList.size() > 0) {
            ganks.add(new GankDayTitleItem(GankResourceType.IOS));
            ganks.addAll(GankDayContentItem.items(result.iosList));
        }

        if (result.androidList != null && result.androidList.size() > 0) {
            ganks.add(new GankDayTitleItem(GankResourceType.ANDROID));
            ganks.addAll(GankDayContentItem.items(result.androidList));
        }

        if (result.frontEndList != null && result.frontEndList.size() > 0) {
            ganks.add(new GankDayTitleItem(GankResourceType.FRONTEND));
            ganks.addAll(GankDayContentItem.items(result.frontEndList));
        }

        if (result.casualList != null && result.casualList.size() > 0) {
            ganks.add(new GankDayTitleItem(GankResourceType.CASUAL));
            ganks.addAll(GankDayContentItem.items(result.casualList));
        }

        if (result.extraList != null && result.extraList.size() > 0) {
            ganks.add(new GankDayTitleItem(GankResourceType.EXTRA));
            ganks.addAll(GankDayContentItem.items(result.extraList));
        }

        if (result.appList != null && result.appList.size() > 0) {
            ganks.add(new GankDayTitleItem(GankResourceType.APP));
            ganks.addAll(GankDayContentItem.items(result.appList));
        }

        if (result.videoList != null && result.videoList.size() > 0) {
            ganks.add(new GankDayTitleItem(GankResourceType.VIDEO));
            ganks.addAll(GankDayContentItem.items(result.videoList));
        }

        if (result.welfareList != null && result.welfareList.size() > 0) {
            ganks.addAll(GankDayContentItem.items(result.welfareList));
        }
        return ganks;
    }

}
