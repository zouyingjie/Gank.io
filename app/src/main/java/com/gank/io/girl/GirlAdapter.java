package com.gank.io.girl;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gank.io.R;
import com.gank.io.model.GankGirlItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyingjie on 16/8/27.
 */

public class GirlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<GankGirlItem> girls;

    public GirlAdapter(){}
    public GirlAdapter(Context context, ArrayList<GankGirlItem> girls){
        this.context = context;
        this.girls = girls;
    }

    public void setImages(List<GankGirlItem> girls){
        int notiPosition = this.girls.size();
         this.girls.addAll(girls);
        notifyItemRangeChanged(notiPosition, girls.size());
    }
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.girl_item, parent, false);
        GirlHolder holder = new GirlHolder(cardView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GankGirlItem girlItem = girls.get(position);
        Uri uri = Uri.parse(girlItem.imageUrl);
        ((GirlHolder)holder).ivGirl.setImageURI(uri);
        ((GirlHolder)holder).tvDescription.setText(girlItem.description);
    }


    @Override
    public int getItemCount() {
        return girls.size();
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

