package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

/**
 * Created by czurczak on 31.10.2016.
 */

public class ShowAuthorsDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_author_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        final DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        Bundle b = getIntent().getExtras();
        Cursor cursor = db.ShowSelectedAuthor(b.getString("Author"));

        TextView name = (TextView) findViewById(R.id.tv_Name);
        TextView birth_date = (TextView) findViewById(R.id.Tv_birth_date);
        TextView sex = (TextView) findViewById(R.id.Tv_sex);
        TextView birth_place = (TextView) findViewById(R.id.Tv_birth_place);
        TextView bio = (TextView) findViewById(R.id.Tv_bio);
        final ImageView photo = (ImageView)findViewById(R.id.iv_photo);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAuthorsDetails.this, FullScreenImage.class);

                Bitmap bmp = ((BitmapDrawable)photo.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("Image", byteArray);
                startActivity(intent);
            }
        });

        cursor.moveToFirst();
            name.setText(cursor.getString(1));
            birth_date.setText(cursor.getString(2));
            sex.setText(cursor.getString(3));
            birth_place.setText(cursor.getString(4));
            bio.setText(cursor.getString(5));
            photo.setImageBitmap(db.GetImage(cursor, 1));
        cursor.close();
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
