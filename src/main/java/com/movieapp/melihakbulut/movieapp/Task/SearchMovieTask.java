package com.movieapp.melihakbulut.movieapp.Task;

import android.content.Context;
import android.os.AsyncTask;

import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonRequest;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonURL;
import com.movieapp.melihakbulut.movieapp.HttpJson.LinkType;

import java.util.ArrayList;

/**
 * Created by melih.akbulut on 12.04.2017.
 */
public class SearchMovieTask extends AsyncTask< String,Void,ArrayList<Movie>> {
    JsonRequest jRequest;
    private Context c;

    public SearchMovieTask(Context c){
        this.c=c;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        jRequest=new JsonRequest(c);
        ArrayList<Movie> mList=(ArrayList<Movie>)jRequest.produceLink(LinkType.SEARCH_MOVIE,params[0], JsonURL.TR);
        return mList;
    }
}
