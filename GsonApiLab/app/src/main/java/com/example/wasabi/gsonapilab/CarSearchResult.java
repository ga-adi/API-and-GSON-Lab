package com.example.wasabi.gsonapilab;

import java.util.ArrayList;

/**
 * Created by Wasabi on 3/1/2016.
 */
public class CarSearchResult {

    private ArrayList<Car> models;

    public ArrayList<Car> getModels() {
        return models;
    }

    public void setModels(ArrayList<Car> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return models.size() + "item(s) in the search result";
    }
}
