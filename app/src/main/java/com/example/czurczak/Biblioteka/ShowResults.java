package com.example.czurczak.Biblioteka;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by czurczak on 14.09.2016.
 */
public class ShowResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_listview);
        ListView myList = (ListView)findViewById(R.id.db_listview);
        myList.setAdapter(ListViewLayout());

    }

    private SimpleCursorAdapter ListViewLayout(){
        final Database db = new Database(this);

        Cursor cursor = db.ShowAll();
        //mapping from cursor to view fields
        String[] fromColNames = new String[] {
                db.KEY_ID,
                db.KEY_TITLE,
                db.KEY_AUTHOR,
                db.KEY_YEAR,
                db.KEY_DESC,
                db.KEY_CYKLE,
                db.KEY_COVER,
                db.KEY_GENRE
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
                R.layout.show_all,
                cursor,
                fromColNames,
                toViewIDs
        );
        myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int colIndex) {
                //String name = cursor.getString(colIndex);
                if (view.getId() == R.id.imgCover ) {
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

        return myCursorAdapter;
    }
}
