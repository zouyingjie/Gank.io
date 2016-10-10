package com.gank.io.zhuangbi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gank.io.R;

public class ZhuangXActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuang_x);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_zhuangbi);
        toolbar.setTitle(getResources().getString(R.string.zhuangbi_lable));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ZhuangXFragment zhuangXFragment = new ZhuangXFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_zhuang_content, zhuangXFragment)
                .commit();
        new ZhuangXPresenter(zhuangXFragment);
    }
}
