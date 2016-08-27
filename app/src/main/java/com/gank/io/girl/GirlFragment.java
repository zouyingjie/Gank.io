package com.gank.io.girl;


import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gank.io.R;
import com.gank.io.model.GankGirlItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class GirlFragment extends Fragment implements GirlContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    protected Subscription subscription;

    private GirlPresenter presenter;
    private RecyclerView recyclerGirl;
    private GirlAdapter adapter;
    private ArrayList<GankGirlItem> girls = new ArrayList<GankGirlItem>();
    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeGirl;
    private int page = 0;

    public GirlFragment() {
        // Required empty public constructor
    }
    public Observer<List<GankGirlItem>> getObserver() {
        return observer;
    }

    Observer<List<GankGirlItem>> observer = new Observer<List<GankGirlItem>>() {
        @Override
        public void onCompleted() {
            Toast.makeText(getActivity(), "Completed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(Throwable e) {
           endRefresh();
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<GankGirlItem> images) {
            endRefresh();
            adapter.setImages(images);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_blank, container, false);

        recyclerGirl = (RecyclerView) root.findViewById(R.id.recycler_girl);
        swipeGirl = (SwipeRefreshLayout) root.findViewById(R.id.swipe_girl);
        swipeGirl.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeGirl.setEnabled(false);
        swipeGirl.setOnRefreshListener(this);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerGirl.setLayoutManager(layoutManager);
        adapter = new GirlAdapter(getContext(), girls);
        recyclerGirl.setAdapter(adapter);


        return root;
    }



    @Override
    public void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void startRefresh() {
        swipeGirl.setEnabled(true);
        swipeGirl.setRefreshing(true);
    }

    @Override
    public void endRefresh() {
        swipeGirl.setRefreshing(false);
    }

    @Override
    public Observer observer() {
        return observer;
    }

    @Override
    public Subscription subscription() {
        return subscription;
    }

    @Override
    public void setSubscription() {

    }


    @Override
    public void setPresenter(GirlContract.Presenter presenter) {
        this.presenter = (GirlPresenter) presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    @Override
    public void onClick(View v) {
       presenter.loadImage();
    }

//    private void loadImage(){
//        unsubscribe();
//
//        subscription = Api.getGankApi()
//                .getBeauties(10, page++)
//                .map(GankBeautyResultToItemsMapper.getInstance())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }

    @Override
    public void onRefresh() {
        presenter.loadImage();

    }

    class GirlAdapter extends RecyclerView.Adapter<GirlHolder>{

        private Context context;
        private ArrayList<GankGirlItem> girls;
        public GirlAdapter(Context context, ArrayList<GankGirlItem> girls){
            this.context = context;
            this.girls = girls;
        }

        public void setImages(List<GankGirlItem> girls){
            this.girls = (ArrayList<GankGirlItem>) girls;
            notifyDataSetChanged();
        }
        @Override
        public GirlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.girl_item, parent, false);
            GirlHolder holder = new GirlHolder(cardView);
            return holder;
        }

        @Override
        public void onBindViewHolder(GirlHolder holder, int position) {
            GankGirlItem girlItem = girls.get(position);
//            Glide.with(holder.itemView.getContext()).load(girlItem.imageUrl).into(holder.ivGirl);
            Uri uri = Uri.parse(girlItem.imageUrl);
//            holder.ivGirl.setImageURI(girlItem.imageUrl);
            holder.ivGirl.setImageURI(uri);
            holder.tvDescription.setText(girlItem.description);
        }

        @Override
        public int getItemCount() {
            return girls.size();
        }


    }

    static class GirlHolder extends RecyclerView.ViewHolder{
       SimpleDraweeView ivGirl;
        TextView tvDescription;
        public GirlHolder(View itemView) {
            super(itemView);
            ivGirl = (SimpleDraweeView) itemView.findViewById(R.id.iv_girl);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_girl_description);
        }
    }
}
