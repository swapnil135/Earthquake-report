package com.example.earthquake;

public class Row {
    private float magnitude;
    private String location;
    private String date;
    private String time;
    private String URL;
    public Row(float m, String l, String d, String t, String u )
    {
        URL=u;
        time=t;
        magnitude=m;
        location=l;
        date=d;
    }
    public float getMagnitude() {
        return magnitude;
    }
    public String getLocation()
    {
        return location;
    }
    public String getDate()
    {
        return date;
    }
    public String getTime()
    {
        return time;
    }
    public String getURL()
    {
        return URL;
    }
}
