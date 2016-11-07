package com.example.czurczak.Biblioteka;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by czurczak on 04.11.2016.
 */

public class ShowBooksDetails extends AppCompatActivity {
    final Database db = new Database(this);
    int isFavorite;
    int isOnMyShelf;
    int isOnWishList;
    ImageButton fav_button;
    ImageButton on_my_shelf_button;
    ImageButton on_wish_list_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_book_details);
//        getSupportActionBar().setHomeButtonEnabled(true);

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


        cursor.moveToFirst();
        fav_button = (ImageButton) findViewById(R.id.favorite_button);
        isFavorite = cursor.getInt(cursor.getColumnIndex(db.TB_FAVORITE));
        if(isFavorite == 1)
            fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_on);
        else fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_off);

        fav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetFavoriteButton(cursor);
            }
        });


        on_my_shelf_button = (ImageButton) findViewById(R.id.on_my_shelf_button);
        isOnMyShelf = cursor.getInt(cursor.getColumnIndex(db.TB_ON_SHELF));
        if(isOnMyShelf == 1)
            on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_on);
        else on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_off);

        on_my_shelf_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetMyShelfButton(cursor);
            }
        });


        on_wish_list_button = (ImageButton) findViewById(R.id.on_wish_list_button);
        isOnWishList = cursor.getInt(cursor.getColumnIndex(db.TB_WISHES));
        if(isOnWishList == 1)
            on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_on);
        else on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_off);

        on_wish_list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetWishListButton(cursor);
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
            int id = cursor.getInt(cursor.getColumnIndex(db.TB_ID));
            if(isFavorite == 1) {
                db.UpdateFavorite(id, 0);
                Toast.makeText(getApplicationContext(), "Usunięto z ulubionych" , Toast.LENGTH_SHORT).show();
            }
            else {
                db.UpdateFavorite(id, 1);
                Toast.makeText(getApplicationContext(), "Dodano do ulubionych" , Toast.LENGTH_SHORT).show();
            }
        RestartActivity();
    }

    public void SetMyShelfButton(Cursor cursor){
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(db.TB_ID));
        if(isOnMyShelf == 1) {
            db.UpdateMyShelf(id, 0);
            Toast.makeText(getApplicationContext(), "Usunięto z mojej półki" , Toast.LENGTH_SHORT).show();
        }
        else {
            db.UpdateMyShelf(id, 1);
            Toast.makeText(getApplicationContext(), "Dodano do mojej półki" , Toast.LENGTH_SHORT).show();
        }
        RestartActivity();
    }

    public void SetWishListButton(Cursor cursor){
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(db.TB_ID));
        if(isOnWishList == 1) {
            db.UpdateWishList(id, 0);
            Toast.makeText(getApplicationContext(), "Usunięto z mojej listy do przeczytania" , Toast.LENGTH_SHORT).show();
        }
        else {
            db.UpdateWishList(id, 1);
            Toast.makeText(getApplicationContext(), "Dodano do mojej listy do przeczytania" , Toast.LENGTH_SHORT).show();
        }
        RestartActivity();
    }

    public void RestartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
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
