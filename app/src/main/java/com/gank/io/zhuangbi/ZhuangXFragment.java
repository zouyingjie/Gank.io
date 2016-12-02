package com.gank.io.zhuangbi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gank.io.R;
import com.gank.io.base.BaseActivity;
import com.gank.io.model.ZhuangXImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class ZhuangXFragment extends Fragment implements ZhuangXContract.View{
    private ZhuangXContract.Presenter presenter;

    @BindView(R.id.recycler_zhuangx)
    RecyclerView recyclerZhuagnx;
    @BindView(R.id.swipe_zhuangx)
    SwipeRefreshLayout swipeZhuangx;

    private ArrayList<ZhuangXImage> zhuangImages = new ArrayList<ZhuangXImage>();
    private ZhuangXAdapter adapter;

    public ZhuangXFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_zhuang_x, container, false);
        ButterKnife.bind(this, root);
        initRootView();
        return root;
    }

    private void initRootView(){
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),2);
        recyclerZhuagnx.setLayoutManager(manager);
        adapter = new ZhuangXAdapter(getContext(), zhuangImages);
        recyclerZhuagnx.setAdapter(adapter);
//        swipeZhuangx.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
//        swipeZhuangx.setEnabled(false);
//        swipeZhuangx.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadImage();
    }

    @Override
    public void startLoad() {

    }

    @Override
    public void endLoad() {

    }

    @Override
    public void refreshImages(List<ZhuangXImage> images) {
        adapter.refresh(images);
    }

    @Override
    public void setPresenter(ZhuangXContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void removePresenter() {
        presenter = null;
    }

    @Override
    public void showToastTip(String tip) {
        ((ZhuangXActivity)getActivity()).showToastTip(tip);
    }
}
