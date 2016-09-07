package com.gank.io.today;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gank.io.R;
import com.gank.io.girl.GirlActivity;
import com.gank.io.zhuangbi.ZhuangXActivity;

public class TodayGankActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private RecyclerView todayGank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        initViewAndPresenter();

    }

    private void initViewAndPresenter(){
        TodayGankFragment fragment = new TodayGankFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_today_gank_content, fragment)
                .commit();
        new TodayGankPresenter(fragment);
    }

    private void showShareDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_share_bottom_sheet, null);
        RecyclerView recyclerBottomSheet = (RecyclerView) view.findViewById(R.id.recycler_share);
        dialog.setContentView(view);
        dialog.setTitle("分享到");
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerBottomSheet.setLayoutManager(manager);
        recyclerBottomSheet.setAdapter(new ShareAdpater(this));
        dialog.show();

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

        if (id == R.id.nav_zhuang_x) {
            Intent intent = new Intent(this, ZhuangXActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_girl) {
            Intent intent = new Intent(this, GirlActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    class GankDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//            super.onDraw(c, parent, state);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.colorAccent));
            paint.setAntiAlias(true);
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager){
                for (int i = 0; i < state.getItemCount(); i ++){
                    View view = parent.getChildAt(i);
                    c.drawRect(parent.getLeft(),getTop(parent, i), parent.getRight(),getButtom(parent, i),
                           paint);
                }

            }
//            c.drawRect();
        }

        private int getTop(RecyclerView parent, int index){
            int top = 0;
            for(int i = 0; i < index; i ++){
                top = top + parent.getChildAt(i).getHeight();
            }
            if(index > 0){
                top += 4*(index-1);
            }
            return top;

        }

        private int getButtom(RecyclerView parent, int index){
            return getTop(parent, index) + 4;
        }
    }



}
