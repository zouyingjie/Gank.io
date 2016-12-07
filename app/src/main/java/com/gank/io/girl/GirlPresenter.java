package com.gank.io.girl;

import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gank.io.R;
import com.gank.io.model.girl.GankGirlItem;
import com.gank.io.network.ApiService;
import com.gank.io.util.GankBeautyResultToItemsMapper;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GirlPresenter implements GirlContract.Presenter {

    private int page = 1;
    private GirlContract.View girlView;
    private Subscription subscription;

    private Observer<List<GankGirlItem>> observer = new Observer<List<GankGirlItem>>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            girlView.endPullRefresh();
            Toast.makeText(((GirlFragment) girlView).getContext(), ((GirlFragment) girlView).getContext().getString(R.string.access_data_fail_tip), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<GankGirlItem> images) {
            girlView.endPullRefresh();
            if (images.size() > 0) {
                girlView.refreshImages(images);
            } else {
                Toast.makeText(((GirlFragment) girlView).getContext(), ((GirlFragment) girlView).getContext().getString(R.string.access_data_fail_tip), Toast.LENGTH_SHORT).show();
            }

        }
    };

    public GirlPresenter(@Nullable GirlContract.View girlView) {
        this.girlView = girlView;
        this.girlView.setPresenter(this);
    }

    @Override
    public void loadImage() {
        unsubscribe();
        girlView.startPullRefresh();
        subscription = ApiService.getGankApi()
                .getGirls(10, page++)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
