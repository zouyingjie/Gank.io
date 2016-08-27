package com.gank.io.girl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gank.io.R;

public class GirlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_girl);
        setSupportActionBar(toolbar);
        GirlFragment fragment = new GirlFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, fragment)
                .commit();

        new GirlPresenter(
                fragment);
    }
}
