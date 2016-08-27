package com.gank.io.girl;

import android.support.annotation.Nullable;

import com.gank.io.network.api.Api;
import com.gank.io.util.GankBeautyResultToItemsMapper;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GirlPresenter implements GirlContract.Presenter {
    private GirlContract.View girlView;
    private int page = 0;

    public GirlPresenter(@Nullable GirlContract.View girlView){
        this.girlView = girlView;
        this.girlView.setPresenter(this);

    }

    @Override
    public void loadImage() {
        girlView.unsubscribe();
        girlView.startRefresh();
        Api.getGankApi()
                .getBeauties(10, page++)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(girlView.observer());
    }

    @Override
    public void showImage() {

    }

    @Override
    public void start() {

    }
}
