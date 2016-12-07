package com.example.czurczak.Biblioteka;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
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
            DatabaseAccess db = DatabaseAccess.getInstance(this);
            db.open();
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndex(db.TB_ID));
            if(isFavorite == 1) {
                db.UpdateFavorite(id, 0);
                Toast.makeText(getApplicationContext(), "Usunięto z ulubionych" , Toast.LENGTH_SHORT).show();
                fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_off);
                isFavorite = 0;
            }
            else {
                db.UpdateFavorite(id, 1);
                Toast.makeText(getApplicationContext(), "Dodano do ulubionych" , Toast.LENGTH_SHORT).show();
                fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_on);
                isFavorite = 1;
            }
    }

    public void SetMyShelfButton(Cursor cursor){
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(db.TB_ID));
        if(isOnMyShelf == 1) {
            db.UpdateMyShelf(id, 0);
            Toast.makeText(getApplicationContext(), "Usunięto z mojej półki" , Toast.LENGTH_SHORT).show();
            on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_off);
            isOnMyShelf = 0;
        }
        else {
            db.UpdateMyShelf(id, 1);
            Toast.makeText(getApplicationContext(), "Dodano do mojej półki" , Toast.LENGTH_SHORT).show();
            on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_on);
            isOnMyShelf = 1;
        }
    }

    public void SetWishListButton(Cursor cursor){
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex(db.TB_ID));
        if(isOnWishList == 1) {
            db.UpdateWishList(id, 0);
            Toast.makeText(getApplicationContext(), "Usunięto z mojej listy do przeczytania" , Toast.LENGTH_SHORT).show();
            on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_off);
            isOnWishList = 0;
        }
        else {
            db.UpdateWishList(id, 1);
            Toast.makeText(getApplicationContext(), "Dodano do mojej listy do przeczytania" , Toast.LENGTH_SHORT).show();
            on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_on);
            isOnWishList = 1;
        }
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

    public void onClickCycle(View view){
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        String cycle = ((TextView)(view.findViewById(R.id.tvCycle))).getText().toString();
        if(cycle.length() != 0) {
            Intent intent = new Intent(getApplicationContext(), ShowBooks.class);
            String tb = db.TB_CYKLE;
            intent.putExtra("Phrase", cycle);
            intent.putExtra("Spinner", tb);
            startActivity(intent);
        }
    }

    public void onClickGenre(View view){
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        Intent intent = new Intent (getApplicationContext(), ShowBooks.class);
        String genre = ((TextView)(view.findViewById(R.id.tvGenre))).getText().toString();
        String tb = db.TG_GENRE;
        intent.putExtra("Phrase", genre);
        intent.putExtra("Spinner", tb);
        startActivity(intent);
    }

    public void onClickAuthor(View view) {
        Intent intent = new Intent(getApplicationContext(), ShowAuthors.class);
        String autor = ((TextView) (view.findViewById(R.id.tvAuthor))).getText().toString();
        intent.putExtra("Author", autor);
        startActivity(intent);
    }
}
