package com.movieapp.melihakbulut.movieapp.Task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.movieapp.melihakbulut.movieapp.Entity.Genre;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonRequest;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonURL;
import com.movieapp.melihakbulut.movieapp.HttpJson.LinkType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melih.akbulut on 02.05.2017.
 */
public class GenreTask extends AsyncTask<String,List<String>,List<Genre>> {

    Activity activity;
    public GenreTask(Activity activity){
        this.activity=activity;
    }

    @Override
    protected List<Genre> doInBackground(String... params) {
        List<Genre> listGenre=(List<Genre>) new JsonRequest(activity).produceLink(LinkType.GET_GENRES,"", JsonURL.TR);
        return listGenre;
    }

}
