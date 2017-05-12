package com.movieapp.melihakbulut.movieapp.DBO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.movieapp.melihakbulut.movieapp.HttpJson.JsonTags_Movie;

/**
 * Created by melih.akbulut on 26.04.2017.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME="MOVIE";
    Context context;

    public DBHelper(Context context) {
        super(context, TABLE_NAME,null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=null;
        while (query==null){
            query=getQuery();
        }

        db.execSQL(query);
    }

    public String getQuery(){
        String query="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"("+JsonTags_Movie.id.toString()+" INTEGER PRIMARY KEY,img1 varchar ,img2 varchar ,img3 varchar ,img4 varchar, img5 varchar, bitmapPoster varchar, ";
        JsonTags_Movie[] arr= JsonTags_Movie.values();
        for (int i = 0; i <arr.length ; i++) {
            if(!arr[i].toString().equals(JsonTags_Movie.id.toString())) {
                if(i!=arr.length-1)
                    query += arr[i].toString() + " varchar ,";
                else
                    query += arr[i].toString() + " varchar );";
            }
        }
        return query;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
