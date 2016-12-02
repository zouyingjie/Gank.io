package com.gank.io.today;

import com.gank.io.base.BasePresenter;
import com.gank.io.base.BaseView;
import com.gank.io.model.gank.GankDayItem;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zouyingjie on 16/9/6.
 */

public class TodayContract {

    public interface Presenter extends BasePresenter {

        void loadTodayGankData();

        void loadData(Calendar c);

        void unsubscribe();

    }

    public interface View extends BaseView<Presenter> {
        void loadTodayGankData(List<GankDayItem> gankDayItems);
    }
}
