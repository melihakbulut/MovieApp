package com.movieapp.melihakbulut.movieapp.Task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by melih.akbulut on 12.04.2017.
 */
public class DownloadImageTask extends AsyncTask<URL[],Void,Bitmap[]> {
    @Override
    protected Bitmap[] doInBackground(URL[]... params) {
        return downloadImage(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmap) {
        super.onPostExecute(bitmap);
    }

    public Bitmap[] downloadImage(URL[] url){
        Bitmap[] bitmap = new Bitmap[url.length];
        try {
            for (int i = 0; i <bitmap.length ; i++) {
                if(url[i]!=null)
                bitmap[i]=BitmapFactory.decodeStream(url[i].openConnection().getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


}
