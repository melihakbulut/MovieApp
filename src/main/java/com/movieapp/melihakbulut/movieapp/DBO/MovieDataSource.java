package com.movieapp.melihakbulut.movieapp.DBO;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.movieapp.melihakbulut.movieapp.Entity.Genre;
import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonRequest;
import com.movieapp.melihakbulut.movieapp.HttpJson.JsonTags_Movie;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by melih.akbulut on 26.04.2017.
 */
public class MovieDataSource {
    Context context;
    DBHelper db;
    SQLiteDatabase dataSource;

    public MovieDataSource(Context context){
        this.context=context;
        db=new DBHelper(context);
    }

    public long doOperation(Movie movie, OPERATION_TYPE type, Bitmap[] bitmapArr){
        long row=0;
        dataSource=db.getWritableDatabase();
        ContentValues values=new ContentValues();
        JsonTags_Movie[] arr= JsonTags_Movie.values();
        JsonRequest jr=new JsonRequest(context);

        for (int i = 0; i <arr.length ; i++) {
            if(!arr[i].toString().equals(JsonTags_Movie.genres.toString()))
            values.put(arr[i].toString(),(String)jr.getProperty(movie,arr[i].toString()));
            else
                break;
        }
        String genres="";
        for (int i = 0; i <movie.getGenres().size() ; i++) {
            genres+=movie.getGenres().get(i).getName();
            if(i!=movie.getGenres().size()-1)
                genres+=" ,";
        }
        values.put(JsonTags_Movie.genres.toString(),genres);
        values.put(JsonTags_Movie.production_companies.toString(),getListAsString(movie.getProduction_companies()));
        values.put(JsonTags_Movie.production_countries.toString(),getListAsString(movie.getProduction_countries()));
        values.put(JsonTags_Movie.spoken_languages.toString(),getListAsString(movie.getSpoken_languages()));


        for (int i = 0; i <bitmapArr.length ; i++) {
            String path=saveToInternalStorage(bitmapArr[i],movie.getId()+i);
            values.put("img"+(i+1),path);
        }
        values.put("bitmapPoster",saveToInternalStorage(movie.getBitmapPoster(),movie.getId()+"poster"));

        try{
            dataSource.beginTransaction();
            if(type.equals(OPERATION_TYPE.insert))
                row = dataSource.insert(DBHelper.TABLE_NAME, null, values);
            dataSource.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            dataSource.endTransaction();
            dataSource.close();
            db.close();
        }
        Log.d("DB-INSERT",String.valueOf(row));
        Toast.makeText(context,"Kaydetme işlemi başarılı",Toast.LENGTH_SHORT).show();
        return row;
    }

//    public byte[] getBitmapAsByteArray(Bitmap bitmap){
//        if(bitmap!=null) {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//            return stream.toByteArray();
//        }
//        else
//            return new byte[]{0};
//    }
//
//    public Bitmap getImage(byte[] image) {
//        return BitmapFactory.decodeByteArray(image, 0, image.length);
//    }

    public List<Movie> getMoviesFromDB() {
        List<Movie> movies = new ArrayList<Movie>();
        dataSource = db.getReadableDatabase();
        JsonTags_Movie[] arr = JsonTags_Movie.values();
        List<String> queryList = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++)
            queryList.add(arr[i].toString());

        for (int i = 0; i < 5; i++)
            queryList.add("img" + (i + 1));

        queryList.add("bitmapPoster");
        String[] queryArray = new String[queryList.size()];
        Cursor cursor = dataSource.query(DBHelper.TABLE_NAME, (String[]) queryList.toArray(queryArray), null, null, null, null, JsonTags_Movie.title + " ASC");

        cursor.moveToFirst();
        JsonRequest jr = new JsonRequest(context);
        int index = 0;
        while (!cursor.isAfterLast()) {
            Movie movie=new Movie();
            for (int i = 0; i < JsonTags_Movie.values().length; i++) {

                String column = JsonTags_Movie.values()[i].toString();
                if (column == JsonTags_Movie.genres.toString())
                    break;
                jr.setProperty(movie, column, cursor.getString(cursor.getColumnIndex(column)));


            }
            List<String> genreString = getStringAsList(cursor.getString(cursor.getColumnIndex(JsonTags_Movie.genres.toString())));
            ArrayList<Genre> genreList = new ArrayList<Genre>();
            for (int i = 0; i < genreString.size(); i++) {
                genreList.add(new Genre(String.valueOf(i), genreString.get(i)));
            }
            movie.setGenres(genreList);
            movie.setProduction_companies(getStringAsList(cursor.getString(cursor.getColumnIndex(JsonTags_Movie.production_companies.toString()))));
            movie.setProduction_countries(getStringAsList(cursor.getString(cursor.getColumnIndex(JsonTags_Movie.production_countries.toString()))));
            movie.setSpoken_languages(getStringAsList(cursor.getString(cursor.getColumnIndex(JsonTags_Movie.spoken_languages.toString()))));

            Bitmap posterImage=loadImageFromInternalStorage(cursor.getString(cursor.getColumnIndex("bitmapPoster")));
            movie.setBitmapPoster(posterImage);
            Bitmap[] bitmapArr=new Bitmap[5];
            for (int i = 0; i < 5; i++){
                String path=cursor.getString(cursor.getColumnIndex("img" + (i + 1)));
                if(path!=null)
                    bitmapArr[i]=loadImageFromInternalStorage(path);
            }
            movie.setBitmapArr(bitmapArr);

            movies.add(movie);

            cursor.moveToNext();
        }
        cursor.close();
        dataSource.close();
        db.close();

        return movies;
    }

    public ArrayList<String> getStringAsList(String col){
        if(col!=null && col.contains(",")) {
            String arr[] = col.split(",");
            return new ArrayList<>(Arrays.asList(arr));
        }
        return new ArrayList<>();
    }

    public String getListAsString(List<String> list){
        String s="";
        for (int i = 0; i <list.size() ; i++) {
            if(i!=list.size()-1)
            s+=list.get(i)+",";
            else
            s+=list.get(i);
        }
        return s;
    }

    private String saveToInternalStorage(Bitmap bitmap,String name){
        ContextWrapper cw=new ContextWrapper(context.getApplicationContext());
        File dir = cw.getDir("imageDir",Context.MODE_PRIVATE);
        File imagePath=new File(dir,name+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imagePath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            return dir.getAbsolutePath()+"/"+name+".jpg";
    }

    public Bitmap loadImageFromInternalStorage(String path){
        Bitmap bitmap=null;
        try {
            File f=new File(path);
            bitmap= BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

}
