package com.gank.io.girl;

import com.gank.io.base.BasePresenter;
import com.gank.io.base.BaseView;
import com.gank.io.model.girl.GankGirlItem;

import java.util.List;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GirlContract {

    interface View extends BaseView<Presenter> {

        void startPullRefresh();

        void endPullRefresh();

        void refreshImages(List<GankGirlItem> images);

    }

    interface Presenter extends BasePresenter {

        void loadImage();

        void unsubscribe();
    }
}
