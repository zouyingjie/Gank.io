package com.gank.io.girl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.gank.io.R;
import com.gank.io.base.BaseActivity;

public class GirlActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_girl);
        toolbar.setTitle(getResources().getString(R.string.girl_lable));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        GirlFragment fragment = new GirlFragment();
        new GirlPresenter(
                fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_girl_content, fragment)
                .commit();


    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, GirlActivity.class);
        context.startActivity(intent);
    }
}
