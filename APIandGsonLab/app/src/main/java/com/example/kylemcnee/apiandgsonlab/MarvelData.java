package com.example.kylemcnee.apiandgsonlab;

import java.util.ArrayList;

/**
 * Created by Kyle McNee on 3/1/2016.
 */
public class MarvelData {
    ArrayList<MarvelCharacter> results;

    public ArrayList<MarvelCharacter> getResults() {
        return results;
    }

    public void setResults(ArrayList<MarvelCharacter> results) {
        this.results = results;
    }
}
