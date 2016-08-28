package com.gank.io.girl;

import com.gank.io.BasePresenter;
import com.gank.io.BaseView;
import com.gank.io.model.GankGirlItem;

import java.util.List;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GirlContract {

    interface View extends BaseView<Presenter> {

        void startRefresh();
        void endRefresh();
        void refreshImages(List<GankGirlItem> images);

    }

    /**
     * GirlFragment显示妹子图片列表:
     * 加载图片,
     * 下拉刷新,本质都是访问网络刷新图片,
     * 如果使用RxJava还涉及到解除注册。
     *
     * View中只有与界面更新有关的操作。具体的逻辑全部放在Presenter中
     *
     */
    interface Presenter extends BasePresenter{

        void loadImage();

        void unsubscribe();
    }
}
