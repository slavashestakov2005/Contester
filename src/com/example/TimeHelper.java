package com.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {
    private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    public static String toJS(long time){
        return format.format(new Date(time));
    }

    public static String getMinTime(){
        return "2020-10-21T19:50";
    }

    public static String getMaxTime(){
        return "9999-12-31T23:59";
    }

    public static String getDateTimeAttributes(){
        return "min=\"" + getMinTime() + "\" max=\"" + getMaxTime() + "\"";
    }
}
