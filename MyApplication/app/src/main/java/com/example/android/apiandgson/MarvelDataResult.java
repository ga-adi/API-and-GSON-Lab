package com.example.android.apiandgson;

/**
 * Created by ShowMe on 3/1/16.
 */
public class MarvelDataResult {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Comics comics;

    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }
}
