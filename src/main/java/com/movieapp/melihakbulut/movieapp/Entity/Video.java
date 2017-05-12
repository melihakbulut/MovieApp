package com.movieapp.melihakbulut.movieapp.Entity;

/**
 * Created by melih.akbulut on 12.04.2017.
 */
public class Video {
    private static String youtube="http://youtube.com/watch?v=";
    private String key,name,site,type;

    public Video(){

    }

    public String getYoutubeLink(){
        String link = null;
        if(site.equals("YouTube"))
            link=youtube+key;

        return link;
    }

    public Video(String key, String name, String site, String type) {
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
