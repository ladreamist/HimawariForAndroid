package com.dreamsforall.satelliteproject.himawariandroid;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by dreamist on 9/5/16.
 */
public class ImageDownloader {
    DownloadManager dl_manager;
    Context context;

    ImageView image_view;

    Bitmap image;
    Bitmap[][] images;
    String formatURL, url;
    static int level = 2;
    static int successes = 0;
    int year, month, day, hour, min;

    public ImageDownloader(Context con){
        context = con;
        images = new Bitmap[level][level];
        image = Bitmap.createBitmap(300,300, Bitmap.Config.RGB_565);
        url = "http://himawari8-dl.nict.go.jp/himawari8/img/D531106/2d/550/2016/01/01/020000_0_0.png";
        formatURL = "http://himawari8.nict.go.jp/img/D531106/%dd/550/%02d/%02d/%02d/%02d%02d00_%d_%d.png";

    }

    public void loadInto(ImageView img_view){
        image_view = img_view; //target to work on fitting images into

        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.MINUTE, -10);

        year    = cal.get(Calendar.YEAR);
        month   = cal.get(Calendar.MONTH);
        day     = cal.get(Calendar.DATE);
        hour    = cal.get(Calendar.HOUR);
        min     = cal.get(Calendar.MINUTE);

        min = min - (min % 10);


        //Log.d("Minutes:", "" + min);
        //Log.d("URL:", String.format(formatURL, level, year, month, day,hour, min, x, y));

        for(int x = 0; x < level; x++ ){
            for(int y = 0; y < level; y++){
                Log.d("[+]", String.format(formatURL, level, year, month, day, hour, min, x, y));

                Picasso.with(context)
                        .load(String.format(formatURL, level, year, month, day, hour, min, x, y))
                        .into( new ImageTarget(x,y,this));
            }
        }
    }

    public Bitmap getImage(){
        return image;
    }

    public Bitmap makeBitmapRow(Bitmap l, Bitmap r){
        Bitmap row = null;

        int width = l.getWidth()+r.getWidth();
        int height = Math.max(l.getHeight(), r.getHeight());

        row = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas comboImage = new Canvas(row);
        comboImage.drawBitmap(l, 0f, 0f, null);
        comboImage.drawBitmap(r, l.getWidth(), 0f, null);

        return row;
    }

    public Bitmap makeBitmapColumn(Bitmap up, Bitmap down){
        Bitmap column = null;

        int width = Math.max(up.getWidth(), down.getWidth());
        int height = up.getHeight() + down.getHeight();

        column = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas comboImage = new Canvas(column);
        comboImage.drawBitmap(up,  0f, 0f, null);
        comboImage.drawBitmap(down, 0f, up.getHeight(), null);
        return column;
    }

    public boolean setTile(int x, int y, Bitmap bmp){
        try{
            images[x][y] = bmp;
            success();
            return true;
        } catch(Exception e){
            Log.e("[-]", e.getMessage());
            return false;
        }
    }

    public void success(){
        successes++;
        Log.d("[+]", "Successes:" + successes);

        if (successes >= level * level){
            Bitmap rows[] = new Bitmap[level];
            Bitmap object = makeBitmapColumn(
                    makeBitmapRow(images[0][0], images[1][0]),
                    makeBitmapRow(images[0][1], images[1][1])
            );
            image = object;
            image_view.setImageBitmap( object );
            successes = 0;
        }

    }


}
