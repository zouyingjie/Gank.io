package com.gank.io.network.api;

import com.gank.io.model.gank.GankCategory;
import com.gank.io.model.gank.GankDate;
import com.gank.io.model.gank.GankDayData;
import com.gank.io.model.girl.GankGirlResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zouyingjie on 16/9/18.
 */

public interface GankApi {
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    Observable<GankCategory> getDataByCategory(@Path("category") String category, @Path("count")String count, @Path("page") String page);

    @GET("day/history")
    Observable<GankDate> getHistoryDate();

    @GET("day/{date}")
    Observable<GankDayData> getDataByDate(@Path("date")String date);

    @GET("data/福利/{number}/{page}")
    Observable<GankGirlResult> getGirls(@Path("number") int number, @Path("page") int page);

}
