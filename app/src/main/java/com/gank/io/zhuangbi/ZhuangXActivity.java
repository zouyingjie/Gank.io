package com.gank.io.zhuangbi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gank.io.R;

public class ZhuangXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuang_x);

        ZhuangXFragment zhuangXFragment = new ZhuangXFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_zhuang_content, zhuangXFragment)
                .commit();
        new ZhuangXPresenter(zhuangXFragment);
    }
}
