package com.example.czurczak.Biblioteka;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class BooksFragment extends android.support.v4.app.Fragment {
    View v;
    Cursor cursor;
    String sort;
    String value;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.books_fragment, container, false);

        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        db.open();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            value = bundle.getString("Tabela");
            if(value != null) cursor = db.ShowSelectedBooks(value, null);
        } else cursor = db.ShowAllBooks(null);

        ListViewLayout(cursor);

        db.close();

        return v;
    }

    public SimpleCursorAdapter ListViewLayout(Cursor cursor) {

        final DatabaseAccess db = DatabaseAccess.getInstance(getActivity());

        //mapping from cursor to view fields
        String[] fromColNames = new String[]{
                db.TB_TITLE,
                db.KEY_AUTHOR,
                db.TB_COVER
        };
        int[] toViewIDs = new int[]{
                R.id.tvTitle,
                R.id.tvAuthor,
                R.id.imgCover
        };

        final SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.show_books,
                cursor,
                fromColNames,
                toViewIDs
        );
        if (cursor != null && cursor.moveToFirst()) {
            myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Cursor cursor, int colIndex) {
                    //String name = cursor.getString(colIndex);

                    if (view.getId() == R.id.imgCover) {
                    /*ImageView IV=(ImageView) view;                                                            Commented section
                    String pack =  getApplicationContext().getPackageName();                                    set cover src to drawable
                    int resID = getApplicationContext().getResources().getIdentifier(name, "drawable", pack);
                    IV.setImageDrawable(getApplicationContext().getResources().getDrawable(resID));*/
                        ((ImageView) view).setImageBitmap(db.GetImage(cursor, 0));
                        return true;
                    }
                    return false;
                }
            });
        }

        ListView myList = (ListView) v.findViewById(R.id.books_listview);
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
        inflater.inflate(R.menu.sort_books, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        switch(item.getItemId())
        {
            case R.id.book_id:
                db.open();
                sort = " ORDER BY " + db.TABLE_BOOKS + "." + db.TB_ID;
                if(value == null) cursor = db.ShowAllBooks(sort);
                else cursor = db.ShowSelectedBooks(value, sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.title_asc:
                db.open();
                sort = " ORDER BY " + db.TABLE_BOOKS + "." + db.TB_TITLE + " ASC";
                if(value == null) cursor = db.ShowAllBooks(sort);
                else cursor = db.ShowSelectedBooks(value, sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.title_desc:
                db.open();
                sort = " ORDER BY "+ db.TABLE_BOOKS + "." + db.TB_TITLE + " DESC";
                if(value == null) cursor = db.ShowAllBooks(sort);
                else cursor = db.ShowSelectedBooks(value, sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}