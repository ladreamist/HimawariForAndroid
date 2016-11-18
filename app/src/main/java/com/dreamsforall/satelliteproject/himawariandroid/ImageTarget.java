package com.dreamsforall.satelliteproject.himawariandroid;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by dreamist on 9/5/16.
 */
public class ImageTarget implements Target {
    int x, y;
    ImageDownloader image_dl;

    public ImageTarget(int x_var, int y_var, ImageDownloader img_dl){
        super();
        x = x_var;
        y = y_var;
        image_dl = img_dl;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        image_dl.setTile( x, y, bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable drawable) {
        Log.e("[-]", "Image load failed... " + x + "," + y);
    }

    @Override
    public void onPrepareLoad(Drawable drawable) {

    }



}
