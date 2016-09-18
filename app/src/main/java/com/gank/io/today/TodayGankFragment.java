package com.gank.io.today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.io.R;
import com.gank.io.model.GankDayContentItem;
import com.gank.io.model.GankDayItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zouyingjie on 16/9/6.
 */

public class TodayGankFragment  extends Fragment implements TodayContract.View {

    @BindView(R.id.recycler_today_gank)
    RecyclerView recyclerToadyGank;
    private TodayContract.Presenter presenter;
    private TodayGankAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_gank, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new TodayGankAdapter();
        recyclerToadyGank.setAdapter(adapter);
        recyclerToadyGank.setLayoutManager(linearLayoutManager);
        adapter.setOnItemClickListener(new TodayGankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GankDayContentItem item) {
                Intent intent = new Intent(getActivity(), GankDetailActivity.class);
                intent.putExtra("GANK_URL",item.url);
                startActivity(intent);
            }
        });
        presenter.loadTodayGankData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadTodayGankData();
    }

    @Override
    public void setPresenter(TodayContract.Presenter presenter) {
        this.presenter = (TodayGankPresenter) presenter;
    }

    @Override
    public void removePresenter() {
        this.presenter = null;
    }

    @Override
    public void loadTodayGankData(List<GankDayItem> gankDayItems) {
        adapter.setData(gankDayItems);
    }

    @Override
    public void setTodayGirl(String url) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
