package com.gank.io.today;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.gank.io.R;
import com.gank.io.base.BaseActivity;
import com.gank.io.constant.Contants;
import com.gank.io.gankdetail.GankCategoryActivity;
import com.gank.io.gankdetail.GankDetailActivity;
import com.gank.io.girl.GirlActivity;
import com.gank.io.model.gank.GankDayContentItem;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.util.ImageUtils;
import com.gank.io.zhuangbi.ZhuangXActivity;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayGankActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, TodayContract.View {


    private TodayContract.Presenter presenter;
    private TodayGankAdapter adapter;

    @BindView(R.id.appbar_gank_today)
    AppBarLayout appbarGankToday;
    @BindView(R.id.fab_select_gank_date)
    FloatingActionButton fabSelectGankDate;
    @BindView(R.id.recycler_today_gank)
    RecyclerView recyclerToadyGank;
    @BindView(R.id.iv_today_girl)
    ImageView ivTodayGril;
    @BindView(R.id.toolbar_gank_today)
    Toolbar toolbarGankToday;
    @BindView(R.id.drawer_gank_today)
    DrawerLayout drawerGankToday;
    @BindView(R.id.main_collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        this.presenter = TodayGankPresenter.getInstance(this);
    }

    private void initView() {
        setSupportActionBar(toolbarGankToday);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerGankToday, toolbarGankToday, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerGankToday.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerToadyGank.setLayoutManager(linearLayoutManager);
        adapter = new TodayGankAdapter();
        adapter.setOnItemClickListener(new TodayGankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GankDayContentItem item) {
                Intent intent = new Intent(TodayGankActivity.this, GankDetailActivity.class);
                intent.putExtra(Contants.GANK_URL, item.url);
                startActivity(intent);
            }
        });
        recyclerToadyGank.setAdapter(adapter);

        fabSelectGankDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });
    }

    private void showCalendar() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR,year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                presenter.loadData(c);

            }
        };
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadTodayGankData();
    }


    @Override
    public void onBackPressed() {
        if (drawerGankToday.isDrawerOpen(GravityCompat.START)) {
            drawerGankToday.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_zhuang_x:
                startActivityWithTitle(ZhuangXActivity.class, "");
                break;
            case R.id.nav_girl:
                startActivityWithTitle(GirlActivity.class, "");
                break;
            case R.id.nav_android:
                startActivityWithTitle(GankCategoryActivity.class, Contants.GANK_RESOUSE_TYPE_ANDROID);
                break;
            case R.id.nav_ios:
                startActivityWithTitle(GankCategoryActivity.class, Contants.GANK_RESOUSE_TYPE_IOS);
                break;
            case R.id.nav_frontend:
                startActivityWithTitle(GankCategoryActivity.class, Contants.GANK_RESOUSE_TYPE_FRONTEND);
                break;
            case R.id.nav_video:
                startActivityWithTitle(GankCategoryActivity.class, Contants.GANK_RESOUSE_TYPE_VIDEO);
                break;
            case R.id.nav_extral:
                startActivityWithTitle(GankCategoryActivity.class, Contants.GANK_RESOUSE_TYPE_EXTRA);
                break;

        }
        drawerGankToday.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startActivityWithTitle(Class activity, String title) {
        Intent intent = new Intent(this, activity);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra("TITLE", title);
        }
        startActivity(intent);
    }

    @Override
    public void loadTodayGankData(@NonNull List<GankDayItem> gankDayItems) {
        if (gankDayItems.size() > 0){
            String imgUrl = ((GankDayContentItem) gankDayItems.remove(gankDayItems.size() - 1)).url;
            ImageUtils.loadImageWithString(this, imgUrl, ivTodayGril);
            adapter.setData(gankDayItems);
        }else {
            Toast.makeText(this, "这一天没有数据哦",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void setPresenter(TodayContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void removePresenter() {
        this.presenter = null;
    }

    @Override
    public void showToastTip(String tip) {
//        showToastTip(tip);
        super.showToastTip(tip);
//        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
    }



}
