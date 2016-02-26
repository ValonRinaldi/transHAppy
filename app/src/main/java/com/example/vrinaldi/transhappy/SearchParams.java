package com.example.vrinaldi.transhappy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicBoolean;

public class SearchParams {
    //Direction
    public static String fromParam;
    public static String toParam;
    public static String via;
    //Date
    public static Date date;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    //Time
    public static Date time;
    public static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
    public static boolean isArrival;

    @Override
    public String toString() {
        return "From: " + fromParam
                + "; To: " + toParam
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

