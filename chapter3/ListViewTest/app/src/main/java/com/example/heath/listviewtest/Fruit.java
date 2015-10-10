package com.example.heath.listviewtest;

/**
 * Created by heath on 15-9-20.
 */
public class Fruit {
    private String name;
    private int imageID;

    public Fruit(String name_, int imageID_) {
        name = name_;
        imageID = imageID_;
    }

    public int getImageID() {
        return imageID;
    }

    public String getName() {
        return name;
    }


}
