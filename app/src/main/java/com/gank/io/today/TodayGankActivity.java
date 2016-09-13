package com.gank.io.today;

import android.Manifest;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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

import com.bumptech.glide.Glide;
import com.gank.io.R;
import com.gank.io.TestFragment;
import com.gank.io.girl.GirlActivity;
import com.gank.io.model.gank.GankDayContentItem;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.model.gank.GankResourceType;
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
        String[] mPermissionList = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS};
        ActivityCompat.requestPermissions(TodayGankActivity.this, mPermissionList, 100);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Gank.io");

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
                intent.putExtra("GANK_URL", item.url);
                startActivity(intent);
            }
        });
        this.presenter = TodayGankPresenter.getInstance(this);
        presenter.loadTodayGankData();

    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadTodayGankData();
    }

    private void initTestFragment() {
        TestFragment fragment = new TestFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_today_gank_content, fragment)
                .commit();

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
//            Intent intent = new Intent(this, ZhuangXActivity.class);
            intent.setClass(this, ZhuangXActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_girl) {
//            Intent intent = new Intent(this, GirlActivity.class);
            intent.setClass(this, GirlActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_android){
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.ANDROID);
            startActivity(intent);
//            ApiService.getGankCategoryApi().getCategory("Android", "10")
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<GankCategory>() {
//                        @Override
//                        public void onCompleted() {
//                            Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onNext(GankCategory gankCategory) {
//                            Toast.makeText(getApplicationContext(), gankCategory.error+"eee", Toast.LENGTH_SHORT).show();
//                        }
//                    });
        }else if (id == R.id.nav_ios){
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.IOS);
            startActivity(intent);
        }
        else if (id == R.id.nav_html){
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.FRONTEND);
            startActivity(intent);
        }
        else if (id == R.id.nav_video){
            intent.setClass(this, GankCategoryActivity.class);
            intent.putExtra("TITLE", GankResourceType.VIDEO);
            startActivity(intent);
        }
        else if (id == R.id.nav_extral){
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
        adapter.setData(gankDayItems);
    }

    @Override
    public void setTodayGirl(String url) {
        Glide.with(this)
                .load(url)
                .crossFade()
                .into(ivTodayGril);
    }


    @Override
    public void setPresenter(TodayContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void removePresenter() {
        this.presenter = null;
    }


    class GankDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDraw(c, parent, state);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.colorAccent));
            paint.setAntiAlias(true);
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                for (int i = 0; i < state.getItemCount(); i++) {
                    View view = parent.getChildAt(i);
                    c.drawRect(parent.getLeft(), getTop(parent, i), parent.getRight(), getButtom(parent, i),
                            paint);
                }

            }
//            c.drawRect();
        }

        private int getTop(RecyclerView parent, int index) {
            int top = 0;
            for (int i = 0; i < index; i++) {
                top = top + parent.getChildAt(i).getHeight();
            }
            if (index > 0) {
                top += 4 * (index - 1);
            }
            return top;

        }

        private int getButtom(RecyclerView parent, int index) {
            return getTop(parent, index) + 4;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
