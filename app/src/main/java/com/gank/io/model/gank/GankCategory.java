package com.gank.io.model.gank;

import java.util.List;

/**
 * Created by zouyingjie on 16/9/13.
 */

public class GankCategory {
    public String count;
    public String error;
    public List<Result> results;

    public static class Result {
        public String desc;
        public String ganhuo_id;
        public String publishedAt;
        public String readability;
        public String type;
        public String url;
        public String who;
    }
}
