package com.example.vrinaldi.transhappy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchParams {
    //Direction
    public static String from;
    public static String to;
    public static String via;
    //Date
    public static Date date;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    //Time
    public static Date time;
    public static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
    //arrival
    public static boolean isArrival;

    @Override
    public String toString() {
        return "From: " + from
                + "; To: " + to
                + "; via: " + via
                + "; date: " + sdf.format(date)
                + "; time: " + stf.format(time)
                + "; isArrival" + isArrival;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof  SearchParams)) {
            return false;
        }
        SearchParams other = (SearchParams) obj;
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}

