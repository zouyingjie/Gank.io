package com.gank.io.network.api;

import com.gank.io.model.GankGirlResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zouyingjie on 16/9/7.
 */

public interface GankGirlApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankGirlResult> getGirls(@Path("number") int number, @Path("page") int page);
}
