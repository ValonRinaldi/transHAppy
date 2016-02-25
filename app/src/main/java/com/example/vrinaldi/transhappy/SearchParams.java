package com.example.vrinaldi.transhappy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class SearchParams {
    //Direction
    private String fromParam;
    private String toParam;
    private String via;
    //Date
    private Date date;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    //Time
    private Date time;
    private static SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
    private boolean isArrival;

    @Override
    public String toString() {
        return fromParam + ";" + toParam + ";" + via + ";"
                + sdf.format(date)+ ";"
                + stf.format(time) + ";" + isArrival;





    }

    public static SearchParams parse(String input) throws ParseException {
        SearchParams searchParams = new SearchParams();
        StringTokenizer strTok = new StringTokenizer(input, ";");

        searchParams.setFromParam((String) strTok.nextElement());
        searchParams.setToParam((String) strTok.nextElement());
        searchParams.setVia((String) strTok.nextElement());
        searchParams.setDate(sdf.parse((String) strTok.nextElement()));
        searchParams.setTime(stf.parse((String) strTok.nextElement()));
        searchParams.setIsArrival(Boolean.parseBoolean((String) strTok.nextElement()));
        return searchParams;
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

    public String getFromParam() {
        return fromParam;
    }

    public void setFromParam(String fromParam) {
        this.fromParam = fromParam;
    }

    public String getToParam() {
        return toParam;
    }

    public void setToParam(String toParam) {
        this.toParam = toParam;
    }


    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public void setIsArrival(boolean isArrival) {
        this.isArrival = isArrival;
    }

    public boolean isArrival() {
        return isArrival;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStringFromDate() {
        return sdf.format(date);
    }

    public String getStringFromTime() {
        return stf.format(time);
    }
}

