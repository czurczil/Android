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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

/**
 * Created by czurczak on 06.11.2016.
 */

public class AuthorsFragment extends Fragment {
    View v;
    String sort;
    Cursor cursor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.authors_fragment, container, false);

        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        db.open();

        cursor = db.ShowAllAuthors(null);

        ListViewLayout(cursor);

        db.close();

        return v;
    }

    public SimpleCursorAdapter ListViewLayout(Cursor cursor){

        final DatabaseAccess db = new DatabaseAccess(getActivity());

        String[] fromColNames = new String[] {
                //db.TA_ID,
                db.KEY_AUTHOR,
                db.TA_PHOTO
        };
        int[] toViewIDs = new int[] {
                //R.id.authors_id,
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
        ListView myList = (ListView)v.findViewById(R.id.authors_listview);

        myList.setAdapter(myCursorAdapter);

        return myCursorAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sort_authors, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        switch(item.getItemId())
        {
            case R.id.author_id:
                db.open();
                sort = " ORDER BY " + db.TABLE_AUTHOR + "." + db.TB_ID;
                cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.first_name_asc:
                db.open();
                sort = " ORDER BY " + db.TABLE_AUTHOR + "." + db.TA_FIRST_NAME + " ASC";
                cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.first_name_desc:
                db.open();
                sort = " ORDER BY "+ db.TABLE_AUTHOR + "." + db.TA_FIRST_NAME + " DESC";
                cursor = db.ShowAllAuthors(sort);
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
                cursor = db.ShowAllAuthors(sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
