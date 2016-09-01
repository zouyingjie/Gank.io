package com.gank.io.zhuangbi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gank.io.R;
import com.gank.io.model.ZhuangXImage;

import java.util.List;

/**
 * Created by zouyingjie on 16/8/28.
 */

public class ZhuangXAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ZhuangXImage> list;

    public ZhuangXAdapter(Context context, List<ZhuangXImage> zhuangXImages){
        this.context = context;
        this.list = zhuangXImages;
    }

    public void refresh(List<ZhuangXImage> zhuangXImages){
        int notiPosition = this.list.size();
        this.list.addAll(zhuangXImages);
        notifyItemRangeChanged(notiPosition, zhuangXImages.size());
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.girl_item, parent, false);
        VH holder = new VH(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ZhuangXImage image = list.get(position);
        ((VH)holder).tvDescription.setText(image.description);
        String uri = image.image_url.substring(0, image.image_url.length() - 1);
        if (image.image_url.endsWith("gif")){
            Glide.with(context)
                    .load(image.image_url)
                    .asGif()
                    .centerCrop()
                    .crossFade()
                    .into(((VH)holder).ivGirl);
        }else {
            Glide.with(context)
                    .load(image.image_url)
                    .centerCrop()
                    .crossFade()
                    .into(((VH)holder).ivGirl);
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivGirl;
        TextView tvDescription;
        public VH(View itemView) {
            super(itemView);
            ivGirl = (ImageView) itemView.findViewById(R.id.iv_girl);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_girl_description);
        }
    }

}


