package com.example.czurczak.Biblioteka;

import android.database.Cursor;
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

/**
 * Created by czurczak on 31.10.2016.
 */

public class ShowAuthorsDetails extends AppCompatActivity {
    final Database db = new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_author_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();
        Cursor cursor = db.ShowSelectedAuthor(b.getString("Author"));

        TextView id = (TextView)findViewById(R.id.tv_id);
        TextView name = (TextView) findViewById(R.id.tv_Name);
        TextView birth_date = (TextView) findViewById(R.id.Tv_birth_date);
        TextView sex = (TextView) findViewById(R.id.Tv_sex);
        TextView birth_place = (TextView) findViewById(R.id.Tv_birth_place);
        TextView bio = (TextView) findViewById(R.id.Tv_bio);
        ImageView photo = (ImageView)findViewById(R.id.iv_photo);

        cursor.moveToFirst();
            id.setText(cursor.getString(0));
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
