package com.gank.io.today;

import android.support.annotation.NonNull;

import com.gank.io.model.gank.GankDayData;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.network.ApiService;
import com.gank.io.util.GankDayDataToGankItemMapper;

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
        }

        @Override
        public void onNext(List<GankDayItem> gankDayItems) {
            todayGankView.loadTodayGankData(gankDayItems);
        }
    };


    public TodayGankPresenter(@NonNull TodayContract.View todayGankView) {
        this.todayGankView = todayGankView;
        todayGankView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void loadTodayGankData() {
        unsubscribe();
        ApiService.getDayApi().getDay()
                .map(new Func1<GankDate, Calendar>() {
                    @Override
                    public Calendar call(GankDate gankDate) {
                        return getLastDate(gankDate);
                    }
                })
                .flatMap(new Func1<Calendar, Observable<GankDayData>>() {
                    @Override
                    public Observable<GankDayData> call(Calendar c) {
                        return getGankDayData(c);

                    }
                }).map(GankDayDataToGankItemMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 获取Gank最近更新的日期
     * @param gankDate
     * @return
     */
    private Calendar getLastDate(GankDate gankDate) {
        String[] date = gankDate.results.get(0).split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(date[0]));
        calendar.set(Calendar.MONTH, Integer.valueOf(date[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date[2]));
        return calendar;
    }

    /**
     * 获取对应日期的数据
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

        return ApiService.getGankDayApi().getDayData(buffer.toString());
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
