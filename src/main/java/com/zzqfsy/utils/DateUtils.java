package com.zzqfsy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chenghao on 4/5/16.
 */
public class DateUtils {
    private static final String FORMAT_DATETIME_LONG = "yyyyMMddHHmmss";
    private static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_DATE = "yyyy-MM-dd";
    private static final String FORMAT_TIME = "HH:mm:ss";
    private static final String FORMAT_TIME_HOUR_MIN = "HH:mm";
    private static final String FORMAT_TIME_HHMMSS = "HHmmss";
    private static final String FORMAT_DATETIME_YYYYMMDD = "yyyyMMdd";
    private static final String FORMAT_DATETIME_4MIN = "yyyyMMddHHmm";
    private static final String FORMAT_DATETIME_YYMMDD="yyMMdd";

    public static final SimpleDateFormat sdf=new SimpleDateFormat();
    public static String formatDate(Date date,String format){
        sdf.applyPattern(format);
        return sdf.format(date);
    }

    public static String formatDate(Date date){
        return formatDate(date, FORMAT_DATE);
    }

    public static String formatDateTime(Date date){
        return formatDate(date, FORMAT_DATETIME);
    }

    public static Date parseDate(String strDate) {
        return parse(strDate, FORMAT_DATE);
    }

    public static Date parseDateTime(String strDate) {
        return parse(strDate, FORMAT_DATETIME);
    }

    public static Date parse(String strDate, String strFormat) {

        if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }

        try {
            return new SimpleDateFormat(strFormat).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static Date beforeXDay(Date date, Integer X) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, X);

        // 设置时间为0时
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
