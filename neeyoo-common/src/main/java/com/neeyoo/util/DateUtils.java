package com.neeyoo.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description: 时间处理工具类
 */
public class DateUtils {

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 获得当天是周几
     */
    public static String getWeekDay() {
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }
}
