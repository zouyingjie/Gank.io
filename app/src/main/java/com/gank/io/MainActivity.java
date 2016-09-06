package com.gank.io;

import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gank.io.girl.GirlActivity;
import com.gank.io.model.gank.GankDayContentItem;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.model.gank.GankDayTitleItem;
import com.gank.io.network.api.ApiService;
import com.gank.io.util.GankDayDataToGankItemMapper;
import com.gank.io.zhuangbi.ZhuangXActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_TITLE = 2;


    private RecyclerView recyclerMain;
    private RecyclerMainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerMain = (RecyclerView) findViewById(R.id.recycler_main);
        adapter = new RecyclerMainAdapter();
        recyclerMain.setAdapter(adapter);
        recyclerMain.setLayoutManager(linearLayoutManager);

//        recyclerMain.addItemDecoration(new GankDecoration());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        loadTodayGank();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShareDialog();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private void showShareDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.layout_share_bottom_sheet, null);
        RecyclerView recyclerBottomSheet = (RecyclerView) view.findViewById(R.id.recycler_share);
        dialog.setContentView(view);
        dialog.setTitle("分享到");

//        dialog.
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerBottomSheet.setLayoutManager(manager);
        recyclerBottomSheet.setAdapter(new ShareAdpater(this));
        dialog.show();

    }

    private void loadTodayGank() {
        final Observer<List<GankDayItem>> observer = new Observer<List<GankDayItem>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), "Err:" + e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<GankDayItem> gankDayItems) {
                adapter.setData(gankDayItems);
            }
        };

        ApiService.getGankDayApi()
                .getDayData("2016/09/05")
                .map(GankDayDataToGankItemMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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

    class RecyclerMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<GankDayItem> items;

        public RecyclerMainAdapter() {
            items = new ArrayList<>(10);
        }


        public void setData(List<GankDayItem> data) {
            this.items = data;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_CONTENT:
                    return new ContentHolder(parent);

                case TYPE_TITLE:
                    return new TitleHolder(parent);

                default:
                    break;
            }
            return new ContentHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            GankDayItem gankDayItem = items.get(position);
            if (holder instanceof ContentHolder) {
                ((ContentHolder) holder).tvContentTitle.setText(((GankDayContentItem) gankDayItem).desc);
            }

            if (holder instanceof TitleHolder) {
                ((TitleHolder) holder).tvGankTitle.setText(((GankDayTitleItem) gankDayItem).title);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            GankDayItem dayItem = items.get(position);
            if (dayItem instanceof GankDayContentItem) {
                return TYPE_CONTENT;
            } else if (dayItem instanceof GankDayTitleItem) {
                return TYPE_TITLE;
            }
            return TYPE_CONTENT;
        }
    }

    class MainHolder extends RecyclerView.ViewHolder {

        public MainHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_gank_content_title)
        TextView tvContentTitle;

        public ContentHolder(View parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_today_gank_content, (ViewGroup) parent, false));
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_gank_title)
        TextView tvGankTitle;

        public TitleHolder(View parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_today_gank_title, (ViewGroup) parent, false));
            ButterKnife.bind(this, itemView);
        }
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


    class ShareAdpater extends RecyclerView.Adapter<ShareHolder>{
        private String[] titles = {"发送朋友","朋友圈","QQ好友", "空间", "微博", "Twitter", "FaceBook"};
        private ArrayList<String> datas = new ArrayList<>(10);
        private Context context;
        public ShareAdpater(Context context){
            this.context = context;
        }
        @Override
        public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_share_bottom_sheet_item, parent, false);
            ShareHolder holder = new ShareHolder(view);
            return holder;
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        @Override
        public void onBindViewHolder(ShareHolder holder, int position) {

        }


    }

    static class ShareHolder extends RecyclerView.ViewHolder{

        public ShareHolder(View itemView) {
            super(itemView);
        }
    }
}
