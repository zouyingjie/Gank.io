package com.gank.io.girl;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.gank.io.network.api.Api;
import com.gank.io.util.GankBeautyResultToItemsMapper;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class GirlFragment extends Fragment implements GirlContract.View, View.OnClickListener {
    protected Subscription subscription;

    private GirlPresenter presenter;
    private RecyclerView recyclerGirl;
    private GirlAdapter adapter;
    private ArrayList<GankGirlItem> girls = new ArrayList<GankGirlItem>();
    private FloatingActionButton fab;

    public GirlFragment() {
        // Required empty public constructor
    }

    Observer<List<GankGirlItem>> observer = new Observer<List<GankGirlItem>>() {
        @Override
        public void onCompleted() {
            Toast.makeText(getActivity(), "Completed", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(List<GankGirlItem> images) {
            adapter.setImages(images);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_blank, container, false);

        recyclerGirl = (RecyclerView) root.findViewById(R.id.recycler_girl);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerGirl.setLayoutManager(layoutManager);
        adapter = new GirlAdapter(getContext(), girls);
        recyclerGirl.setAdapter(adapter);


        return root;
    }

    @Override
    public void refreshView() {

    }

    @Override
    public void setPresenter(GirlContract.Presenter presenter) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    @Override
    public void onClick(View v) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = Api.getGankApi()
                .getBeauties(10, 2)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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
