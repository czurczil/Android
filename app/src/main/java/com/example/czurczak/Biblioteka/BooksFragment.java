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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    int isFavorite;
    int isOnMyShelf;
    int isOnWishList;

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
                db.TB_COVER,
                db.TB_FAVORITE,
                db.TB_ON_SHELF,
                db.TB_WISHES

        };
        int[] toViewIDs = new int[]{
                R.id.tvTitle,
                R.id.tvAuthor,
                R.id.imgCover,
                R.id.imageFav,
                R.id.imageRead,
                R.id.imageToRead
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
                    boolean binded = false;
                    if(colIndex == cursor.getColumnIndex(db.TB_COVER)){
                        ImageView cover = (ImageView)view.findViewById(R.id.imgCover);
                        cover.setImageBitmap(db.GetImage(cursor, 0));
                        binded = true;
                    }
                    if(colIndex == cursor.getColumnIndex(db.TB_FAVORITE)){
                        ImageView fav_button = (ImageView)view.findViewById(R.id.imageFav);
                        isFavorite = cursor.getInt(cursor.getColumnIndex(db.TB_FAVORITE));
                        if(isFavorite == 1)
                            fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_on);
                        else fav_button.setImageResource(R.drawable.ic_favorite_white_48dp_off);
                        binded = true;
                    }
                   if(colIndex == cursor.getColumnIndex(db.TB_ON_SHELF)){
                        ImageView on_my_shelf_button = (ImageView)view.findViewById(R.id.imageRead);
                        isOnMyShelf = cursor.getInt(cursor.getColumnIndex(db.TB_ON_SHELF));
                        if(isOnMyShelf == 1)
                            on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_on);
                        else on_my_shelf_button.setImageResource(R.drawable.ic_storage_white_48dp_off);
                        binded = true;
                    }
                    if(colIndex == cursor.getColumnIndex(db.TB_WISHES)){
                        ImageView on_wish_list_button = (ImageView)view.findViewById(R.id.imageToRead);
                        isOnWishList = cursor.getInt(cursor.getColumnIndex(db.TB_WISHES));
                        if(isOnWishList == 1)
                            on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_on);
                        else on_wish_list_button.setImageResource(R.drawable.ic_assignment_white_48dp_off);
                        binded = true;
                    }
                    return binded;
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
            case R.id.year_up:
                db.open();
                sort = " ORDER BY "+ db.TABLE_BOOKS + "." + db.TB_YEAR + " ASC";
                if(value == null) cursor = db.ShowAllBooks(sort);
                else cursor = db.ShowSelectedBooks(value, sort);
                ListViewLayout(cursor);
                db.close();
                return true;
            case R.id.year_down:
                db.open();
                sort = " ORDER BY "+ db.TABLE_BOOKS + "." + db.TB_YEAR + " DESC";
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