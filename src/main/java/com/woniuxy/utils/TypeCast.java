package com.woniuxy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TypeCast {
    //Data类型转String类型
    public static String toString(Date date) {

        String time;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyyMMdd");
        time = formater.format(date);
        return time;
    }
    //String类型转Data类型
    public static Date toDate(String dateStr) {
        Date date = null;
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyyMMdd");
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
