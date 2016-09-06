package com.gank.io.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zouyingjie on 16/9/1.
 */

public class ImageUtils {
    public static void loadImage(Context context, String uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    public static void loadImageWithPlaceholder(Context context, String uri, ImageView imageView, Drawable drawable) {
        Glide.with(context)
                .load(uri)
                .centerCrop()
                .crossFade()
                .placeholder(drawable)
                .into(imageView);
    }

    public static void loadGifWithPlaceholder(Context context, String uri, ImageView imageView, Drawable drawable) {
        Glide.with(context)
                .load(uri)
                .asGif()
                .centerCrop()
                .crossFade()
                .placeholder(drawable)
                .into(imageView);
    }

    public static void loadGif(Context context, String uri, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .asGif()
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    public static boolean isGif(String uri){
        if (uri.contains(".gif")){
            return true;
        }else {
            return false;
        }
    }
}
