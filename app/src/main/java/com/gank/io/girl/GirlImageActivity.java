package com.gank.io.girl;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.gank.io.R;
import com.gank.io.base.BaseActivity;
import com.gank.io.util.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GirlImageActivity extends BaseActivity {

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
    }

    private void initToolBar() {
        toolbar.setTitle("Girl");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static void actionStart(Context context,  String girlDesc, String girlUrl){
        Intent intent = new Intent(context, GirlImageActivity.class);
        intent.putExtra("GIRL_DESC", girlDesc);
        intent.putExtra("GIRL_URL", girlUrl);
        context.startActivity(intent);
    }
}
