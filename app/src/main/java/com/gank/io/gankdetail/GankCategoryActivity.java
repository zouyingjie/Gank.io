package com.gank.io.gankdetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gank.io.R;
import com.gank.io.base.BaseActivity;
import com.gank.io.model.gank.GankCategory;
import com.gank.io.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.gank.io.R.id.swipe_gank_category;

public class GankCategoryActivity extends BaseActivity {

    private static final String PAGE_NUMBER = "15";

    private int page = 1;
    private List<GankCategory.Result> datas = new ArrayList<>();
    private CategoryAdapter adapter;

    @BindView(swipe_gank_category)
    SwipeRefreshLayout swipeGankCategory;
    @BindView(R.id.recycler_gank_category)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_gank_category)
    Toolbar toolbar;
    @BindView(R.id.fab_category_next_page)
    FloatingActionButton fabNextPage;

    private Observer<GankCategory> observer = new Observer<GankCategory>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            swipeGankCategory.setEnabled(false);
            swipeGankCategory.setRefreshing(false);
            showToastTip(getResources().getString(R.string.access_data_fail_tip));
        }

        @Override
        public void onNext(GankCategory gankCategory) {
            swipeGankCategory.setEnabled(false);
            swipeGankCategory.setRefreshing(false);
            adapter.setData(gankCategory.results);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_category);
        ButterKnife.bind(this);

        toolbar.setTitle(getIntent().getStringExtra("TITLE"));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        swipeGankCategory.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeGankCategory.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((v, url) -> {
            Intent intent = new Intent(GankCategoryActivity.this, GankDetailActivity.class);
            intent.putExtra("GANK_URL", url);
            startActivity(intent);
        });

        fabNextPage.setOnClickListener(v -> {
            swipeGankCategory.setRefreshing(true);
            page++;
            loadData();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        swipeGankCategory.setEnabled(true);
        swipeGankCategory.setRefreshing(true);
        ApiService.getGankApi().getDataByCategory(getIntent().getStringExtra("TITLE"), PAGE_NUMBER, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}


