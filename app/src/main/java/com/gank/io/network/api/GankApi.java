package com.gank.io.network.api;


import com.gank.io.model.GankGirlResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zouyingjie on 16/8/26.
 */

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankGirlResult> getBeauties(@Path("number") int number, @Path("page") int page);
}