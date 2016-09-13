package com.gank.io.today;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gank.io.R;
import com.gank.io.model.gank.GankCategory;
import com.gank.io.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GankCategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private List<GankCategory.Result> datas = new ArrayList<>();
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_category);
        toolbar = (Toolbar) findViewById(R.id.toolbar_gank_category);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("TITLE"));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_gank_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryAdapter(this, datas);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        ApiService.getGankCategoryApi().getCategory(getIntent().getStringExtra("TITLE"), "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankCategory>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GankCategory gankCategory) {
                        Toast.makeText(getApplicationContext(), gankCategory.error + "eee", Toast.LENGTH_SHORT).show();
                        adapter.setData(gankCategory.results);
                    }
                });
    }

    class CategoryAdapter extends RecyclerView.Adapter {
        private Context context;
        private List<GankCategory.Result> results;

        public void setData(List<GankCategory.Result> results){
            this.results = results;
            notifyDataSetChanged();
        }

        public CategoryAdapter(Context context, List<GankCategory.Result> results){
            this.context = context;
            this.results = results;
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_category_item, parent, false);
            VH holder = new VH(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((VH) holder).textView.setText(results.get(position).desc);
        }

        @Override
        public int getItemCount() {
            return results.size();
        }


    }

    class VH extends RecyclerView.ViewHolder {

        TextView textView;

        public VH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_category_title);
        }
    }
}
