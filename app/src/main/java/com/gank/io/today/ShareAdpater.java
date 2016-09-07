package com.gank.io.today;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gank.io.R;

import butterknife.BindView;
import butterknife.ButterKnife;


class ShareAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String[] titles = {"微信朋友", "朋友圈", "QQ好友", "QQ空间", "新浪微博", "Twitter", "FaceBook"};
    private Context context;

    ShareAdpater(Context context) {
        this.context = context;
    }

    @Override
    public ShareHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_share_bottom_sheet_item, parent, false);
        return new ShareHolder(view);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ShareHolder)holder).tvShareTitle.setText(titles[position]);
    }

     static class ShareHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_share_title)
        TextView tvShareTitle;

        ShareHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


