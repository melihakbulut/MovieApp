package com.movieapp.melihakbulut.movieapp.Entity;

/**
 * Created by melih.akbulut on 07.04.2017.
 */
public class Image {
    private static String baseURL="http://image.tmdb.org/t/p/";
    private String width,file_path;

    public Image(){
        this.width="1000";
    }

    public Image(String width, String file_path) {
        this.width = width;
        this.file_path = file_path;
    }

    public String getPath() {
        return file_path;
    }

    public void setPath(String file_path) {
        this.file_path = file_path;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public static void setBaseURL(String baseURL) {
        Image.baseURL = baseURL;
    }
}
