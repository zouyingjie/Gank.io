package com.gank.io.girl;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gank.io.R;

public class GirlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_girl);
        toolbar.setTitle(getResources().getString(R.string.girl_lable));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GirlFragment fragment = new GirlFragment();
        new GirlPresenter(
                fragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_girl_content, fragment)
                .commit();


    }
}
