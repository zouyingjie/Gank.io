package com.gank.io.girl;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.gank.io.R;
import com.gank.io.model.girl.GankGirlItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GirlFragment extends Fragment implements GirlContract.View, SwipeRefreshLayout.OnRefreshListener {

    private GirlPresenter presenter;

    @BindView(R.id.recycler_girl)
    RecyclerView recyclerGirl;
    @BindView(R.id.swipe_girl)
    SwipeRefreshLayout swipeGirl;
    @BindView(R.id.bar_gril)
    ProgressBar barGirl;

    @Inject
    GirlAdapter adapter;

    private ArrayList<GankGirlItem> girls = new ArrayList<GankGirlItem>();


    public GirlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        ButterKnife.bind(this, root);
        initRootView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadImage();
    }

    @SuppressLint("NewApi")
    private void initRootView() {
        swipeGirl.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeGirl.setEnabled(false);

        adapter = new GirlAdapter(getContext(), girls);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerGirl.setLayoutManager(layoutManager);
        recyclerGirl.setAdapter(adapter);
        recyclerGirl.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.findLastVisibleItemPosition() + 1 == adapter.getItemCount()) {
                    presenter.loadImage();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    @Override
    public void startPullRefresh() {
        swipeGirl.setEnabled(true);
        swipeGirl.setRefreshing(true);
    }

    @Override
    public void endPullRefresh() {
        swipeGirl.setRefreshing(false);
    }


    @Override
    public void refreshImages(List<GankGirlItem> images) {
        adapter.setImages(images);
    }


    @Override
    public void setPresenter(GirlContract.Presenter presenter) {
        this.presenter = (GirlPresenter) presenter;
    }

    @Override
    public void removePresenter() {
        presenter = null;
    }

    @Override
    public void onRefresh() {
        presenter.loadImage();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
        removePresenter();
    }
}
