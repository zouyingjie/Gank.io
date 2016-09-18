package com.gank.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zouyingjie on 16/9/5.
 */

public class GankDayData {
    public List<String> category;
    public Result results;

    public static class Result {
        @SerializedName("Android") public List<Gank> androidList;
        @SerializedName("iOS") public List<Gank> iosList;
        @SerializedName("福利") public List<Gank> welfareList;
        @SerializedName("拓展资源") public List<Gank> extraList;
        @SerializedName("前端") public List<Gank> frontEndList;
        @SerializedName("瞎推荐") public List<Gank> casualList;
        @SerializedName("App") public List<Gank> appList;
        @SerializedName("休息视频") public List<Gank> videoList;
    }
}
