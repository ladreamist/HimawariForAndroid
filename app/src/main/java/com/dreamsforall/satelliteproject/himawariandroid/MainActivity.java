package com.dreamsforall.satelliteproject.himawariandroid;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    Button button;
    Button setWallpaperButton;
    ImageDownloader img_dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageView);
        img_dl = new ImageDownloader(this);

        button = (Button) findViewById(R.id.button);
        setWallpaperButton = (Button) findViewById(R.id.setWallpaper);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.d("[+]", "Button clicked!");
                Context c = getBaseContext();
                String m = "Please wait while your image is gathered.";

                Toast.makeText(MainActivity.this, m, Toast.LENGTH_SHORT).show();
                img_dl.loadInto(image);


                //image.setImageBitmap(img_dl.getImage());
            }
        });

        setWallpaperButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Bitmap img = img_dl.getImage();
                Log.d("[+]", "Ready to try setting wallpaper");

                Context c = getBaseContext();
                try{
                    WallpaperManager w_manager = WallpaperManager.getInstance(c);
                    w_manager.setBitmap(img);
                    Toast.makeText(MainActivity.this,
                            "Your wallpaper is set.", Toast.LENGTH_SHORT).show();
                } catch(Exception e){
                    Log.e("[-]", e.getMessage());
                }

            }
        });



    }

    public void loadImage(View view){
        //image.setImageBitmap(img_dl.getImage());
        img_dl.loadInto(image);
    }
}
