package com.movieapp.melihakbulut.movieapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.movieapp.melihakbulut.movieapp.Entity.Genre;
import com.movieapp.melihakbulut.movieapp.R;

import java.util.List;

/**
 * Created by melih.akbulut on 02.05.2017.
 */
public class SpinnerAdapter extends BaseAdapter {
    List<Genre> list;
    Context context;
    public SpinnerAdapter(Context context,List<Genre> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(list.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.dropdown_item, parent, false);
        }
        TextView txt= (TextView) v.findViewById(R.id.textGenre);
        txt.setText(list.get(position).getName());

        return v;
    }
}
