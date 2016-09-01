package com.gank.io.network.api;

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
    private static Api gankApi;
    private static Api zhuangXApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    /**
     * 获取Gank妹子图片
     * @return
     */
    public static Api getGankApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankApi = retrofit.create(Api.class);
        }
        return gankApi;
    }

    /**
     * 获取装X大全图片
     * @return
     */
    public static Api getZhuangXApi(){
        if (zhuangXApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://zhuangbi.info/")
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            zhuangXApi = retrofit.create(Api.class);

        }

        return zhuangXApi;
    }
}
