package com.movieapp.melihakbulut.movieapp.fragments;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.movieapp.melihakbulut.movieapp.Adapter.CustomExpAdapter;
import com.movieapp.melihakbulut.movieapp.Adapter.CustomPagerAdapter;
import com.movieapp.melihakbulut.movieapp.DBO.MovieDataSource;
import com.movieapp.melihakbulut.movieapp.R;

public class SecondFragment extends BaseFragment {
    MovieDataSource dataSource;

    public SecondFragment() {
    }

    public static SecondFragment newInstance() {
        return new SecondFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataSource=new MovieDataSource(getActivity().getBaseContext());
        final ExpandableListView expListView= (ExpandableListView) getActivity().findViewById(R.id.expListView);
        final CustomExpAdapter expAdapter=new CustomExpAdapter(dataSource.getMoviesFromDB(),getActivity());
        expListView.setIndicatorBounds(0,20);
        expListView.setAdapter(expAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                expListView.setSelectedGroup(groupPosition);
            }
        });

    }
}
