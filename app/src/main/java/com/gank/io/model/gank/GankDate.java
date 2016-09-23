package com.gank.io.model.gank;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zouyingjie on 16/9/7.
 */

public class GankDate {
    public String error;
    public List<String> results;

    public Calendar getLastDate() {
        String[] date = this.results.get(0).split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.valueOf(date[0]));
        calendar.set(Calendar.MONTH, Integer.valueOf(date[1]));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date[2]));
        return calendar;
    }
}
