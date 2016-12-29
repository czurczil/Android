package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import static android.R.attr.value;

/**
 * Created by czurczak on 14.09.2016.
 */
public class ShowBooks extends AppCompatActivity {
    String sort;
    Cursor cursor;
    String spinner = null;
    String phrase = null;
    String table = null;

    int isFavorite;
    int isOnMyShelf;
    int isOnWishList;
    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_books_listview);

        buttonClick.setDuration(80);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);


        Bundle b = getIntent().getExtras();
        if (b != null) {
            spinner = b.getString("Spinner"); //get spinner and
            phrase = b.getString("Phrase");   //searching phrase value from Search class

            table = b.getString("Table");
        }


        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        if (spinner != null && phrase != null && table == null) {

            cursor = db.ShowSelectedBooks(spinner, phrase, null);

            ListViewLayout(cursor);

            db.RecordCount(getApplicationContext(), cursor);
        } else if (table != null) {
            cursor = db.ShowSelectedBooks(table, null);

            ListViewLayout(cursor);

            db.RecordCount(getApplicationContext(), cursor);
        } else {
            cursor = db.ShowAllBooks(null);
            ListViewLayout(cursor);
        }
        db.close();
    }

    public void ListViewLayout(Cursor cursor) {

        final DatabaseAccess db = DatabaseAccess.getInstance(this);
        //db.open();

        //mapping from cursor to view fields
        String[] fromColNames = new String[]{
                db.TB_TITLE,
                db.KEY_AUTHOR,
                db.TB_COVER,
                db.TB_FAVORITE,
                db.TB_ON_SHELF,
                db.TB_WISHES
        };
        int[] toViewIDs = new int[]{
                R.id.tvTitle,
                R.id.tvAuthor,
                R.id.imgCover,
                R.id.imageFav,
                R.id.imageRead,
                R.id.imageToRead
        };

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.show_books,
                cursor,
                fromColNames,
                toViewIDs
        );
        if (cursor != null && cursor.moveToFirst()) {
            myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int colIndex) {
                    //String name = cursor.getString(colIndex);
                    boolean binded = false;
                    if(colIndex == cursor.getColumnIndex(db.TB_COVER)){
                        ImageView cover = (ImageView)view.findViewById(R.id.imgCover);
                        cover.setImageBitmap(db.GetImage(cursor, 0));
                        binded = true;
                    }
                    if(colIndex == cursor.getColumnIndex(db.TB_FAVORITE)){
                        ImageView fav_button = (ImageView)view.findViewById(R.id.imageFav);
                        isFavorite = cursor.getInt(cursor.getColumnIndex(db.TB_FAVORITE));
                        if(isFavorite == 1)
                            fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_on);
                        else fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_off);
                        binded = true;
                    }
                    if(colIndex == cursor.getColumnIndex(db.TB_ON_SHELF)){
                        ImageView on_my_shelf_button = (ImageView)view.findViewById(R.id.imageRead);
                        isOnMyShelf = cursor.getInt(cursor.getColumnIndex(db.TB_ON_SHELF));
                        if(isOnMyShelf == 1)
                            on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_on);
                        else on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_off);
                        binded = true;
                    }
                    if(colIndex == cursor.getColumnIndex(db.TB_WISHES)){
                        ImageView on_wish_list_button = (ImageView)view.findViewById(R.id.imageToRead);
                        isOnWishList = cursor.getInt(cursor.getColumnIndex(db.TB_WISHES));
                        if(isOnWishList == 1)
                            on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_on);
                        else on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_off);
                        binded = true;
                    }
                    return binded;
                }
            });
        }

        ListView myList = (ListView) findViewById(R.id.books_listview);
        myList.setAdapter(myCursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_books, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.book_id:
                db.open();
                sort = " ORDER BY " + db.TABLE_BOOKS + "." + db.TB_ID;
                if (spinner != null && phrase != null && table == null) cursor = db.ShowSelectedBooks(spinner, phrase, sort);
                else if(table != null) cursor = db.ShowSelectedBooks(table, sort);
                else cursor = db.ShowAllBooks(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.title_asc:
                db.open();
                sort = " ORDER BY " + db.TABLE_BOOKS + "." + db.TB_TITLE + " ASC";
                if (spinner != null && phrase != null && table == null) cursor = db.ShowSelectedBooks(spinner, phrase, sort);
                else if(table != null) cursor = db.ShowSelectedBooks(table, sort);
                else cursor = db.ShowAllBooks(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.title_desc:
                db.open();
                sort = " ORDER BY " + db.TABLE_BOOKS + "." + db.TB_TITLE + " DESC";
                if (spinner != null && phrase != null && table == null) cursor = db.ShowSelectedBooks(spinner, phrase, sort);
                else if(table != null) cursor = db.ShowSelectedBooks(table, sort);
                else cursor = db.ShowAllBooks(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.year_up:
                db.open();
                sort = " ORDER BY "+ db.TABLE_BOOKS + "." + db.TB_YEAR + " ASC";
                if (spinner != null && phrase != null && table == null) cursor = db.ShowSelectedBooks(spinner, phrase, sort);
                else if(table != null) cursor = db.ShowSelectedBooks(table, sort);
                else cursor = db.ShowAllBooks(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.year_down:
                db.open();
                sort = " ORDER BY "+ db.TABLE_BOOKS + "." + db.TB_YEAR + " DESC";
                if (spinner != null && phrase != null && table == null) cursor = db.ShowSelectedBooks(spinner, phrase, sort);
                else if(table != null) cursor = db.ShowSelectedBooks(table, sort);
                else cursor = db.ShowAllBooks(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickBooks(View view) {
        view.startAnimation(buttonClick);
        Intent intent = new Intent(getApplicationContext(), ShowBooksDetails.class);
        String title = ((TextView) (view.findViewById(R.id.tvTitle))).getText().toString();
        intent.putExtra("Title", title);
        startActivity(intent);
    }
}
