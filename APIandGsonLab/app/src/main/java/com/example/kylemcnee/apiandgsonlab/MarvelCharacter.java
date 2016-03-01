package com.example.kylemcnee.apiandgsonlab;

/**
 * Created by Kyle McNee on 3/1/2016.
 */
public class MarvelCharacter {
    private String name;
    private String description;
    private MarvelThumbnail thumbnail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MarvelThumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MarvelThumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }
}
