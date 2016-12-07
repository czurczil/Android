package com.example.czurczak.Biblioteka;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by czurczak on 06.11.2016.
 */

public class BooksFragment extends android.support.v4.app.Fragment {
    View v;
    Cursor cursor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.books_fragment, container, false);

        DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        db.open();


        Bundle bundle = this.getArguments();
        if(bundle != null){
            String value = bundle.getString("Tabela");
            if(value.equals("Ulubione")){
                cursor = db.ShowSelectedBooks(value);
            }
            else if(value.equals("Na_polce")){
                cursor = db.ShowSelectedBooks(value);
            }
            else cursor = db.ShowSelectedBooks(value);
        }
        else cursor = db.ShowAllBooks();

        ListViewLayout(cursor);

        db.close();

        return v;
    }

    public void ListViewLayout(Cursor cursor){

        final DatabaseAccess db = DatabaseAccess.getInstance(getActivity());
        db.open();
        //mapping from cursor to view fields
        String[] fromColNames = new String[] {
                db.TB_ID,
                db.TB_TITLE,
                db.KEY_AUTHOR,
                db.TB_COVER
        };
        int[] toViewIDs = new int[] {
                R.id.tvID,
                R.id.tvTitle,
                R.id.tvAuthor,
                R.id.imgCover
        };

        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.show_books,
                cursor,
                fromColNames,
                toViewIDs
        );
        if(cursor != null && cursor.moveToFirst()) {
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

        ListView myList = (ListView)v.findViewById(R.id.books_listview);
        myList.setAdapter(myCursorAdapter);
    }
}
