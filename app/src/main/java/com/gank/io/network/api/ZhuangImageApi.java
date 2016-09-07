package com.gank.io.network.api;

import com.gank.io.model.ZhuangXImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zouyingjie on 16/9/7.
 */

public interface ZhuangImageApi {
    @GET("search")
    Call<List<ZhuangXImage>> loadImage(@Query("q") String query);
}
