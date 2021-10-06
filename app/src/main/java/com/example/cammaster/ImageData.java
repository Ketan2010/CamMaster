package com.example.cammaster;

public class ImageData {
    public String path;
    public String date;
    public String time;
    public String location;

    public ImageData() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ImageData(String path, String date, String time, String location) {
        this.path = path;
        this.date = date;
        this.time = time;
        this.location = location;
    }
}
