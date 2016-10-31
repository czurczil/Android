package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by czurczak on 31.10.2016.
 */

public class ShowAuthors extends AppCompatActivity {
    final Database db = new Database(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_authors_listview);
        ListView myList = (ListView)findViewById(R.id.authors_listview);
        getSupportActionBar().setHomeButtonEnabled(true);

        myList.setAdapter(ListViewLayout(db.ShowAllAuthors()));
        db.close();

    }
    public void onClick(View view){
        Intent intent = new Intent (getApplicationContext(), ShowAuthorsDetails.class);
        String autor = ((TextView)(view.findViewById(R.id.authors_name))).getText().toString();
        intent.putExtra("Author", autor);
        startActivity(intent);
    }

    public SimpleCursorAdapter ListViewLayout(Cursor cursor){

        String[] fromColNames = new String[] {
                db.TA_ID,
                db.KEY_AUTHOR
        };
        int[] toViewIDs = new int[] {
                R.id.authors_id,
                R.id.authors_name
        };

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.show_authors,
                cursor,
                fromColNames,
                toViewIDs
        );

        return myCursorAdapter;
    }

}
