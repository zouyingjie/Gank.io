package com.gank.io.gankdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gank.io.R;
import com.gank.io.adapter.CategoryAdapter;
import com.gank.io.model.gank.GankCategory;
import com.gank.io.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GankCategoryActivity extends AppCompatActivity {
    private static final String PAGE_NUMBER = "15";
    private int page = 1;
    @BindView(R.id.recycler_gank_category)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_gank_category)
    Toolbar toolbar;

    @BindView(R.id.fab_category_next_page)
    FloatingActionButton fabNextPage;

    private List<GankCategory.Result> datas = new ArrayList<>();
    private CategoryAdapter adapter;

    Observer<GankCategory> observer = new Observer<GankCategory>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(GankCategory gankCategory) {
            adapter.setData(gankCategory.results);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_category);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_gank_category);
        toolbar.setTitle(getIntent().getStringExtra("TITLE"));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String url) {
                Intent intent = new Intent(GankCategoryActivity.this, GankDetailActivity.class);
                intent.putExtra("GANK_URL", url);
                startActivity(intent);
            }
        });

        fabNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page ++;
                loadData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {


        ApiService.getGankApi().getDataByCategory(getIntent().getStringExtra("TITLE"), PAGE_NUMBER, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}


