package com.example.comp5216_petloversapp;

import java.io.Serializable;

public class News implements Serializable {
    public News(String title, String description, String time, int image) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.image = image;
    }

    public String title;
    public String description;
    public String time;
    public int image;
}
