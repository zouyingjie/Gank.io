package com.gank.io.today;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.gank.io.R;
import com.gank.io.model.gank.GankDayData;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.network.ApiService;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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
    private Context context;

    Observer<List<GankDayItem>> observer = new Observer<List<GankDayItem>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            todayGankView.showToastTip(context.getString(R.string.access_data_fail_tip));
        }

        @Override
        public void onNext(List<GankDayItem> gankDayItems) {
            if (gankDayItems.size() > 0) {
                Reservoir.putUsingObservable("current_data", gankDayItems).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.i("LALALA","缓存完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        todayGankView.showToastTip(context.getString(R.string.access_data_fail_tip));
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        todayGankView.loadTodayGankData(gankDayItems);
                    }
                });



            } else {
                todayGankView.showToastTip(context.getString(R.string.access_data_fail_tip));
            }

        }
    };


    private TodayGankPresenter(@NonNull TodayContract.View todayGankView) {
        this.todayGankView = todayGankView;
        this.context = (Context) todayGankView;
    }

    public static TodayGankPresenter getInstance(@NonNull TodayContract.View todayGankView) {
        return new TodayGankPresenter(todayGankView);
    }

    @Override
    public void loadTodayGankData() {
        try {
            boolean b = Reservoir.contains("current_data");
            Log.i("LALALA", b+"");
            final Type testResultType = new TypeToken<List<GankDayItem>>() {}.getType();
            Log.i("LALALA", testResultType.toString()+"");
            Reservoir.getAsync("current_data", testResultType, new ReservoirGetCallback<List<GankDayItem>>() {

                @Override
                public void onSuccess(List<GankDayItem> gankDayItems) {
                    Log.i("LALALA", "取值成功");
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        unsubscribe();
        //Lambda表达式或者方法引用以简化代码,但也会降低代码的可读性,两者之间合理取舍
        ApiService.getGankApi().getHistoryDate()
                .map(gankDate -> gankDate.getLastDate())
                .flatMap(calendar -> getGankDataByDate(calendar))
                .map(dayData -> dayData.gankDayDataToGankItem())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void loadData(Calendar c) {
        unsubscribe();
        getGankDataByDate(c).map(dayData -> dayData.gankDayDataToGankItem())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    private Observable<GankDayData> getGankDataByDate(Calendar c) {
        String date = String.valueOf(c.get(Calendar.YEAR)) +
                "/" +
                c.get(Calendar.MONTH) +
                "/" +
                c.get(Calendar.DAY_OF_MONTH);

        return ApiService.getGankApi().getDataByDate(date);
    }

    @Override
    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
