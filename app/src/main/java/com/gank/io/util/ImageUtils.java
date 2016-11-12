package com.gank.io.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by zouyingjie on 16/9/1.
 */

public class ImageUtils {
    public static void loadImageWithString(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .crossFade()
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public static void loadImageWithUri(Context context, Uri url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public static void loadImageWithPlaceholder(Context context, String url, ImageView imageView, Drawable drawable) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .placeholder(drawable)
                .into(imageView);
    }

    public static void loadGifWithPlaceholder(Context context, String url, ImageView imageView, Drawable drawable) {
        Glide.with(context)
                .load(url)
                .asGif()
                .centerCrop()
                .crossFade()
                .placeholder(drawable)
                .into(imageView);
    }

    public static void loadGif(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asGif()
                .centerCrop()
                .crossFade()
                .into(imageView);
    }

    public static boolean isGif(String url){
        return url.contains(".gif");
    }
}
