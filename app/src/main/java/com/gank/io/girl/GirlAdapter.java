package com.gank.io.girl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gank.io.R;
import com.gank.io.model.girl.GankGirlItem;
import com.gank.io.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouyingjie on 16/8/27.
 */

public class GirlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<GankGirlItem> girls;

    public GirlAdapter() {}

    public GirlAdapter(Context context, ArrayList<GankGirlItem> girls) {
        this.context = context;
        this.girls = girls;
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(@NonNull OnItemClickListener listener){
        this.listener = listener;
    }

    public void setImages(List<GankGirlItem> girls) {
        this.girls.addAll(girls);
        notifyItemRangeChanged(this.girls.size(), girls.size());
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.girl_item, parent, false);
        return  new GirlHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GankGirlItem girlItem = girls.get(position);
        ImageUtils.loadImageWithString(context, girlItem.imageUrl, ((GirlHolder) holder).ivGirl);
        ((GirlHolder) holder).tvDescription.setText(girlItem.description);
        ((GirlHolder) holder).ivGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(v, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return girls.size();
    }


    static class GirlHolder extends RecyclerView.ViewHolder {
        ImageView ivGirl;
        TextView tvDescription;

        public GirlHolder(View itemView) {
            super(itemView);
            ivGirl = (ImageView) itemView.findViewById(R.id.iv_girl);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_girl_description);
        }
    }


    interface OnItemClickListener{
        void onItemClickListener(View v, int position);
    }

}


