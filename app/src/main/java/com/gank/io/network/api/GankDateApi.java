package com.gank.io.network.api;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zouyingjie on 16/9/7.
 */

public interface GankDateApi {
    @GET("day/history")
    Observable<com.gank.io.today.GankDate> getDay();
}
