package com.gank.io.base;

import android.app.Application;

import com.anupcowkur.reservoir.Reservoir;

import java.io.IOException;

/**
 * Created by zouyingjie on 16/9/8.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Reservoir.init(this, 4096);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
