package com.gank.io.girl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gank.io.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GirlImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_girl_detail)
    ImageView ivGirlDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT > 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        Intent intent = getIntent();
        String desc = intent.getStringExtra("GIRL_DESC");
        String url = intent.getStringExtra("GIRL_URL");
//        ImageUtils.loadImageWithString(this, url, ivGirlDetail);
        ivGirlDetail.setContentDescription(desc);
        Glide.with(this)
                .load(url)
                .fitCenter()
                .into(ivGirlDetail);



    }
}
