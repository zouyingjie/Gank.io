package com.gank.io.base;

/**
 * Created by zouyingjie on 16/8/26.
 */

public interface  BaseView<T> {
    void setPresenter(T presenter);

    void removePresenter();

    void showToastTip(String tip);
}
