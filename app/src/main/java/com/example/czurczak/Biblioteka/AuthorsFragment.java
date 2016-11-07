package com.example.czurczak.Biblioteka;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by czurczak on 06.11.2016.
 */

public class AuthorsFragment extends Fragment {
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.authors_fragment, container, false);

        Database db = new Database(getActivity());

        Cursor cursor = db.ShowAllAuthors();

        ListView myList = (ListView)v.findViewById(R.id.authors_listview);

        myList.setAdapter(ListViewLayout(cursor));

        db.close();

        return v;
    }

    public SimpleCursorAdapter ListViewLayout(Cursor cursor){

        final Database db = new Database(getActivity());

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
                getActivity(),
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
}
