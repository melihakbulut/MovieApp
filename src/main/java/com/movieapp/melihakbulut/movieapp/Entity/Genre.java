package com.movieapp.melihakbulut.movieapp.Entity;

/**
 * Created by melih.akbulut on 07.04.2017.
 */
public class Genre {
    private String id,name;

    public Genre(){

    }

    public Genre(String id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
