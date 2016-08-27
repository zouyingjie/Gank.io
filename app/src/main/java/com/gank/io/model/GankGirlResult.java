package com.gank.io.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GankGirlResult {
    public boolean error;
    public @SerializedName("results")
    List<GankGirl> beauties;
}
