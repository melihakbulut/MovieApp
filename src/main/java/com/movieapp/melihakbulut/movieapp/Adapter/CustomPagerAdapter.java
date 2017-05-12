package com.movieapp.melihakbulut.movieapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movieapp.melihakbulut.movieapp.MainActivity;
import com.movieapp.melihakbulut.movieapp.R;

import java.util.List;

/**
 * Created by melih.akbulut on 14.04.2017.
 */
public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    Activity activity;
    LayoutInflater mLayoutInflater;
    List<Bitmap> imgList;

    public CustomPagerAdapter(Activity activity, List<Bitmap> imgList) {

        mContext = activity.getBaseContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        this.imgList=imgList;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }


    @Override
    public Object instantiateItem(View container, int position) {
        LinearLayout itemView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.pager_item, (ViewGroup) activity.findViewById(R.id.movieContent), false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageBitmap(imgList.get(position));

        TextView pageIndicator=(TextView) itemView.findViewById(R.id.pageIndicator);
        pageIndicator.setText(position+1+"/"+imgList.size());

        ((ViewPager)container).addView( itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View) object);
    }
}
