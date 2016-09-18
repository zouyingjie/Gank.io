package com.gank.io.model.girl;

import com.gank.io.model.gank.Gank;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zouyingjie on 16/8/26.
 */

public class GankGirlResult {
    public boolean error;
    public @SerializedName("results")
    List<Gank> beauties;
}
