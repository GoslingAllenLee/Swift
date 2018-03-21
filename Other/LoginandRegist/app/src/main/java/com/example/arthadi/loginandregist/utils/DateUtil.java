package com.example.arthadi.loginandregist.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by blastocode on 6/10/17.
 */

public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private static SimpleDateFormat sdfMonth = new SimpleDateFormat("MM-yyyy");
    private static SimpleDateFormat mysqlSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat mysql2SDF = new SimpleDateFormat("yyyy-MM-dd");
    //display day name from a date
    private static SimpleDateFormat sdfDay = new SimpleDateFormat("EEEE");

    public static String format(Date date) {
        return sdf.format(date);
    }

    public static String formatMySql(Date date) {
        return mysqlSDF.format(date);
    }

    public static String formatMonth (Date date) {
        return sdfMonth.format(date);
    }

    public static String formatDay(Date date) {
        return sdfDay.format(date);
    }

    public static Date toDate (String date) throws ParseException{
        return mysql2SDF.parse(date);
    }
}
