package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by czurczak on 22.11.2016.
 */

public class GenresFragment extends Fragment {
    View v;
    String sort;
    Cursor cursor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.genres_fragment, container, false);

        final DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        db.open();

        cursor = db.ShowAllGenres(null);

        ListViewLayout(cursor);

        db.close();

        return v;
    }

    public void ListViewLayout(Cursor cursor){

        final DatabaseAccess db = new DatabaseAccess(getActivity());

        //mapping from cursor to view fields
        String[] fromColNames = new String[] {
                //db.TG_ID,
                db.TG_GENRE
        };
        int[] toViewIDs = new int[] {
                //R.id.genre_id,
                R.id.genre_name
        };

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.show_genres,
                cursor,
                fromColNames,
                toViewIDs
        );

        ListView myList = (ListView)v.findViewById(R.id.genres_listview);
        myList.setAdapter(myCursorAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_genres_and_series, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        switch(item.getItemId())
        {
            case R.id.sort_id:
                db.open();
                sort = " ORDER BY " + db.TG_ID;
                cursor = db.ShowAllGenres(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.name_asc:
                db.open();
                sort = " ORDER BY " + db.TG_GENRE + " ASC";
                cursor = db.ShowAllGenres(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.name_desc:
                db.open();
                sort = " ORDER BY "+  db.TG_GENRE + " DESC";
                cursor = db.ShowAllGenres(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
