package com.gank.io.model.gank;

import java.util.List;

/**
 * Created by zouyingjie on 16/9/13.
 */

public class GankCategory {
    public String count;
    public String error;
    public List<Result> results;

    static class Result{
        private String desc;
        private String ganhuo_id;
        private String publishedAt;
        private String readability;
        private String type;
        private String url;
        private String who;
    }
}
