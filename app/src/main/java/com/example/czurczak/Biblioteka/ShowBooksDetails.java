package com.example.czurczak.Biblioteka;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by czurczak on 04.11.2016.
 */

public class ShowBooksDetails extends AppCompatActivity {
    final Database db = new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_book_details);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();
        final Cursor cursor = db.ShowSelectedBooks(b.getString("Title"));

        TextView id = (TextView)findViewById(R.id.tvID);
        TextView name = (TextView) findViewById(R.id.tvAuthor);
        TextView title = (TextView) findViewById(R.id.tvTitle);
        TextView year = (TextView) findViewById(R.id.tvYear);
        TextView cycle = (TextView) findViewById(R.id.tvCycle);
        TextView genre = (TextView) findViewById(R.id.tvGenre);
        ImageView cover = (ImageView)findViewById(R.id.imgCover);
        TextView desc = (TextView)findViewById(R.id.tvDesc);

        ImageButton fav_button = (ImageButton) findViewById(R.id.image_button);
        fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFavoriteButton(cursor);
            }
        });


        cursor.moveToFirst();
            id.setText(cursor.getString(0));
            name.setText(cursor.getString(1));
            title.setText(cursor.getString(2));
            year.setText(cursor.getString(3));
            desc.setText(cursor.getString(4));
            cycle.setText(cursor.getString(5));
            cover.setImageBitmap(db.GetImage(cursor, 0));
            genre.setText(cursor.getString(7));
        db.close();
    }

        public void SetFavoriteButton(Cursor cursor){
            cursor.moveToFirst();
            ImageButton favorite_button = (ImageButton) findViewById(R.id.image_button);
            int isFavorite = cursor.getInt(cursor.getColumnIndex(db.TB_FAVORITE));
            if(isFavorite == 1)
                favorite_button.setImageResource(android.R.drawable.btn_star_big_on);
            else favorite_button.setImageResource(android.R.drawable.btn_star_big_off);
    }
}
