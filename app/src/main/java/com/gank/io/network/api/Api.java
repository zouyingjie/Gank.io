package com.gank.io.network.api;

import com.gank.io.model.GankGirlResult;
import com.gank.io.model.ZhuangXImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zouyingjie on 16/9/1.
 */

public interface Api {
    @GET("data/福利/{number}/{page}")
    Observable<GankGirlResult> getBeauties(@Path("number") int number, @Path("page") int page);

    @GET("search")
    Call<List<ZhuangXImage>> loadImage(@Query("q") String query);
}

