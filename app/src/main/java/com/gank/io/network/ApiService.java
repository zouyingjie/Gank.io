package com.gank.io.network;

import com.gank.io.network.api.GankCategoryApi;
import com.gank.io.network.api.GankDateApi;
import com.gank.io.network.api.GankDayApi;
import com.gank.io.network.api.GankGirlApi;
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
    private static GankCategoryApi gankCategoryApi;
    private static GankGirlApi gankGirlApi;
    private static ZhuangImageApi zhuangXApi;
    private static GankDayApi gankDayApi;
    private static GankDateApi gankDateApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    /**
     * 获取Gank妹子图片
     *
     * @return
     */
    public static GankGirlApi getGankGirlApi() {
        if (gankGirlApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)//设置CallAdapter
                    //这里设置的是RxJavaCallAdapter。。。默认是ExecutorCallAdapterFactory,返回的是默认的call
                    .build();
            gankGirlApi = retrofit.create(GankGirlApi.class);//创建类
        }
        return gankGirlApi;
    }

    /**
     * 获取装X大全图片
     *
     * @return
     */
    public static ZhuangImageApi getZhuangXApi() {
        if (zhuangXApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://zhuangbi.info/")
                    .addConverterFactory(gsonConverterFactory)
                    .build();
            zhuangXApi = retrofit.create(ZhuangImageApi.class);

        }

        return zhuangXApi;
    }

    /**
     * 获取今日数据
     */

    public static GankDayApi getGankDayApi() {
        if (gankDayApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankDayApi = retrofit.create(GankDayApi.class);
        }
        return gankDayApi;
    }

    /**
     * 获取发布过的日期
     */

    public static GankDateApi getDayApi() {
        if (gankDateApi == null) {
            Retrofit retrofit = new Retrofit.Builder()//初始化的时候确定系统平台
                    .client(okHttpClient)//设置Http客户端
                    .baseUrl("http://gank.io/api/")//设置访问的基本url
                    .addConverterFactory(gsonConverterFactory)//设置数据类型转化类:gson
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankDateApi = retrofit.create(GankDateApi.class);
        }
        return gankDateApi;
    }

    public static GankCategoryApi getGankCategoryApi() {
        if (gankCategoryApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/search/query/listview/category/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankCategoryApi = retrofit.create(GankCategoryApi.class);
        }
        return gankCategoryApi;
    }
}
