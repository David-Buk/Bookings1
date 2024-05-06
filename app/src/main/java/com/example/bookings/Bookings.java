package com.example.bookings;

public class Bookings {
    private String email;
    private String date;
    private String time;

    public Bookings(String email, String date, String time) {
        this.email = email;
        this.date = date;
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
