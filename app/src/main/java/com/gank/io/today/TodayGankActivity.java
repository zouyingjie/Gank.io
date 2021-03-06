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
import android.view.MenuItem;
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
//        drawerGankToday = (DrawerLayout) findViewById(R.id.drawer_gank_today);
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
        adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(TodayGankActivity.this, GankDetailActivity.class);
            intent.putExtra(Contants.GANK_URL, item.url);
            startActivity(intent);
        });
        recyclerToadyGank.setAdapter(adapter);
        fabSelectGankDate.setOnClickListener(v -> showCalendar());
    }

    private void showCalendar() {
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            presenter.loadData(c);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_zhuang_x:
                ZhuangXActivity.actionStart(this);
                break;
            case R.id.nav_girl:
                GirlActivity.actionStart(this);
                break;
            case R.id.nav_android:
                GankCategoryActivity.actionStart(this, Contants.GANK_RESOUSE_TYPE_ANDROID);
                break;
            case R.id.nav_ios:
                GankCategoryActivity.actionStart(this, Contants.GANK_RESOUSE_TYPE_IOS);
                break;
            case R.id.nav_frontend:
                GankCategoryActivity.actionStart(this, Contants.GANK_RESOUSE_TYPE_FRONTEND);
                break;
            case R.id.nav_video:
                GankCategoryActivity.actionStart(this, Contants.GANK_RESOUSE_TYPE_VIDEO);
                break;
            case R.id.nav_extral:
                GankCategoryActivity.actionStart(this, Contants.GANK_RESOUSE_TYPE_EXTRA);
                break;

        }
        drawerGankToday.closeDrawer(GravityCompat.START);
        item.setCheckable(false);
        return true;
    }

    @Override
    public void loadTodayGankData(@NonNull List<GankDayItem> gankDayItems) {
        if (gankDayItems.size() > 0) {
            String imgUrl = ((GankDayContentItem) gankDayItems.remove(gankDayItems.size() - 1)).url;
            ImageUtils.loadImageWithString(this, imgUrl, ivTodayGril);
            adapter.setData(gankDayItems);
        } else {
            Toast.makeText(this, getResources().getString(R.string.access_data_fail_tip), Toast.LENGTH_SHORT).show();
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
        super.showToastTip(tip);
    }

}
