package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by czurczak on 31.10.2016.
 */

public class ShowAuthors extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_authors_listview);
        ListView myList = (ListView)findViewById(R.id.authors_listview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        Bundle b = getIntent().getExtras();
        if(b != null){
            String autor = b.getString("Author");

            myList.setAdapter(ListViewLayout(db.ShowSelectedAuthor(autor)));

            db.close();
        }
        else {
            myList.setAdapter(ListViewLayout(db.ShowAllAuthors()));
            db.close();
        }
    }
    public void onClickAuthors(View view){
        Intent intent = new Intent (getApplicationContext(), ShowAuthorsDetails.class);
        String autor = ((TextView)(view.findViewById(R.id.authors_name))).getText().toString();
        intent.putExtra("Author", autor);
        startActivity(intent);
    }

    public SimpleCursorAdapter ListViewLayout(Cursor cursor){
        final DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();

        String[] fromColNames = new String[] {
                db.TA_ID,
                db.KEY_AUTHOR,
                db.TA_PHOTO
        };
        int[] toViewIDs = new int[] {
                R.id.authors_id,
                R.id.authors_name,
                R.id.imageView2
        };

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.show_authors,
                cursor,
                fromColNames,
                toViewIDs
        );
        if(cursor != null && cursor.moveToFirst()) {
            myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int colIndex) {
                    //String name = cursor.getString(colIndex);

                    if (view.getId() == R.id.imageView2) {
                    /*ImageView IV=(ImageView) view;                                                            Commented section
                    String pack =  getApplicationContext().getPackageName();                                    set cover src to drawable
                    int resID = getApplicationContext().getResources().getIdentifier(name, "drawable", pack);
                    IV.setImageDrawable(getApplicationContext().getResources().getDrawable(resID));*/
                        ((ImageView) view).setImageBitmap(db.GetImage(cursor, 1));
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
