package com.gank.io.today;

import android.support.annotation.NonNull;

import com.gank.io.model.gank.GankDate;
import com.gank.io.model.gank.GankDayData;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.network.ApiService;

import java.util.Calendar;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zouyingjie on 16/9/6.
 */

public class TodayGankPresenter implements TodayContract.Presenter {
    private TodayContract.View todayGankView;
    private Subscription subscription;

    Observer<List<GankDayItem>> observer = new Observer<List<GankDayItem>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            todayGankView.showErrorTip();
        }

        @Override
        public void onNext(List<GankDayItem> gankDayItems) {
            todayGankView.loadTodayGankData(gankDayItems);
        }
    };

    private TodayGankPresenter(@NonNull TodayContract.View todayGankView) {
        this.todayGankView = todayGankView;
    }

    public static TodayGankPresenter getInstance(@NonNull TodayContract.View todayGankView) {
        return new TodayGankPresenter(todayGankView);
    }

    @Override
    public void loadTodayGankData() {
        unsubscribe();
        ApiService.getGankApi().getHistoryDate()
                .map(GankDate::getLastDate)
                .flatMap(this::getGankDayData)
                .map(GankDayData::gankDayDataToGankItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取对应日期的数据
     *
     * @param c
     * @return
     */
    private Observable<GankDayData> getGankDayData(Calendar c) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(c.get(Calendar.YEAR))
                .append("/")
                .append(c.get(Calendar.MONTH))
                .append("/")
                .append(c.get(Calendar.DAY_OF_MONTH));
        return ApiService.getGankApi().getDataByDate(buffer.toString());
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
