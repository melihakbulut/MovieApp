package com.movieapp.melihakbulut.movieapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.R;
import com.movieapp.melihakbulut.movieapp.Views.Conversion;
import com.movieapp.melihakbulut.movieapp.Views.MovieInfoInner;
import com.movieapp.melihakbulut.movieapp.Views.WrapContentHeightViewPager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by melih.akbulut on 28.04.2017.
 */
public class CustomExpAdapter extends BaseExpandableListAdapter {
    List<Movie> movies;
    Activity activity;
    Context context;
    LinearLayout movieInfoLayout;
    ImageView img;
    TextView title;
    static String companies;
    static String genre;
    LinearLayout movieContent;
    WrapContentHeightViewPager pager;
    CustomPagerAdapter adapter;
    LinearLayout overview;
    ImageView imdb;
    static MovieInfoInner movieInfoInner;
    static LayoutInflater inflater;

    public CustomExpAdapter(List<Movie> movies,Activity activity){
        this.movies=movies;
        this.activity=activity;
        this.context=activity.getBaseContext();
        movieInfoInner=new MovieInfoInner(context);
        inflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return movies.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return movies.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v=convertView;

        if (v == null) {
            v = inflater.inflate(R.layout.exp_list_group, parent, false);
        }

        img=(ImageView)v.findViewById(R.id.poster_path);
        title=(TextView)v.findViewById(R.id.original_title);

        img.setImageBitmap(movies.get(groupPosition).getBitmapPoster());
        title.setText(movies.get(groupPosition).getTitle());

        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v=convertView;
        final Movie movie=movies.get(groupPosition);

            v = inflater.inflate(R.layout.fragment_first, parent, false);

        img=(ImageView)v.findViewById(R.id.poster_path);
        title=(TextView)v.findViewById(R.id.original_title);

        img.setImageBitmap(movies.get(groupPosition).getBitmapPoster());
        title.setText(movies.get(groupPosition).getTitle());

        img.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);

        movieInfoLayout=(LinearLayout)v.findViewById(R.id.movieInfo) ;
        movieInfoLayout.addView(getMovieInfoInnerLayoutStatic("Yayın Tarihi", Conversion.toDate(movie.getRelease_date())));

        genre="";

        for (int i = 0; i < movie.getGenres().size() ; i++) {
            genre+=movie.getGenres().get(i).getName();
            if(i!=movie.getGenres().size()-1)
                genre+=" ,";

        }

        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Tür",genre));

        movieInfoLayout.addView(getMovieInfoInnerLayoutStatic("Orjinal Dil",Conversion.getLang(movie.getOriginal_language())));
        movieInfoLayout.addView(getMovieInfoInnerLayoutStatic("Bütçe",Conversion.toCurrency(movie.getRevenue())));
        movieInfoLayout.addView(getMovieInfoInnerLayoutStatic("Süre",movie.getRuntime()+" dk"));

        companies="";
        for (int i = 0; i < movie.getProduction_companies().size() ; i++) {
            companies+=movie.getProduction_companies().get(i);
            if(i!=movie.getProduction_companies().size()-1)
                companies+=" ,";
        }

        movieInfoLayout.addView(getMovieInfoInnerLayoutStatic("Yapımcı",companies));
        movieInfoLayout.addView(getMovieInfoInnerLayoutStatic("Puan",movie.getVote_average()+"   ( "+movie.getVote_count()+" oy )"));


        movieContent=(LinearLayout)v.findViewById(R.id.movieContent);

        pager = new WrapContentHeightViewPager(context);

        adapter = new CustomPagerAdapter(activity, Arrays.asList(movie.getBitmapArr()));

        pager.setAdapter(adapter);
        movieContent.addView(pager);

        overview = new LinearLayout(context);
        overview.setOrientation(LinearLayout.VERTICAL);
        overview.addView(new MovieInfoInner(context).getInfoTextView("Film Özeti"));
        overview.addView(new MovieInfoInner(context).getValueTextView(movie.getOverview()));

        movieContent.addView(overview);


        imdb=(ImageView)v.findViewById(R.id.imdb);
        imdb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.imdb.com/title/"+movie.getImdb_id()));
                activity.startActivity(intent);
            }
        });

        return v;
    }

    public LinearLayout getMovieInfoInnerLayoutStatic(String param1,String param2){
        return movieInfoInner.getMovieInnerLayout(param1,param2);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
