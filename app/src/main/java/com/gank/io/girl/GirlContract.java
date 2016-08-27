package com.gank.io.girl;

import com.gank.io.BasePresenter;
import com.gank.io.BaseView;

import rx.Observer;
import rx.Subscription;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GirlContract {

    interface View extends BaseView<Presenter> {

        void unsubscribe();
        void startRefresh();
        void endRefresh();
        Observer observer();
        Subscription subscription();
        void setSubscription();

    }

    interface Presenter extends BasePresenter{
        void loadImage();

        void showImage();
    }
}
