package com.gank.io.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.gank.io.R;
import com.gank.io.constant.Contants;
import com.gank.io.constant.GankResourceType;
import com.gank.io.gankdetail.GankCategoryActivity;
import com.gank.io.gankdetail.GankDetailActivity;
import com.gank.io.girl.GirlActivity;
import com.gank.io.model.gank.GankDayContentItem;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.util.ImageUtils;
import com.gank.io.zhuangbi.ZhuangXActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayGankActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TodayContract.View {
    @BindView(R.id.recycler_today_gank)
    RecyclerView recyclerToadyGank;
    @BindView(R.id.iv_today_girl)
    ImageView ivTodayGril;
    @BindView(R.id.toolbar_gan_today)
    Toolbar toolbar;

    private TodayContract.Presenter presenter;
    private TodayGankAdapter adapter;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
//        ActivityCompat.requestPermissions(TodayGankActivity.this, mPermissionList, 100);
        initView();
        this.presenter = TodayGankPresenter.getInstance(this);


    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_gan_today);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
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


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadTodayGankData();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_zhuang_x:
                startActivityWithTitle(ZhuangXActivity.class, "");
                break;
            case R.id.nav_girl:
                startActivityWithTitle(GirlActivity.class, "");
                break;
            case R.id.nav_android:
                startActivityWithTitle(GankCategoryActivity.class, GankResourceType.ANDROID);
                break;
            case R.id.nav_ios:
                startActivityWithTitle(GankCategoryActivity.class, GankResourceType.IOS);
                break;
            case R.id.nav_frontend:
                startActivityWithTitle(GankCategoryActivity.class, GankResourceType.FRONTEND);
                break;
            case R.id.nav_video:
                startActivityWithTitle(GankCategoryActivity.class, GankResourceType.VIDEO);
                break;
            case R.id.nav_extral:
                startActivityWithTitle(GankCategoryActivity.class, GankResourceType.EXTRA);
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
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
    public void loadTodayGankData(List<GankDayItem> gankDayItems) {
        ImageUtils.loadImageWithString(this, ((GankDayContentItem) gankDayItems.remove(gankDayItems.size() - 1)).url, ivTodayGril);
        adapter.setData(gankDayItems);
    }

    @Override
    public void setTodayGirl(String url) {

    }

    @Override
    public void showErrorTip() {
        Toast.makeText(this, R.string.query_error_tip, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void setPresenter(TodayContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void removePresenter() {
        this.presenter = null;
    }


}
