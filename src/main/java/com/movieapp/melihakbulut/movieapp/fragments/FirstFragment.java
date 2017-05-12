package com.movieapp.melihakbulut.movieapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.movieapp.melihakbulut.movieapp.Adapter.CustomPagerAdapter;
import com.movieapp.melihakbulut.movieapp.Adapter.DelayAutoCompleteTextView;
import com.movieapp.melihakbulut.movieapp.Adapter.MovieAutoCompleteAdapter;
import com.movieapp.melihakbulut.movieapp.Entity.Genre;
import com.movieapp.melihakbulut.movieapp.Entity.Image;
import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonRequest;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonTags_Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonURL;
import com.movieapp.melihakbulut.movieapp.HttpJson.LinkType;
import com.movieapp.melihakbulut.movieapp.MainActivity;
import com.movieapp.melihakbulut.movieapp.R;
import com.movieapp.melihakbulut.movieapp.Task.DownloadImageTask;
import com.movieapp.melihakbulut.movieapp.Task.GetMovieTask;
import com.movieapp.melihakbulut.movieapp.Views.Conversion;
import com.movieapp.melihakbulut.movieapp.Views.CustomDialog;
import com.movieapp.melihakbulut.movieapp.Views.MovieInfoInner;
import com.movieapp.melihakbulut.movieapp.Views.WrapContentHeightViewPager;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogRecord;

public class FirstFragment extends BaseFragment {

    private static final int THRESHOLD =3 ;
    ImageView img;
    DelayAutoCompleteTextView searchMovie;
    DrawerLayout drawer;
    LinearLayout layoutFragment;
    Context context;
    CustomPagerAdapter adapter;
    ViewPager pager;


    public FirstFragment() {
    }

    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutFragment=(LinearLayout)getActivity().findViewById(R.id.firstFragmentLayout);
    }

    public Movie executeGetMovieTask(final Activity act, final Movie movie){
        Movie m=null;
        if(movie!=null)
            new GetMovieTask(act,layoutFragment).execute(movie.getId(), JsonURL.TR);

        return m;
    }

}
