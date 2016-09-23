package com.gank.io.zhuangbi;

import android.support.annotation.NonNull;

import com.gank.io.model.ZhuangXImage;
import com.gank.io.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zouyingjie on 16/8/28.
 */

public class ZhuangXPresenter implements ZhuangXContract.Presenter {

    private ZhuangXContract.View zhuagnxView;


    public ZhuangXPresenter(@NonNull ZhuangXContract.View view) {
        this.zhuagnxView = view;
        this.zhuagnxView.setPresenter(this);
    }

    @Override
    public void loadImage() {
        Call<List<ZhuangXImage>> call = ApiService.getZhuangXApi()
                .loadImage("装逼");
        call.enqueue(new Callback<List<ZhuangXImage>>() {
            @Override
            public void onResponse(Call<List<ZhuangXImage>> call, Response<List<ZhuangXImage>> response) {
                List<ZhuangXImage> zhuangXImages = response.body();
                List<ZhuangXImage> imageDatas = new ArrayList<ZhuangXImage>();
                //只显示jpg、png和gif三种格式的图片
                for (ZhuangXImage image : zhuangXImages) {
                    if (image.image_url.contains(".jpg")
                            || image.image_url.contains(".png")
                            || image.image_url.contains(".gif")) {
                        imageDatas.add(image);
                    }
                }
                zhuagnxView.refreshImages(imageDatas);
            }

            @Override
            public void onFailure(Call<List<ZhuangXImage>> call, Throwable t) {

            }
        });


    }

    @Override
    public void unsubscribe() {

    }

}
