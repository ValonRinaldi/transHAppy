package com.example.vrinaldi.transhappy;

/**
 * Created by martius on 22.02.16.
 */
public class Connection {


    private String From;
    private String To;
    private String Date;
    private String Track;
    private int id;

    public Connection(int id, String From, String To, String Date, String Track) {
        this.id = id;
        this.From = From;
        this.To = To;
        this.Date = Date;
        this.Track = Track;
    }

    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }

    public String getDate() {
        return Date;
    }

    public String getTrack() {
        return Track;
    }


    public int getId() {
        return id;
    }

}
