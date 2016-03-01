package com.example.api_gson_lab;

/**
 * Created by Todo on 3/1/2016.
 */
public class YelpLocations {
    private String name;
    private int rating;

    public YelpLocations(String name, int rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name;
    }
}
