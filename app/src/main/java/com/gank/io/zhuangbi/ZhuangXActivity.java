package com.gank.io.zhuangbi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.gank.io.R;
import com.gank.io.base.BaseActivity;

public class ZhuangXActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuang_x);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_zhuangbi);
        toolbar.setTitle(getResources().getString(R.string.zhuangbi_lable));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        ZhuangXFragment zhuangXFragment = new ZhuangXFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_zhuang_content, zhuangXFragment)
                .commit();
        new ZhuangXPresenter(zhuangXFragment);
    }
}
