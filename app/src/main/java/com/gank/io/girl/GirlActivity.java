package com.gank.io.girl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gank.io.R;

public class GirlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);
        GirlFragment fragment = new GirlFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_content, fragment)
                .commit();
    }
}
