package com.example.czurczak.Biblioteka;

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

/**
 * Created by czurczak on 22.11.2016.
 */

public class SeriesFragment extends Fragment {
    View v;
    String sort;
    Cursor cursor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.series_fragment, container, false);

        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        db.open();

        cursor = db.ShowAllSeries(null);

        ListViewLayout(cursor);

        db.close();

        return v;
    }

    public void ListViewLayout(Cursor cursor){

        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());

        //mapping from cursor to view fields

        String[] fromColNames = new String[] {
                db.TB_CYKLE
        };
        int[] toViewIDs = new int[] {
                R.id.series_name
        };

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.show_series,
                cursor,
                fromColNames,
                toViewIDs
        );

        ListView myList = (ListView)v.findViewById(R.id.series_listview);
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
                sort = " ORDER BY " + db.TB_ID;
                cursor = db.ShowAllSeries(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.name_asc:
                db.open();
                sort = " ORDER BY " + db.TB_CYKLE + " ASC";
                cursor = db.ShowAllSeries(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.name_desc:
                db.open();
                sort = " ORDER BY "+ db.TB_CYKLE + " DESC";
                cursor = db.ShowAllSeries(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
