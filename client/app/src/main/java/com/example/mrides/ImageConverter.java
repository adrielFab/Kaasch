package com.example.mrides;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;


public class ImageConverter extends AsyncTask<String, Void, Bitmap>{

    ImageView imageView;

    public ImageConverter(ImageView bmImage) {
        this.imageView = bmImage;
    }

    /**
     * Method that takes in a valid google image url and converts into an actual
     * image which is to be displayed in the application.
     * @param urls, Photo Urls strings which are to be converted into actual images
     * @return
     */
    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("ImageConverter", e.getMessage());
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {

        imageView.setImageBitmap(Bitmap.createScaledBitmap(result, 350, 350, false));
    }
}
