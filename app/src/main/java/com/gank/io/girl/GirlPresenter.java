package com.gank.io.girl;

import android.content.ClipData;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.gank.io.R;
import com.gank.io.model.GankGirlItem;
import com.gank.io.network.api.Api;
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

    public GirlPresenter(@Nullable GirlContract.View girlView){
        this.girlView = girlView;
        this.girlView.setPresenter(this);

    }
//    @Override
//    public void loadImage(GirlFragment.GirlAdapter adapter) {
////        unsubscribe();
////              subscription = Api.getGankApi()
////                .getBeauties(10, 2)
////                .map(GankBeautyResultToItemsMapper.getInstance())
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(observer);
//
//
////        Observer<List<GankGirlItem>> observer = new Observer<List<GankGirlItem>>() {
////            @Override
////            public void onCompleted() {
////            }
////
////            @Override
////            public void onError(Throwable e) {
//////                Toast.makeText(girlView.getContext, R.string.loading_failed, Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onNext(List<ClipData.Item> images) {
////                pageTv.setText(getString(R.string.page_with_number, page));
////                adapter.setItems(images);
////            }
////        };
//
//    }

    @Override
    public void loadImage() {

    }

    @Override
    public void showImage() {

    }

    @Override
    public void start() {

    }
}
