package com.gank.io.network;

import com.gank.io.constant.Contants;
import com.gank.io.network.api.GankApi;
import com.gank.io.network.api.ZhuangImageApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class ApiService {
    private static GankApi gankApi;
    private static ZhuangImageApi zhuangXApi;

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static final CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static GankApi getGankApi() {
        if (gankApi == null) {
            gankApi = createRetrofit(Contants.GANK_BASE_URL).create(GankApi.class);
        }
        return gankApi;
    }

    public static ZhuangImageApi getZhuangXApi() {
        if (zhuangXApi == null) {
            zhuangXApi = createRetrofit(Contants.ZHUANGX_BASE_URL).create(ZhuangImageApi.class);
        }
        return zhuangXApi;
    }


    private static Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

}
