package com.example.czurczak.Biblioteka;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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
        ListView myList = (ListView)findViewById(R.id.books_listview);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle b = getIntent().getExtras();
        if(b != null){
            String spinner = b.getString("Spinner"); //get spinner and
            String phrase = b.getString("Phrase");   //searching phrase value from Search class
            Cursor cursor = db.ShowSelectedBooks(spinner, phrase);
            myList.setAdapter(ListViewLayout(cursor));
            db.RecordCount(getApplicationContext(), cursor);
            db.close();
        }
        else  {
            myList.setAdapter(ListViewLayout(db.ShowAllBooks()));
            db.close();
        }
    }

    public SimpleCursorAdapter ListViewLayout(Cursor cursor){

        //mapping from cursor to view fields
        String[] fromColNames = new String[] {
                db.TB_ID,
                db.TB_TITLE,
                db.KEY_AUTHOR,
                db.TB_YEAR,
                db.TB_DESC,
                db.TB_CYKLE,
                db.TB_COVER,
                db.TG_GENRE

        };
        int[] toViewIDs = new int[] {
                R.id.tvID,
                R.id.tvTitle,
                R.id.tvAuthor,
                R.id.tvYear,
                R.id.tvDesc,
                R.id.tvCycle,
                R.id.imgCover,
                R.id.tvGenre
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
                        ((ImageView) view).setImageBitmap(db.GetImage(cursor));
                        return true;
                    }
                    return false;
                }
            });
        }
        return myCursorAdapter;
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
