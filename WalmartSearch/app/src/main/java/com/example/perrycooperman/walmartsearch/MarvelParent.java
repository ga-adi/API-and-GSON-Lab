package com.example.perrycooperman.walmartsearch;

/**
 * Created by perrycooperman on 3/1/16.
 */
public class MarvelParent {
    public MarvelSearchArray data;

    public MarvelSearchArray getData() {
        return data;
    }

    public void setData(MarvelSearchArray data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
