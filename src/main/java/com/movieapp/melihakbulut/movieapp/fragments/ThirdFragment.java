package com.movieapp.melihakbulut.movieapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.movieapp.melihakbulut.movieapp.Adapter.CustomExpAdapter;
import com.movieapp.melihakbulut.movieapp.Adapter.CustomListViewAdapter;
import com.movieapp.melihakbulut.movieapp.Adapter.SpinnerAdapter;
import com.movieapp.melihakbulut.movieapp.Entity.Genre;
import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.R;
import com.movieapp.melihakbulut.movieapp.Task.GenreTask;
import com.movieapp.melihakbulut.movieapp.Task.ListViewTask;
import com.movieapp.melihakbulut.movieapp.Views.CustomDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ThirdFragment extends BaseFragment {

    Spinner sp;
    SpinnerAdapter adapter;
    List<Genre> genreList= null;
    AdapterView.OnItemSelectedListener selectedListener;

    List<Movie> movieList=null;
    ListView listView;
    static CustomListViewAdapter listAdapter;

    int page=1;
    String movieGenre;

    public ThirdFragment() {
        movieList=new ArrayList<Movie>();
    }

    public static ThirdFragment newInstance() {
        return new ThirdFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sp=(Spinner) view.findViewById(R.id.spinnerGenres);
        listView=(ListView)view.findViewById(R.id.listViewGenre);
        init();
        //sp.setOnItemSelectedListener(selectedListener);

        sp.post(new Runnable() {
            public void run() {
                sp.setOnItemSelectedListener(selectedListener);
            }
        });

    }

    public void addMovieToList(){
        page++;
        List<Movie> movieListAdd= null;
        try {
            movieListAdd = new ListViewTask(getActivity()).execute(movieGenre,String.valueOf(page)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        movieList.addAll(movieListAdd);
        listAdapter.notifyDataSetChanged();
    }

    public void getMovieList(String id){
        movieGenre=id;
        page=1;
        movieList= null;
        try {
            movieList = new ListViewTask(getActivity()).execute(id,String.valueOf(page)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        listAdapter=new CustomListViewAdapter(getActivity().getBaseContext(),movieList);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    public void init(){
        setGenreSpinner();
        declareListeners();
    }

    public void declareListeners(){
        selectedListener=new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("LINK",genreList.get(position).getName());
                getMovieList(genreList.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                        addMovieToList();
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.executePendingTransactions();
                FirstFragment ff=new FirstFragment();

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.fragContent,ff,"FF");
                transaction.addToBackStack(null);
                transaction.commit();


                Movie movie=(Movie)listAdapter.getItem(position);

                ff.executeGetMovieTask(getActivity(),movie);
            }
        });
    }

    public void setGenreSpinner(){

        try {
            genreList = (List<Genre>) new GenreTask(getActivity()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter=new SpinnerAdapter(getActivity().getBaseContext(),genreList);
        sp.setAdapter(adapter);
        sp.setSelection(0, false);
    }
}
