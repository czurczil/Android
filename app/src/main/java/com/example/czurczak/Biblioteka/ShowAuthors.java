package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by czurczak on 31.10.2016.
 */

public class ShowAuthors extends AppCompatActivity {
    String sort;
    String autor;
    Cursor cursor;
    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_authors_listview);

        buttonClick.setDuration(80);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        Bundle b = getIntent().getExtras();
        if(b != null){
            autor = b.getString("Author");

            cursor = db.ShowSelectedAuthor(autor);
        }
        else {
            cursor = db.ShowAllAuthors(null);
        }
        ListViewLayout(cursor);
        db.close();
    }

    public SimpleCursorAdapter ListViewLayout(Cursor cursor){
        final DatabaseAccess db = DatabaseAccess.getInstance(this);

        String[] fromColNames = new String[] {
                db.KEY_AUTHOR,
                db.TA_PHOTO
        };
        int[] toViewIDs = new int[] {
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

        ListView myList = (ListView)findViewById(R.id.authors_listview);
        myList.setAdapter(myCursorAdapter);

        return myCursorAdapter;
    }

    public void onClickAuthors(View view){
        view.startAnimation(buttonClick);
        Intent intent = new Intent (getApplicationContext(), ShowAuthorsDetails.class);
        String autor = ((TextView)(view.findViewById(R.id.authors_name))).getText().toString();
        intent.putExtra("Author", autor);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_authors, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DatabaseAccess db = DatabaseAccess.getInstance(getApplicationContext());
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.author_id:
                db.open();
                sort = " ORDER BY " + db.TABLE_AUTHOR + "." + db.TB_ID;
                if(autor != null) cursor = db.ShowSelectedAuthor(autor);
                else cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.first_name_asc:
                db.open();
                sort = " ORDER BY " + db.TABLE_AUTHOR + "." + db.TA_FIRST_NAME + " ASC";
                if(autor != null) cursor = db.ShowSelectedAuthor(autor);
                else cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.first_name_desc:
                db.open();
                sort = " ORDER BY "+ db.TABLE_AUTHOR + "." + db.TA_FIRST_NAME + " DESC";
                if(autor != null) cursor = db.ShowSelectedAuthor(autor);
                else cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.last_name_asc:
                db.open();
                sort = " ORDER BY " + db.TABLE_AUTHOR + "." + db.TA_LAST_NAME + " ASC";
                cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.last_name_desc:
                db.open();
                sort = " ORDER BY "+ db.TABLE_AUTHOR + "." + db.TA_LAST_NAME + " DESC";
                if(autor != null) cursor = db.ShowSelectedAuthor(autor);
                else cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
