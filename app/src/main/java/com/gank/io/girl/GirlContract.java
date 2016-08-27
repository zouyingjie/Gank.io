package com.gank.io.girl;

import com.gank.io.BasePresenter;
import com.gank.io.BaseView;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GirlContract {

    interface View extends BaseView<Presenter> {
        void refreshView();

    }

    interface Presenter extends BasePresenter{
        void loadImage();

        void showImage();
    }
}
