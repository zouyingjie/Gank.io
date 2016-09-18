package com.gank.io.model.gank;

import com.gank.io.constant.GankResourceType;
import com.google.gson.annotations.SerializedName;

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
}
