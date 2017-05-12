package com.movieapp.melihakbulut.movieapp.Task;

import android.app.Activity;
import android.os.AsyncTask;

import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonRequest;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonURL;
import com.movieapp.melihakbulut.movieapp.HttpJson.LinkType;
import com.movieapp.melihakbulut.movieapp.Views.CustomDialog;
import com.movieapp.melihakbulut.movieapp.fragments.ThirdFragment;

import java.util.List;

/**
 * Created by melih.akbulut on 03.05.2017.
 */
public class ListViewTask extends AsyncTask<String,Void,List<Movie>> {

    Activity activity;
    List<Movie> movieList;
    public ListViewTask(Activity activity){
        this.activity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        movieList=(List<Movie>) new JsonRequest(activity).produceLink(LinkType.GET_MOVIE_ACC_TO_GENRE,params[0], JsonURL.TR,params[1]);
        return movieList;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);

    }
}
