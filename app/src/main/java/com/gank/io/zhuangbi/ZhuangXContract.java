package com.gank.io.zhuangbi;

import com.gank.io.base.BasePresenter;
import com.gank.io.base.BaseView;
import com.gank.io.model.ZhuangXImage;

import java.util.List;

/**
 * Created by zouyingjie on 16/8/28.
 */

public class ZhuangXContract {

    interface View extends BaseView<Presenter>{
        void startLoad();
        void endLoad();
        void refreshImages(List<ZhuangXImage> images);
    }

    interface Presenter extends BasePresenter{
        void loadImage();
        void unsubscribe();
    }
}
