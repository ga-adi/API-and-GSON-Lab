package com.example.wasabi.gsonapilab;

/**
 * Created by Wasabi on 3/1/2016.
 */
public class Car {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + "\n" + name;
    }
}
