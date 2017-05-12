package com.movieapp.melihakbulut.movieapp.Entity;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by melih.akbulut on 07.04.2017.
 */
public class Movie {
    private String poster_path,backdrop_path,budget,homepage,id,imdb_id,original_language,original_title,
                    overview,popularity,release_date,revenue,runtime,status,title,vote_average,vote_count;
    private ArrayList<Genre> genres;
    private ArrayList<String> production_companies,production_countries,spoken_languages;
    private ArrayList<Image> imageList;
    private ArrayList<Video> videoList;
    private Bitmap[] bitmapArr;
    private Bitmap bitmapPoster;

    public Movie(){
        genres=new ArrayList<Genre>();
        production_companies=new ArrayList<String>();
        production_countries=new ArrayList<String>();
        spoken_languages=new ArrayList<String>();
        imageList=new ArrayList<Image>();
        videoList=new ArrayList<Video>();
    }

    public Movie(String poster_path, String backdrop_path, String budget, String homepage, String id, String imdb_id, String original_language,
                 String original_title, String overview, String popularity, String release_date, String revenue, String runtime, String status,
                 String title, String vote_average, String vote_count, ArrayList<Genre> genres, ArrayList<String> production_companies,
                 ArrayList<String> production_countries, ArrayList<String> spoken_languages,ArrayList<Image> imageList,ArrayList<Video> videoList) {
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.genres = genres;
        this.production_companies = production_companies;
        this.production_countries = production_countries;
        this.spoken_languages = spoken_languages;
        this.imageList = imageList;
        this.videoList = videoList;
    }

    public Bitmap getBitmapPoster() {
        return bitmapPoster;
    }

    public void setBitmapPoster(Bitmap bitmapPoster) {
        this.bitmapPoster = bitmapPoster;
    }

    public Bitmap[] getBitmapArr() {
        return bitmapArr;
    }

    public void setBitmapArr(Bitmap[] bitmapArr) {
        this.bitmapArr = bitmapArr;
    }

    public ArrayList<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<Video> videoList) {
        this.videoList = videoList;
    }

    public ArrayList<Image> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Image> imageList) {
        this.imageList = imageList;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
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

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getProduction_companies() {
        return production_companies;
    }

    public void setProduction_companies(ArrayList<String> production_companies) {
        this.production_companies = production_companies;
    }

    public ArrayList<String> getProduction_countries() {
        return production_countries;
    }

    public void setProduction_countries(ArrayList<String> production_countries) {
        this.production_countries = production_countries;
    }

    public ArrayList<String> getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(ArrayList<String> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }
}
