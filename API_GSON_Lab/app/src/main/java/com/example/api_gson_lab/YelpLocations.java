package com.example.api_gson_lab;

/**
 * Created by Todo on 3/1/2016.
 */
public class YelpLocations {
    private String name;
    private double rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return name;
    }
}
