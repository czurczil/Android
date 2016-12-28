package com.example.czurczak.Biblioteka;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by czurczak on 20.12.2016.
 */

public class FullScreenImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_image);

        ImageView Image = (ImageView)findViewById(R.id.fullImageView);
        ImageButton exit = (ImageButton)findViewById(R.id.closebtn);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        byte[] byteArray = getIntent().getExtras().getByteArray("Image");

        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        Image.setImageBitmap(bmp);
    }
}
