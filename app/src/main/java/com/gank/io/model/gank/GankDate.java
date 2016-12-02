package com.gank.io.model.gank;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zouyingjie on 16/9/7.
 */

public class GankDate {
    public String error;
    private List<String> results;

    public Calendar getLastDate() {
            String[] dateArr = this.results.get(0).split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(dateArr[0]));
            calendar.set(Calendar.MONTH, Integer.valueOf(dateArr[1]));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dateArr[2]));
            return calendar;
    }
}
