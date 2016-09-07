package com.gank.io.network.api;

import com.gank.io.model.gank.GankDayData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zouyingjie on 16/9/7.
 */

public interface GankDayApi {

    @GET("day/{date}")
    Observable<GankDayData> getDayData(@Path("date")String date);
}
