package com.example.bookings;

public class Bookings {
    private String campus;
    private String date;
    private String time;

    public Bookings(String campus, String date, String time) {
        this.campus = campus;
        this.date = date;
        this.time = time;
    }

    public String getCampus() {
        return campus;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
