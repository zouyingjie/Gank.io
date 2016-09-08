package com.gank.io.today;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gank.io.R;
import com.gank.io.model.gank.GankDayContentItem;
import com.gank.io.model.gank.GankDayItem;
import com.gank.io.model.gank.GankDayTitleItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zouyingjie on 16/9/6.
 */


public class TodayGankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_CONTENT = 1;
    private static final int TYPE_TITLE = 2;

    private List<GankDayItem> items;
    private OnItemClickListener listener;

    public TodayGankAdapter() {
        items = new ArrayList<>(10);
    }

    public interface OnItemClickListener{
        void onItemClick(GankDayContentItem item);
    }

    public void setData(List<GankDayItem> data) {
        this.items = data;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CONTENT:
                return new ContentHolder(parent);

            case TYPE_TITLE:
                return new TitleHolder(parent);

            default:
                break;
        }
        return new ContentHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GankDayItem gankDayItem = items.get(position);
        if (holder instanceof ContentHolder) {
            ((ContentHolder) holder).tvContentTitle.setText(((GankDayContentItem) gankDayItem).desc);
            ((ContentHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick((GankDayContentItem) gankDayItem);
                }
            });
        }

        if (holder instanceof TitleHolder) {
            ((TitleHolder) holder).tvGankTitle.setText(((GankDayTitleItem) gankDayItem).title);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        GankDayItem dayItem = items.get(position);
        if (dayItem instanceof GankDayContentItem) {
            return TYPE_CONTENT;
        } else if (dayItem instanceof GankDayTitleItem) {
            return TYPE_TITLE;
        }
        return TYPE_CONTENT;
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_gank_content_title)
        TextView tvContentTitle;

        public ContentHolder(View parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_today_gank_content, (ViewGroup) parent, false));
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_gank_title)
        TextView tvGankTitle;

        public TitleHolder(View parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_today_gank_title, (ViewGroup) parent, false));
            ButterKnife.bind(this, itemView);
        }
    }

}

