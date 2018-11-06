package com.example.shadhin.helloworldonlyjava.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by enamul on 5/9/18.
 */

public class MyDateFormat {

    public static String getApiCurrentDate() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String month1 = null;
        String day1 = null;
        if (month < 9) {
            month1 = "0" + (month + 1);
        }else{
            month1 = month + 1+"";
        }
        if (day <=9) {
            day1 = "0" + day;
        }else{
            day1=day+"";
        }

        return day1 + "-" + month1 + "-" + year;

    }

 /*   public static String geCurrendDate() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String month1 = null;
        String day1 = null;
        if (month < 9) {
            month1 = "0" + (month + 1);
        }else{
            month1 = month + 1+"";
        }
        if (day <=9) {
            day1 = "0" + day;
        }else{
            day1=day+"";
        }

        return day1 + "/" + month1 + "/" + year;

    }
    */

    public static String geCurrendDate() {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String month1 = null;
        String day1 = null;
        if (month < 9) {
            month1 = "0" + (month + 1);
        } else {
            month1 = month + 1 + "";
        }
        if (day <= 9) {
            day1 = "0" + day;
        } else {
            day1 = day + "";
        }

        // return day1 + "/" + month1 + "/" + year;
        return year+"-"+month1+"-"+day1 ;

    }

    public static String getFormatedDate_dd_mm_yyyy(String s) {

        String date = "Mar 10, 2016 6:30:00 PM";
        //SimpleDateFormat spf=new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aaa");
        SimpleDateFormat spf = new SimpleDateFormat("dd/mm/yyyy");
        Date newDate = null;
        try {
            newDate = spf.parse(s);
            spf = new SimpleDateFormat("yyyy-mm-dd");
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

      //  System.out.println(date);
        return date;
    }

}
