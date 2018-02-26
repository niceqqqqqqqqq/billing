package com.smallserver.pfmp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WQ on 2017/12/29.
 */
public class BaseUitls {

    private final static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
    private final static SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd ");

    public static Date parseDate(String date){
        try {
            return  format1.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatTimeToStr1(Timestamp timestamp){
        return  format1.format(timestamp);
    }

    public static String formatTimeToStr2(Timestamp timestamp){
        return  format2.format(timestamp);
    }

    public static String formatDateToStr1(Date date){
        return  format1.format(date);
    }

    public static String formatDateToStr2(Date date){
        return  format2.format(date);
    }
}
