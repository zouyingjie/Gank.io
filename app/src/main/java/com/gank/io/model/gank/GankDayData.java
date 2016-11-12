package com.gank.io.model.gank;

import com.gank.io.constant.Contants;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyingjie on 16/9/5.
 */

public class GankDayData  {
    public List<String> category;
    private Result results;

    public static class Result {
        @SerializedName(Contants.GANK_RESOUSE_TYPE_ANDROID) public List<Gank> androidList;
        @SerializedName(Contants.GANK_RESOUSE_TYPE_IOS) public List<Gank> iosList;
        @SerializedName(Contants.GANK_RESOUSE_TYPE_WELFARE) public List<Gank> welfareList;
        @SerializedName(Contants.GANK_RESOUSE_TYPE_EXTRA) public List<Gank> extraList;
        @SerializedName(Contants.GANK_RESOUSE_TYPE_FRONTEND) public List<Gank> frontEndList;
        @SerializedName(Contants.GANK_RESOUSE_TYPE_CASUAL) public List<Gank> casualList;
        @SerializedName(Contants.GANK_RESOUSE_TYPE_APP) public List<Gank> appList;
        @SerializedName(Contants.GANK_RESOUSE_TYPE_VIDEO) public List<Gank> videoList;
    }


    public  List<GankDayItem> gankDayDataToGankItem() {
        GankDayData.Result result = this.results;
        List<GankDayItem> ganks = new ArrayList<>(10);

        if (result.iosList != null && result.iosList.size() > 0) {
            ganks.add(new GankDayTitleItem(Contants.GANK_RESOUSE_TYPE_IOS));
            ganks.addAll(GankDayContentItem.items(result.iosList));
        }

        if (result.androidList != null && result.androidList.size() > 0) {
            ganks.add(new GankDayTitleItem(Contants.GANK_RESOUSE_TYPE_ANDROID));
            ganks.addAll(GankDayContentItem.items(result.androidList));
        }

        if (result.frontEndList != null && result.frontEndList.size() > 0) {
            ganks.add(new GankDayTitleItem(Contants.GANK_RESOUSE_TYPE_FRONTEND));
            ganks.addAll(GankDayContentItem.items(result.frontEndList));
        }

        if (result.casualList != null && result.casualList.size() > 0) {
            ganks.add(new GankDayTitleItem(Contants.GANK_RESOUSE_TYPE_CASUAL));
            ganks.addAll(GankDayContentItem.items(result.casualList));
        }

        if (result.extraList != null && result.extraList.size() > 0) {
            ganks.add(new GankDayTitleItem(Contants.GANK_RESOUSE_TYPE_EXTRA));
            ganks.addAll(GankDayContentItem.items(result.extraList));
        }

        if (result.appList != null && result.appList.size() > 0) {
            ganks.add(new GankDayTitleItem(Contants.GANK_RESOUSE_TYPE_APP));
            ganks.addAll(GankDayContentItem.items(result.appList));
        }

        if (result.videoList != null && result.videoList.size() > 0) {
            ganks.add(new GankDayTitleItem(Contants.GANK_RESOUSE_TYPE_VIDEO));
            ganks.addAll(GankDayContentItem.items(result.videoList));
        }

        if (result.welfareList != null && result.welfareList.size() > 0) {
            ganks.addAll(GankDayContentItem.items(result.welfareList));
        }
        return ganks;
    }

}
