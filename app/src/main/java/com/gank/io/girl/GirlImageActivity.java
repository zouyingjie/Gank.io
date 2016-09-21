package com.gank.io.girl;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.gank.io.R;
import com.gank.io.util.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GirlImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_girl_detail)ImageView ivGirlDetail;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String desc = intent.getStringExtra("GIRL_DESC");
        String url = intent.getStringExtra("GIRL_URL");
        ImageUtils.loadImageWithString(this, url, ivGirlDetail);
        ivGirlDetail.setContentDescription(desc);


    }
}
