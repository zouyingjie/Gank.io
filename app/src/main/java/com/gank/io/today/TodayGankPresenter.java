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
import rx.functions.Func1;
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
          //Lambda表达式可以简化代码,但也会降低代码的可读性,两者之间取舍
//        ApiService.getGankApi().getHistoryDate()
//                .map(GankDate::getLastDate)
//                .flatMap(this::getGankDayData)
//                .map(GankDayData::gankDayDataToGankItem)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);

        ApiService.getGankApi().getHistoryDate()
                .map(new Func1<GankDate, Calendar>() {

                    @Override
                    public Calendar call(GankDate gankDate) {

                        return gankDate.getLastDate();
                    }
                })
                .flatMap(new Func1<Calendar, Observable<GankDayData>>() {
                    @Override
                    public Observable<GankDayData> call(Calendar calendar) {
                        return getGankDayData(calendar);
                    }
                })
                .map(new Func1<GankDayData, List<GankDayItem>>() {
                    @Override
                    public List<GankDayItem> call(GankDayData dayData) {
                        return dayData.gankDayDataToGankItem();
                    }
                }).subscribeOn(Schedulers.io())
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
