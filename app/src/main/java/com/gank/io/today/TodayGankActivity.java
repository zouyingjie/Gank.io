package com.gank.io.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.List;

public class TodayGankActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TodayContract.View {
    private RecyclerView recyclerToadyGank;
    private TodayContract.Presenter presenter;
    private TodayGankAdapter adapter;
    private ImageView ivTodayGril;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
//        ActivityCompat.requestPermissions(TodayGankActivity.this, mPermissionList, 100);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        recyclerToadyGank = (RecyclerView) findViewById(R.id.recycler_today_gank);
        ivTodayGril = (ImageView) findViewById(R.id.iv_today_girl);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShareDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new TodayGankAdapter();
        recyclerToadyGank.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerToadyGank.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(new TodayGankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GankDayContentItem item) {
                Intent intent = new Intent(TodayGankActivity.this, GankDetailActivity.class);
                intent.putExtra(Contants.GANK_URL, item.url);
                startActivity(intent);
            }
        });
        this.presenter = TodayGankPresenter.getInstance(this);

    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadTodayGankData();
    }


    private void showShareDialog() {
        UMImage image = new UMImage(TodayGankActivity.this, "http://www.umeng.com/images/pic/social/integrated_3.png");
        UMShareListener listener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(TodayGankActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                Toast.makeText(TodayGankActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(TodayGankActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
            }
        };

        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN
                };
        new ShareAction(this).setDisplayList(displaylist)
                .withText("呵呵")
                .withTitle("title")
                .withTargetUrl("http://www.baidu.com")
                .withMedia(image)
                .setListenerList(listener)
                .open();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = new Intent();
        ;
        if (id == R.id.nav_zhuang_x) {
            intent.setClass(this, ZhuangXActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_girl) {
            intent.setClass(this, GirlActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_android) {
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.ANDROID);
            startActivity(intent);
        } else if (id == R.id.nav_ios) {
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.IOS);
            startActivity(intent);
        } else if (id == R.id.nav_html) {
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.FRONTEND);
            startActivity(intent);
        } else if (id == R.id.nav_video) {
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.VIDEO);
            startActivity(intent);
        } else if (id == R.id.nav_extral) {
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.EXTRA);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadTodayGankData(List<GankDayItem> gankDayItems) {
        ImageUtils.loadImageWithString(this,  ((GankDayContentItem) gankDayItems.remove(gankDayItems.size() - 1)).url, ivTodayGril);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
