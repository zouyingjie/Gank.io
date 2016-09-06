package com.gank.io.girl;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gank.io.model.gank.GankGirlItem;
import com.gank.io.network.api.ApiService;
import com.gank.io.util.GankBeautyResultToItemsMapper;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GirlPresenter implements GirlContract.Presenter {
    private GirlContract.View girlView;
    private int page = 0;
    private Subscription subscription;

    Observer<List<GankGirlItem>> observer = new Observer<List<GankGirlItem>>() {
        @Override
        public void onCompleted() {
            Toast.makeText(((GirlFragment) girlView).getContext(), "Completed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(Throwable e) {
            girlView.endRefresh();
            Toast.makeText(((GirlFragment) girlView).getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<GankGirlItem> images) {
            girlView.endRefresh();
            girlView.refreshImages(images);
        }
    };

    public GirlPresenter(@Nullable GirlContract.View girlView) {
        this.girlView = girlView;
        this.girlView.setPresenter(this);
    }

    @Override
    public void loadImage() {
        unsubscribe();
        girlView.startRefresh();
        subscription = ApiService.getGankApi()
                .getBeauties(10, page++)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void start() {}

    @Override
    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
