package com.devmohamedibrahim1997.noteapp;

import android.widget.Toast;

import java.text.DateFormatSymbols;

public class HelperClass {

    public static String getYear(String s){
        return s+ "  ";
    }

    public static String getMonth(String s){
        String month = "";
        int num = Integer.parseInt(s) - 1;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
       return month + " ";
    }

    public static String getDay(String s){
        String day = "";
        if (Integer.parseInt(s) < 10) {
            day = s.substring(1, 2);
        }else{
            day = s;
        }
        return day + " ";
    }

    public static String getTime(String hours, String minutes){

        String time = "";

        if (Integer.parseInt(hours) >= 0 && Integer.parseInt(hours) < 10 ) {
            hours = hours.substring(1, 2);
        }

        if (Integer.parseInt(hours) < 12 && Integer.parseInt(hours) > 0) {
            time = hours + ":"  + minutes + " AM";
        }
        else if(Integer.parseInt(hours) > 12){
            time = (Integer.parseInt(hours) - 12) + ":" + minutes + " PM";
        }
        else if (Integer.parseInt(hours) == 12) {
            time = hours + ":" + minutes + " PM";
        }
        else if(Integer.parseInt(hours) == 0){
            time = "12" + ":" + minutes + " AM";
        }

        return time;
    }
}
