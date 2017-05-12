package com.movieapp.melihakbulut.movieapp.HttpJson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.movieapp.melihakbulut.movieapp.Entity.Genre;
import com.movieapp.melihakbulut.movieapp.Entity.Image;
import com.movieapp.melihakbulut.movieapp.Entity.Movie;
import com.movieapp.melihakbulut.movieapp.Entity.Person;
import com.movieapp.melihakbulut.movieapp.Entity.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by melih.akbulut on 07.04.2017.
 */
public class JsonRequest {
    Context c;
    JSONObject json;
    //String urlString="http://api.themoviedb.org/3/search/person?api_key=81ce029bedd80648a31faa80ff8e7b63&language=tr-TR&query=john&page=1&include_adult=false";


    public JsonRequest(Context c){
        this.c=c;
        json=new JSONObject();
    }

    public Object produceLink(LinkType l,String... params){
        Object o=null;
        String urlLink=null;
        JsonURL urlGenerator=new JsonURL();

        if(l.equals(LinkType.GET_MOVIE_DETAIL)) {
            urlLink=urlGenerator.getMovieDetail(params[0], params[1]);
            o=getMovieDetailFromObject(get(urlLink));
        }
        else if(l.equals(LinkType.SEARCH_MOVIE)){
            urlLink=urlGenerator.getSearchMovie(params[0], params[1]);
            o=getMovieSearch(get(urlLink));
        }
        else if(l.equals(LinkType.GET_PERSON_DETAIL)){
            urlLink=urlGenerator.getDetailPerson(params[0], params[1]);
            o=getPersonDetailFromObject(get(urlLink));
        }
        else if(l.equals(LinkType.SEARCH_PERSON)){
            urlLink=urlGenerator.getSearchPerson(params[0], params[1]);
            o=getPersonSearch(get(urlLink));
        }
        else if(l.equals(LinkType.GET_PERSON_MOVIES)){
            urlLink=urlGenerator.getPersonMovies(params[0], params[1]);
            o=getPersonMovies(get(urlLink));
        }
        else if(l.equals(LinkType.GET_GENRES)){
            urlLink=urlGenerator.getGenres(params[0], params[1]);
            o=getGenreList(get(urlLink));
        }
        else if (l.equals(LinkType.GET_MOVIE_ACC_TO_GENRE)){
            urlLink=urlGenerator.getMovieListAccGenre(params[0], params[1],JsonURL.SORT_DESC,params[2]);
            o=getMovieListAccToGenre(get(urlLink));
        }
        return o;
    }


    public JSONObject get(String urlLink){
        HttpURLConnection connection =null;
        StringBuilder sb=null;

        try {
            Log.d("LINK",urlLink);
            URL url=new URL(urlLink);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(15001);
            connection.setConnectTimeout(15001);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setDoOutput(true);
            connection.connect();


            BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

            char[] buffer = new char[1024];

            String jsonString = new String();

            sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            json=new JSONObject(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public Movie getMovieImagesAndVideos(JSONObject jObj,Movie m){
        JSONArray videoArr=null,imageArr=null;
        try {
            videoArr=jObj.getJSONObject("videos").getJSONArray("results");
            imageArr=jObj.getJSONObject("images").getJSONArray("backdrops");

            for (int i = 0; i <videoArr.length() ; i++) {
                if(!videoArr.isNull(0))
                m.setVideoList((ArrayList< Video>) getObjectFromArray(new Video(),videoArr,"key","name","site","type"));
            }
            for (int i = 0; i <imageArr.length() ; i++) {
                if(!imageArr.isNull(0))
                    m.setImageList((ArrayList<Image>) getObjectFromArray(new Image(),imageArr,"file_path"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return m;
    }

    public ArrayList<Person> getPersonSearch(JSONObject jObjRaw){
        ArrayList<Person> personList=null;
        String arr[]=new String[JsonTags_Person.values().length];
        for (int i = 0; i <arr.length ; i++) {
            arr[i]=JsonTags_Person.values()[i].toString();
        }

        try {
            personList=(ArrayList<Person>) getObjectFromArray(new Person(),jObjRaw.getJSONArray("results"),arr);
            for (int i = 0; i <personList.size() ; i++) {
                Log.d("MOV",personList.get(i).getName());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return personList;
    }

    public Person getPersonMovies(JSONObject jObj){
        Person p=new Person();
        try {
            String []arr=new String[JsonTags_Movie.values().length];
            for (int i = 0; i <arr.length ; i++) {
                arr[i]=JsonTags_Movie.values()[i].toString();
            }
           p.setMovieList((ArrayList<Movie>) getObjectFromArray(new Movie(),jObj.getJSONArray(JsonTags_Person.cast.toString()),arr));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return p;
    }

    public Person getPersonDetailFromObject(JSONObject jObj){
        Person p=null;
        try {
            if(jObj!=null){
                p=new Person();
                JsonTags_Person[] arr=JsonTags_Person.values();
                for (int i = 0; i <arr.length; i++) {
                    if (arr[i] != null && !arr[i].toString().isEmpty()) {
                        Class<?> classMovie = Class.forName("com.movieapp.melihakbulut.movieapp.Entity.Person");
                        if(!jObj.isNull(arr[i].toString()))
                            setProperty(p,arr[i].toString(),jObj.getString(arr[i].toString()));
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return p;
    }


    public List<Genre> getGenreList(JSONObject jObjRaw){
        List<Genre> list=new ArrayList<Genre>();
        try {
            JSONArray genres=jObjRaw.getJSONArray("genres");
            list= (List<Genre>) getObjectFromArray(new Genre(),genres,"id","name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Movie> getMovieListAccToGenre(JSONObject jObj){
        List<Movie> movieList=new ArrayList<Movie>();
        Movie movie=null;
        try {
            JSONArray results=jObj.getJSONArray("results");
            for (int i = 0; i <20 ; i++) {
                movie=new Movie();
                JSONObject jsonObject = results.getJSONObject(i);
                movie.setId(jsonObject.getString("id"));
                movie.setPoster_path(jsonObject.getString("poster_path"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setOriginal_title(jsonObject.getString("original_title"));
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public ArrayList<Movie> getMovieSearch(JSONObject jObjRaw){
        ArrayList<Movie> movieList=null;
        String [] arr=new String[JsonTags_Movie.values().length];
        for (int i = 0; i <arr.length ; i++) {
            arr[i]= JsonTags_Movie.values()[i].toString();
        }
        try {
            movieList=(ArrayList<Movie>) getObjectFromArray(new Movie(),jObjRaw.getJSONArray("results"),arr);
            for (int i = 0; i <movieList.size() ; i++) {
                Log.d("MOV",movieList.get(i).getTitle());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public Movie getMovieDetailFromObject(JSONObject jObj){
        Movie m=null;
        try {
            if(jObj!=null) {
                m = new Movie();
                JsonTags_Movie[] arr= JsonTags_Movie.values();
                for (int i = 0; i <arr.length ; i++) {
                    if (arr[i] != null && !arr[i].toString().isEmpty()) {
                        if(arr[i].equals(JsonTags_Movie.genres))
                            break;

                        Class<?> classMovie = Class.forName("com.movieapp.melihakbulut.movieapp.Entity.Movie");
                        if(!jObj.isNull(arr[i].toString()))
                        setProperty(m,arr[i].toString(),jObj.getString(arr[i].toString()));
                        Log.d("MOVIE - "+arr[i].toString().toUpperCase(),jObj.getString(arr[i].toString()));
                    }
                }

                JSONArray jsonArray = jObj.getJSONArray(JsonTags_Movie.genres.toString());

                if(!jObj.isNull(JsonTags_Movie.genres.toString()))
                m.setGenres((ArrayList<Genre>) getObjectFromArray(new Genre(),jObj.getJSONArray(JsonTags_Movie.genres.toString()),"id","name"));

                m.setProduction_companies(getStringArrayFromJArray(jObj.getJSONArray(JsonTags_Movie.production_companies.toString()),"name"));
                m.setProduction_countries(getStringArrayFromJArray(jObj.getJSONArray(JsonTags_Movie.production_countries.toString()),"name"));
                m.setSpoken_languages(getStringArrayFromJArray(jObj.getJSONArray(JsonTags_Movie.spoken_languages.toString()),"name"));

                    m=getMovieImagesAndVideos(jObj,m);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return m;
    }

    public ArrayList<String> getStringArrayFromJArray(JSONArray jArr,String paramater){
        ArrayList<String> list=new ArrayList<String>();

            try {
                for (int i = 0; i <jArr.length() ; i++) {
                    JSONObject jObj = jArr.getJSONObject(i);
                    if(!jObj.isNull(paramater))
                        list.add(jObj.getString(paramater));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return list;
    }

    public ArrayList<?> getObjectFromArray(Object object,JSONArray jArr,String... params){
        Class<?> classG = object.getClass();
        ArrayList<Object> arraylist= new ArrayList<Object>();
        try {
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject jObj = jArr.getJSONObject(i);
                object=classG.newInstance();
                if(params[0]!=""){
                    for (int j = 0; j <params.length ; j++) {
                        if(!jObj.isNull(params[j]))
                            setProperty(object,params[j],jObj.getString(params[j]));
                    }
                }
                arraylist.add(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return arraylist;
    }

    public boolean setProperty(Object object, String fieldName, Object fieldValue) {
        Class<?> classG = object.getClass();
        while (classG != null) {
            try {
                Field field = classG.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                classG = classG.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    public <V> V getProperty(Object object, String fieldName) {
        Class<?> classG = object.getClass();
        while (classG != null) {
            try {
                Field field = classG.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (V) field.get(object);
            } catch (NoSuchFieldException e) {
                classG = classG.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }
    }
