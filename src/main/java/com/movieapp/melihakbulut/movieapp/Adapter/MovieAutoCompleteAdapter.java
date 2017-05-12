package com.movieapp.melihakbulut.movieapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonURL;
import com.movieapp.melihakbulut.movieapp.R;
import com.movieapp.melihakbulut.movieapp.Task.DownloadImageTask;
import com.movieapp.melihakbulut.movieapp.Task.SearchMovieTask;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by melih.akbulut on 12.04.2017.
 */
public class MovieAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 20;
    private Context context;
    private List<Movie> movieList,movieListBackup;

    public MovieAutoCompleteAdapter(Context context){
        movieList=new ArrayList<Movie>();
        this.context=context;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.suggestion_dropdown_list_item, parent, false);
        }
        Bitmap img=null;
        if(movieList.get(position).getBackdrop_path()!=null && !movieList.get(position).getBackdrop_path().isEmpty()) {
            try {
                Bitmap[] bMain=new DownloadImageTask().execute(new URL[]{new URL(JsonURL.getImageURL(movieList.get(position).getPoster_path(),"300"))}).get();
                img=bMain[0];
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            ((ImageView) v.findViewById(R.id.imgMovie)).setImageBitmap(img);
        }
        ((TextView)v.findViewById(R.id.txtTitle)).setText(movieList.get(position).getTitle());
        ((TextView)v.findViewById(R.id.txtOriginal)).setText(movieList.get(position).getOriginal_title());

        return v;
    }

    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                movieListBackup=null;
                try {
                    if(constraint!=null)
                        if(constraint.length()>2) {
                            movieList = new SearchMovieTask(context).execute(constraint.toString()).get();
                        }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                FilterResults filterResults = new FilterResults();

                List<Movie> listF=new ArrayList<Movie>();

                if (movieListBackup == null&& !movieList.isEmpty())
                    movieListBackup = new ArrayList<Movie>(movieList);

                if(movieListBackup!=null)
                if (constraint == null || constraint.length() == 0) {
                    filterResults.values = movieListBackup;
                    filterResults.count = movieListBackup.size();
                }
                else{

                    for (Movie m:movieListBackup) {
                            listF.add(m);

                    }
                    filterResults.values = listF;
                    filterResults.count = listF.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.values!=null)
                    movieList=(ArrayList<Movie>)results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
