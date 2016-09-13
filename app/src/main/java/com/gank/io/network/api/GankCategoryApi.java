package com.gank.io.network.api;

import com.gank.io.model.gank.GankCategory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zouyingjie on 16/9/13.
 */

public interface GankCategoryApi {

    @GET("{category}/count/{count}/page/1")
    Observable<GankCategory> getCategory(@Path("category") String catrgory, @Path("count")String count);
}
