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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by melih.akbulut on 03.05.2017.
 */
public class CustomListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Movie> movieList,movieListBackup;
    LayoutInflater inflater;

    static ImageView imgView;
    static TextView title;
    static TextView titleOriginal;

    public CustomListViewAdapter(Context context,List<Movie> movieList){
        this.movieList=movieList;
        this.context=context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return Long.parseLong(movieList.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;

        if(v==null)
            v = inflater.inflate(R.layout.listview_list_item, parent, false);

        Bitmap img=null;
        imgView= (ImageView) v.findViewById(R.id.imgMovie);

        if(movieList.get(position).getPoster_path()!=null && !movieList.get(position).getPoster_path().isEmpty()) {
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
            imgView.setImageBitmap(img);
        }
        title=(TextView) v.findViewById(R.id.txtTitle);
        titleOriginal=(TextView) v.findViewById(R.id.txtOriginal);

        title.setText(movieList.get(position).getTitle());
        titleOriginal.setText(movieList.get(position).getOriginal_title());

        return v;
    }

}
