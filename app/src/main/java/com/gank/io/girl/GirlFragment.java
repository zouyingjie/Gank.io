package com.gank.io.girl;


import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadImage();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        adapter.setOnItemClickListener(new GirlAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                GankGirlItem item = girls.get(position);
                Intent intent = new Intent(getActivity(), GirlImageActivity.class);
                intent.putExtra("GIRL_DESC", item.description);
                intent.putExtra("GIRL_URL", item.imageUrl);
                if (Build.VERSION.SDK_INT > 21){
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(getActivity(), v, "robot").toBundle());
                }else {
                    startActivity(intent);
                }

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
