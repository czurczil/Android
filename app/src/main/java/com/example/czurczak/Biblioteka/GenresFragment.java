package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.genres_fragment, container, false);

        Database db = new Database(getActivity());

        Cursor cursor = db.ShowAllGenres();

        ListViewLayout(cursor);

        db.close();

        return v;
    }

    public void ListViewLayout(Cursor cursor){

        final Database db = new Database(getActivity());

        //mapping from cursor to view fields
        String[] fromColNames = new String[] {
                db.TG_ID,
                db.TG_GENRE
        };
        int[] toViewIDs = new int[] {
                R.id.genre_id,
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

}
