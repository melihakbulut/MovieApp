package com.movieapp.melihakbulut.movieapp.Entity;

import java.util.ArrayList;

/**
 * Created by melih.akbulut on 07.04.2017.
 */
public class Person {
    private String name,birthday,place_of_birth,also_known_as,
            biography,gender,deathday,id,imdb_id,profile_path,popularity,homepage;
    private ArrayList<Movie> movieList;

    public Person(){
        movieList=new ArrayList<Movie>();
    }


    public Person(String name, String birthday, String place_of_birth, String also_known_as, String biography,
                  String gender, String deathday, String id, String imdb_id, String profile_path, String popularity, String homepage,ArrayList<Movie> movieList) {
        this.name = name;
        this.birthday = birthday;
        this.place_of_birth = place_of_birth;
        this.also_known_as = also_known_as;
        this.biography = biography;
        this.gender = gender;
        this.deathday = deathday;
        this.id = id;
        this.imdb_id = imdb_id;
        this.profile_path = profile_path;
        this.popularity = popularity;
        this.homepage = homepage;
        this.movieList = movieList;
    }

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(String also_known_as) {
        this.also_known_as = also_known_as;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
}
