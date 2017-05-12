package com.movieapp.melihakbulut.movieapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.movieapp.melihakbulut.movieapp.Adapter.DelayAutoCompleteTextView;
import com.movieapp.melihakbulut.movieapp.Adapter.MovieAutoCompleteAdapter;
import com.movieapp.melihakbulut.movieapp.DBO.MovieDataSource;
import com.movieapp.melihakbulut.movieapp.DBO.OPERATION_TYPE;
import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonRequest;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonTags_Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonURL;
import com.movieapp.melihakbulut.movieapp.Task.GetMovieTask;
import com.movieapp.melihakbulut.movieapp.fragments.BaseFragment;
import com.movieapp.melihakbulut.movieapp.fragments.FirstFragment;
import com.movieapp.melihakbulut.movieapp.fragments.SecondFragment;
import com.movieapp.melihakbulut.movieapp.fragments.ThirdFragment;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BaseFragment.OnFragmentInteractionListener {
    private  DrawerLayout drawer;
    private static final int THRESHOLD =3 ;
    DelayAutoCompleteTextView searchMovie;
    static Movie selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getWindow().requestFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initialFragment();
        executeGetMovieTask();
    }

    public void executeGetMovieTask(){
        searchMovie=(DelayAutoCompleteTextView)findViewById(R.id.searchText);
        searchMovie.setThreshold(THRESHOLD);
        final MovieAutoCompleteAdapter movieAdapter =new MovieAutoCompleteAdapter(this);
        searchMovie.setAdapter(movieAdapter);
        searchMovie.setLoadingIndicator((android.widget.ProgressBar)findViewById(R.id.pb_loading_indicator));
        searchMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Activity act=MainActivity.this;

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.executePendingTransactions();
                FirstFragment ff=new FirstFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.fragContent,ff,"FF");
                transaction.addToBackStack(null);
                transaction.commit();


                Movie movie=(Movie)adapter.getItemAtPosition(position);
                searchMovie.setText(movie.getTitle());

                drawer.closeDrawers();
                selectedMovie=ff.executeGetMovieTask(act,movie);
            }
        });
    }

    public void initialFragment(){
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragContent,new ThirdFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static void setSelectedMovie(Movie movie){
        selectedMovie=movie;
    }

    public static Movie getSelectedMovie(){
        return selectedMovie;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveMovie) {
            if(selectedMovie!=null)
                new MovieDataSource(this.getBaseContext()).doOperation(selectedMovie, OPERATION_TYPE.insert,selectedMovie.getBitmapArr());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        selectDrawerItem(item);
        return true;
    }

    public void selectDrawerItem(MenuItem item){
        Fragment f=null;
        Class fragmentClass;

        switch (item.getItemId()){
            case R.id.nav_slideshow:
                fragmentClass= SecondFragment.class; break;
            case R.id.nav_manage:
                fragmentClass= ThirdFragment.class; break;
            default:
                fragmentClass=ThirdFragment.class;
        }

        try {
            f=(Fragment) fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragContent,f).commit();

        item.setChecked(true);
        setTitle(item.getTitle());

        drawer.closeDrawers();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
