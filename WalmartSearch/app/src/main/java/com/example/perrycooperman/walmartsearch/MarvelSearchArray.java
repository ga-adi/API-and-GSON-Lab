package com.example.perrycooperman.walmartsearch;

import java.util.ArrayList;

/**
 * Created by perrycooperman on 3/1/16.
 */
public class MarvelSearchArray {
    ArrayList<MarvelObject> results;

    public ArrayList<MarvelObject> getResults() {
            return results;
    }

    public void setResults(ArrayList<MarvelObject> results) {
        this.results = results;
    }

    public String toString() {
        return results.size()+" item(s) in the search result";
    }


}
