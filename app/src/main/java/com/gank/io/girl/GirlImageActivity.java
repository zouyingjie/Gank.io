package com.gank.io.girl;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gank.io.R;
import com.gank.io.util.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GirlImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_girl_detail)
    ImageView ivGirlDetail;
    @BindView(R.id.toolbar_girl_image)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl_detail);
        ButterKnife.bind(this);
        initToolBar();
        Intent intent = getIntent();
        String desc = intent.getStringExtra("GIRL_DESC");
        String url = intent.getStringExtra("GIRL_URL");
        ivGirlDetail.setContentDescription(desc);
        ImageUtils.loadImageWithString(this, url, ivGirlDetail);
        ivGirlDetail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(GirlImageActivity.this, "123", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    private void initToolBar() {
        toolbar.setTitle("Girl");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
