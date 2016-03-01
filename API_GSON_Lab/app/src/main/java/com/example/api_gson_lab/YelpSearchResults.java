package com.example.api_gson_lab;

import java.util.ArrayList;

/**
 * Created by Todo on 3/1/2016.
 */
public class YelpSearchResults {
    private ArrayList<YelpLocations> businesses;

    public YelpSearchResults(ArrayList<YelpLocations> businesses) {
        this.businesses = businesses;
    }

    public ArrayList<YelpLocations> getBusinesses() {
        return businesses;
    }

    public void setBusinesses(ArrayList<YelpLocations> businesses) {
        this.businesses = businesses;
    }
}
