package com.gank.io.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gank.io.R;
import com.gank.io.model.gank.GankCategory;

import java.util.List;

/**
 * Created by zouyingjie on 16/10/28.
 */

public class CategoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GankCategory.Result> results;
    private OnItemClickListener listener;

    public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
        this.listener = listener;
    }


    public void setData(List<GankCategory.Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    public CategoryAdapter(Context context, List<GankCategory.Result> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gank_content_title_item, parent, false);
        VH holder = new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((VH) holder).textView.setText(results.get(position).desc);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, results.get(position).url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView textView;

        public VH(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_gank_content_title);
        }


    }


    public interface OnItemClickListener {
        void onItemClick(View v, String url);
    }
}
