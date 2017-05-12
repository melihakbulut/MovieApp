package com.movieapp.melihakbulut.movieapp.Views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by melih.akbulut on 14.04.2017.
 */
public class MovieInfoInner extends LinearLayout {
    Context context;
    LinearLayout.LayoutParams paramsText;


    public MovieInfoInner(Context context) {
        super(context);
        this.context=context;
    }

    public LinearLayout getMovieInnerLayout(String text,String value){
        LinearLayout movieInner=getInnerLayout(HORIZONTAL);

        paramsText = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        paramsText.weight=4f;

        TextView infoTxt=getInfoTextView(text);
        infoTxt.setLayoutParams(paramsText);
        movieInner.addView(infoTxt);

        paramsText.weight=5f;

        TextView valueTxt=getValueTextView(value);
        valueTxt.setLayoutParams(paramsText);
        movieInner.addView(valueTxt);

        return movieInner;
    }

    public LinearLayout getInnerLayout(int orientation){
        LinearLayout movieInner=new LinearLayout(context);
        movieInner.setOrientation(orientation);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,getDimensionInDP(5));
        movieInner.setWeightSum(10f);
        movieInner.setLayoutParams(params);

        return movieInner;
    }


    public TextView getInfoTextView(String text){
        TextView infoText=new TextView(context);
        infoText.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        infoText.setTextColor(Color.WHITE);
        infoText.setTypeface(null, Typeface.BOLD);
        infoText.setText(text);
        return infoText;
    }

    public TextView getValueTextView(String value){
        //params.setMargins(getDimensionInDP(10),0,0,0);
        TextView valueText=new TextView(context);
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        valueText.setTextColor(Color.WHITE);
        valueText.setText(value);
        return valueText;
    }

    public int getDimensionInDP(int dim){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dim, getResources().getDisplayMetrics());
    }
}
