package com.example.android.apiandgson;

/**
 * Created by ShowMe on 3/1/16.
 */
public class Item {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;

    }
}
