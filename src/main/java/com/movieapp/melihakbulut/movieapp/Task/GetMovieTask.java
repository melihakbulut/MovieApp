package com.movieapp.melihakbulut.movieapp.Task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.movieapp.melihakbulut.movieapp.Adapter.CustomPagerAdapter;
import com.movieapp.melihakbulut.movieapp.Entity.Image;
import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonRequest;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonURL;
import com.movieapp.melihakbulut.movieapp.HttpJson.LinkType;
import com.movieapp.melihakbulut.movieapp.MainActivity;
import com.movieapp.melihakbulut.movieapp.R;
import com.movieapp.melihakbulut.movieapp.Views.Conversion;
import com.movieapp.melihakbulut.movieapp.Views.CustomDialog;
import com.movieapp.melihakbulut.movieapp.Views.MovieInfoInner;
import com.movieapp.melihakbulut.movieapp.Views.WrapContentHeightViewPager;
import com.movieapp.melihakbulut.movieapp.fragments.BaseFragment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by melih.akbulut on 20.04.2017.
 */
public class GetMovieTask extends AsyncTask<String,Void,Movie> {

    JsonRequest jRequest;
    Context context;
    Activity activity;
    LinearLayout layoutFragment;
    CustomPagerAdapter adapter;
    ViewPager pager;
    CustomDialog dialog ;
    private int movieImageCount=5;


    public GetMovieTask(Activity activity, LinearLayout layoutFragment){
        this.context=activity.getBaseContext();
        this.activity=activity;
        this.layoutFragment=layoutFragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new CustomDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        }

    @Override
    protected Movie doInBackground(String... params) {

        jRequest=new JsonRequest(context);
        Movie m=(Movie)jRequest.produceLink(LinkType.GET_MOVIE_DETAIL,params[0], JsonURL.TR);
        return m;
    }

    @Override
    protected void onPostExecute(final Movie movie) {
        super.onPostExecute(movie);

        LinearLayout movieInfoLayout=(LinearLayout)activity.findViewById(R.id.movieInfo) ;

        ImageView imgMain=(ImageView)activity.findViewById(R.id.poster_path);
        imgMain.setVisibility(View.VISIBLE);
        try {
            Bitmap[] bMain=new DownloadImageTask().execute(new URL[]{new URL(JsonURL.getImageURL(movie.getPoster_path()))}).get();
            imgMain.setImageBitmap(bMain[0]);
            movie.setBitmapPoster(bMain[0]);
            //layoutFragment.addView(imgMain);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        TextView txt=(TextView)activity.findViewById(R.id.original_title);
        txt.setVisibility(View.VISIBLE);
        txt.setText(movie.getTitle()+" ( "+movie.getOriginal_title()+" )");

        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Yayın Tarihi", Conversion.toDate(movie.getRelease_date())));
        String genre="";

        for (int i = 0; i < movie.getGenres().size() ; i++) {
            genre+=movie.getGenres().get(i).getName();
            if(i!=movie.getGenres().size()-1)
                genre+=" ,";

        }

        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Tür",genre));

        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Orjinal Dil",Conversion.getLang(movie.getOriginal_language())));
        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Bütçe",Conversion.toCurrency(movie.getRevenue())));
        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Süre",movie.getRuntime()+" dk"));

        String companies="";
        for (int i = 0; i < movie.getProduction_companies().size() ; i++) {
            companies+=movie.getProduction_companies().get(i);
            if(i!=movie.getProduction_companies().size()-1)
                companies+=" ,";
        }

        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Yapımcı",companies));
        movieInfoLayout.addView(new MovieInfoInner(context).getMovieInnerLayout("Puan",movie.getVote_average()+"   ( "+movie.getVote_count()+" oy )"));

        LinearLayout movieContent=(LinearLayout)activity.findViewById(R.id.movieContent);

        List<Bitmap> imgList=new ArrayList<Bitmap>();

        int index=0;
        URL[] urlArr=new URL[movieImageCount];
        for (Image e: movie.getImageList()) {

            if(index==movieImageCount)
                break;
            try {
                urlArr[index]=new URL(JsonURL.getImageURL(e.getPath(),"1000"));
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
            index++;
        }
        Bitmap[] bitmapArr=null;
        try {
            bitmapArr=new DownloadImageTask().execute(urlArr).get();
            if(bitmapArr!=null)
                imgList=Arrays.asList(bitmapArr);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }


        pager = new WrapContentHeightViewPager(context);

        adapter = new CustomPagerAdapter(activity,imgList);

        pager.setAdapter(adapter);
        movieContent.addView(pager);


        LinearLayout overview=new LinearLayout(context);
        overview.setOrientation(LinearLayout.VERTICAL);
        overview.addView(new MovieInfoInner(context).getInfoTextView("Film Özeti"));
        overview.addView(new MovieInfoInner(context).getValueTextView(movie.getOverview()));

        movieContent.addView(overview);

        ImageView imdb=(ImageView)activity.findViewById(R.id.imdb);
        imdb.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.imdb.com/title/"+movie.getImdb_id()));
                activity.startActivity(intent);
            }
        });

        if(bitmapArr!=null)
        movie.setBitmapArr(bitmapArr);

        MainActivity.setSelectedMovie(movie);

        dialog.dismiss();

    }

}
