package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by czurczak on 14.09.2016.
 */
public class ShowBooks extends AppCompatActivity {
    final Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_books_listview);
        getSupportActionBar().setHomeButtonEnabled(true);

        String spinner = null;
        String phrase = null;
        String table = null;
        Bundle b = getIntent().getExtras();
        if(b != null){
            spinner = b.getString("Spinner"); //get spinner and
            phrase = b.getString("Phrase");   //searching phrase value from Search class

            table = b.getString("Table");
        }


        if(spinner != null && phrase != null && table == null){

            Cursor cursor = db.ShowSelectedBooks(spinner, phrase);

            ListViewLayout(cursor);

            db.RecordCount(getApplicationContext(), cursor);
            db.close();
        }
        else if(table != null){
            Cursor cursor = db.ShowSelectedBooks(table);

            ListViewLayout(cursor);

            db.RecordCount(getApplicationContext(), cursor);
            db.close();
        }
        else  {
            Cursor cursor = db.ShowAllBooks();
            ListViewLayout(cursor);
            db.close();
        }
    }
    public void onClick(View view){
        Intent intent = new Intent (getApplicationContext(), ShowBooksDetails.class);
        String title = ((TextView)(view.findViewById(R.id.tvTitle))).getText().toString();
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    public void ListViewLayout(Cursor cursor){

        //mapping from cursor to view fields
        String[] fromColNames = new String[] {
                db.TB_ID,
                db.TB_TITLE,
                db.KEY_AUTHOR,
                db.TB_COVER
        };
        int[] toViewIDs = new int[] {
                R.id.tvID,
                R.id.tvTitle,
                R.id.tvAuthor,
                R.id.imgCover
        };

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.show_books,
                cursor,
                fromColNames,
                toViewIDs
        );
        if(cursor != null && cursor.moveToFirst()) {
            myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int colIndex) {
                    //String name = cursor.getString(colIndex);

                    if (view.getId() == R.id.imgCover) {
                    /*ImageView IV=(ImageView) view;                                                            Commented section
                    String pack =  getApplicationContext().getPackageName();                                    set cover src to drawable
                    int resID = getApplicationContext().getResources().getIdentifier(name, "drawable", pack);
                    IV.setImageDrawable(getApplicationContext().getResources().getDrawable(resID));*/
                        ((ImageView) view).setImageBitmap(db.GetImage(cursor, 0));
                        return true;
                    }
                    return false;
                }
            });
        }

        ListView myList = (ListView)findViewById(R.id.books_listview);
        myList.setAdapter(myCursorAdapter);
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
