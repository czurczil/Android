package com.example.czurczak.Biblioteka;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by czurczak on 07.12.2016.
 */

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    public static final String TABLE_BOOKS = "Ksiazki";
    public static final String TB_ID = "_id";
    public static final String TB_TITLE = "Tytuł";
    public static final String TB_YEAR = "Rok_wydania";
    public static final String TB_DESC = "Opis";
    public static final String TB_CYKLE = "Cykl";
    public static final String TB_COVER = "Okładka";
    public static final String TB_FAVORITE = "Ulubione";
    public static final String TB_ON_SHELF = "Na_polce";
    public static final String TB_WISHES = "Do_przeczytania";

    public static final String KEY_GENRE = "Gatunek";
    public static final String KEY_AUTHOR = "Autor";

    public static final String TABLE_AUTHOR = "Autor";
    public static final String TA_ID = "_id";
    public static final String TA_FIRST_NAME = "Imię";
    public static final String TA_LAST_NAME = "Nazwisko";
    public static final String TA_BIRTH_DATE = "Data_urodzenia";
    public static final String TA_SEX = "Płeć";
    public static final String TA_BIRTH_PLACE = "Miejsce_urodzenia";
    public static final String TA_BIO = "Biografia";
    public static final String TA_PHOTO = "Zdjęcie";


    public static final String TABLE_AUTHOR_BOOKS = "Ksiazki_Autorów";
    public static final String TAB_ID = "_id";
    public static final String TAB_AUTHOR_ID = "id_Autora";
    public static final String TAB_BOOK_ID = "id_Ksiazki";

    public static final String TABLE_GENRE = "Gatunek";
    public static final String TG_ID = "_id";
    public static final String TG_GENRE = "Gatunek";

    public static final String TABLE_BOOKS_GENRE = "Gatunki_Ksiazek";
    public static final String TBG_ID = "_id";
    public static final String TBG_BOOK_ID = "id_Ksiazki";
    public static final String TBG_GENRE_ID = "id_Gatunku";

    public static final int CoverHeight = 500;
    public static final int CoverWidth = 350;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    public DatabaseAccess(Context context) {
        this.openHelper = new Database(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the readable database connection.
     */
    public void open() {
        this.db = openHelper.getReadableDatabase();
    }

    public void write() {
        this.db = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public Cursor ShowAllSeries(String sort){
        //SQLiteDatabase db = getReadableDatabase();


        Cursor cursor;
        String slctQuery = "SELECT " + TB_ID + ", " + TB_CYKLE +
                " FROM " + TABLE_BOOKS + " WHERE " + TB_CYKLE + " <> '' " +
                "GROUP BY " + TB_CYKLE;
        if(sort != null)
            slctQuery = slctQuery + sort;
        cursor = db.rawQuery(slctQuery, null);

        if(cursor != null)
            cursor.move(0);
        return cursor;
    }

    public Cursor ShowAllGenres(String sort){


        Cursor cursor;
        String slctQuery = "SELECT " + TG_ID + ", " + TG_GENRE +
                " FROM " + TABLE_GENRE;
        if(sort != null)
            slctQuery = slctQuery + sort;
        cursor = db.rawQuery(slctQuery, null);

        if(cursor != null)
            cursor.move(0);
        return cursor;
    }

    public Cursor ShowAllAuthors(String sort){
        //SQLiteDatabase db = getReadableDatabase();



        Cursor cursor;
        String slctQuery = "SELECT " + TA_ID + ", (" + TA_FIRST_NAME + "  || ' ' ||  " + TA_LAST_NAME + ") AS Autor, " + TA_PHOTO +
                " FROM " + TABLE_AUTHOR;
        if(sort != null)
            slctQuery = slctQuery + sort;
        cursor = db.rawQuery(slctQuery, null);

        if(cursor != null)
            cursor.move(0);
        return cursor;
    }
    public Cursor ShowSelectedAuthor(String author){
        //SQLiteDatabase db = getReadableDatabase();



        Cursor cursor;

        if(author.contains(",")){

            String slctQuery = "SELECT " + TA_ID + ", (" + TA_FIRST_NAME + "  || ' ' ||  " + TA_LAST_NAME + ") AS Autor, " +
                    TA_BIRTH_DATE + ", " + TA_SEX + ", " + TA_BIRTH_PLACE + ", " + TA_BIO + ", " + TA_PHOTO +
                    " FROM " + TABLE_AUTHOR + " WHERE " + "(" + TA_FIRST_NAME + "  || ' ' ||  " + TA_LAST_NAME + ") LIKE ?";

            int count = author.length() - author.replaceAll(",","").length();
            String parts[] = author.split(",");
            String authors[] = new String[count + 1];

            for(int i = 0; i <= count; i++){
                authors[i] =  parts[i];
                slctQuery = slctQuery + " OR (" + TA_FIRST_NAME + "  || ' ' ||  " + TA_LAST_NAME + ") LIKE ?";
            }

            cursor = db.rawQuery(slctQuery, authors);
        }
        else {
            String slctQuery = "SELECT " + TA_ID + ", (" + TA_FIRST_NAME + "  || ' ' ||  " + TA_LAST_NAME + ") AS Autor, " +
                    TA_BIRTH_DATE + ", " + TA_SEX + ", " + TA_BIRTH_PLACE + ", " + TA_BIO + ", " + TA_PHOTO +
                    " FROM " + TABLE_AUTHOR + " WHERE " + "(" + TA_FIRST_NAME + "  || ' ' ||  " + TA_LAST_NAME + ") LIKE ?;";
            cursor = db.rawQuery(slctQuery, new String[] {author});
        }

        if(cursor != null)
            cursor.move(0);
        return cursor;
    }

    //Displaying results for Books
    public Cursor ShowAllBooks(String sort){
        String slctQuery = "SELECT " + TABLE_BOOKS + "." + TB_ID + ", " +
                "GROUP_CONCAT(DISTINCT " + TABLE_AUTHOR + "." + TA_FIRST_NAME + " || ' ' || " + TABLE_AUTHOR + "." + TA_LAST_NAME +") AS Autor, " +
                TABLE_BOOKS + "." + TB_TITLE + ", " + TABLE_BOOKS + "." + TB_YEAR + ", " + TABLE_BOOKS + "." + TB_DESC + ", " +
                TABLE_BOOKS + "." + TB_CYKLE + ", " + TABLE_BOOKS + "." + TB_COVER + ", GROUP_CONCAT(DISTINCT " + TABLE_GENRE + "." + TG_GENRE + ") AS Gatunek, " +
                TABLE_BOOKS + "." + TB_FAVORITE +
                " FROM " + TABLE_BOOKS +
                " LEFT JOIN " + TABLE_AUTHOR_BOOKS + " ON ("+ TABLE_BOOKS + "." + TB_ID +"=" + TABLE_AUTHOR_BOOKS + "." + TAB_BOOK_ID +") " +
                "LEFT JOIN " + TABLE_AUTHOR + " ON ("+ TABLE_AUTHOR_BOOKS + "." + TAB_AUTHOR_ID +"=" + TABLE_AUTHOR + "." + TA_ID + ") " +
                "LEFT JOIN " + TABLE_BOOKS_GENRE + " ON (" + TABLE_BOOKS + "." + TB_ID + "=" + TABLE_BOOKS_GENRE + "." + TBG_BOOK_ID + ") " +
                "LEFT JOIN " + TABLE_GENRE + " ON (" + TABLE_BOOKS_GENRE + "." + TBG_GENRE_ID + "=" + TABLE_GENRE + "." + TG_ID + ") " +
                "GROUP BY "  + TABLE_BOOKS + "." + TB_ID;
        if(sort != null)
            slctQuery = slctQuery + sort;

        Cursor cursor = db.rawQuery(slctQuery, null);
        if(cursor != null)
            cursor.move(0);
        return cursor;
    }

    public Cursor ShowSelectedBooks(String spinner, String text, String sort){
        // SQLiteDatabase db = getReadableDatabase();


        Cursor cursor;

        String slctQuery = "SELECT " + TABLE_BOOKS + "." + TB_ID + ", " +
                "GROUP_CONCAT(DISTINCT " + TABLE_AUTHOR + "." + TA_FIRST_NAME + " || ' ' || " + TABLE_AUTHOR + "." + TA_LAST_NAME +") AS Autor, " +
                TABLE_BOOKS + "." + TB_TITLE + ", " + TABLE_BOOKS + "." + TB_YEAR + ", " + TABLE_BOOKS + "." + TB_DESC + ", " +
                TABLE_BOOKS + "." + TB_CYKLE + ", " + TABLE_BOOKS + "." + TB_COVER + ", GROUP_CONCAT(DISTINCT " + TABLE_GENRE + "." + TG_GENRE + ") AS Gatunek, " +
                TABLE_BOOKS + "." + TB_FAVORITE +
                " FROM " + TABLE_BOOKS +
                " LEFT JOIN " + TABLE_AUTHOR_BOOKS + " ON ("+ TABLE_BOOKS + "." + TB_ID +"=" + TABLE_AUTHOR_BOOKS + "." + TAB_BOOK_ID +") " +
                "LEFT JOIN " + TABLE_AUTHOR + " ON ("+ TABLE_AUTHOR_BOOKS + "." + TAB_AUTHOR_ID +"=" + TABLE_AUTHOR + "." + TA_ID + ") " +
                "LEFT JOIN " + TABLE_BOOKS_GENRE + " ON (" + TABLE_BOOKS + "." + TB_ID + "=" + TABLE_BOOKS_GENRE + "." + TBG_BOOK_ID + ") " +
                "LEFT JOIN " + TABLE_GENRE + " ON (" + TABLE_BOOKS_GENRE + "." + TBG_GENRE_ID + "=" + TABLE_GENRE + "." + TG_ID + ") ";

        if (spinner.equals("Autor")) {
            if (text.contains(" ")) {
                String[] parts = text.split(" ");
                String imie = "%" + parts[0] + "%";
                String nazwisko = "%" + parts[1] + "%";
                slctQuery = slctQuery + " WHERE (" + TABLE_AUTHOR + "." + TA_FIRST_NAME + " LIKE ? AND " + TABLE_AUTHOR + "." + TA_LAST_NAME + " LIKE ?)" +
                        " OR (" + TABLE_AUTHOR + "." + TA_LAST_NAME + " LIKE ? AND " + TABLE_AUTHOR + "." + TA_FIRST_NAME + " LIKE ?) " +
                        "GROUP BY "  + TABLE_BOOKS + "." + TB_ID;
                if(sort != null)
                    slctQuery = slctQuery + sort;
                cursor = db.rawQuery(slctQuery, new String[]{imie, nazwisko, imie, nazwisko});
            } else {
                slctQuery = slctQuery + " WHERE " + TABLE_AUTHOR + "." + TA_FIRST_NAME + " LIKE ? OR " + TABLE_AUTHOR + "." + TA_LAST_NAME + " LIKE ?" +
                        " GROUP BY "  + TABLE_BOOKS + "." + TB_ID;
                if(sort != null)
                    slctQuery = slctQuery + sort;
                cursor = db.rawQuery(slctQuery, new String[]{"%" + text + "%", "%" + text + "%"});
            }
        }
        else if (spinner.equals("Gatunek")){
            if(text.contains(",")){

                slctQuery = slctQuery + " WHERE " + TABLE_GENRE + "." + TG_GENRE + " LIKE ? ";

                int count = text.length() - text.replaceAll(",","").length();
                String parts[] = text.split(",");
                String genres[] = new String[count + 1];

                for(int i = 0; i <= count; i++){
                    genres[i] = "%" + parts[i] + "%";
                    slctQuery = slctQuery + " OR "+ TABLE_GENRE + "." + TG_GENRE + " LIKE ? ";
                }

                slctQuery = slctQuery + "GROUP BY "  + TABLE_BOOKS + "." + TB_ID;
                if(sort != null)
                    slctQuery = slctQuery + sort;
                cursor = db.rawQuery(slctQuery, genres);
            }
            else {
                slctQuery = slctQuery + " WHERE " + TABLE_GENRE + "." + TG_GENRE + " LIKE ? " +
                        "GROUP BY "  + TABLE_BOOKS + "." + TB_ID;
                if(sort != null)
                    slctQuery = slctQuery + sort;
                cursor = db.rawQuery(slctQuery, new String[] {"%" + text + "%"});
            }
        }
        else {
            if (spinner.equals("Tytuł")) slctQuery = slctQuery + " WHERE " + TABLE_BOOKS + "." + TB_TITLE +" LIKE ? " +
                    "GROUP BY "  + TABLE_BOOKS + "." + TB_ID;
            else if (spinner.equals("Rok wydania"))
                slctQuery = slctQuery + " WHERE " + TABLE_BOOKS + "." + TB_YEAR + " LIKE ? " +
                        "GROUP BY "  + TABLE_BOOKS + "." + TB_ID;
            else if (spinner.equals("Cykl")) slctQuery = slctQuery + " WHERE " + TABLE_BOOKS + "." + TB_CYKLE + " LIKE ? " +
                    "GROUP BY "  + TABLE_BOOKS + "." + TB_ID;

            if(sort != null)
                slctQuery = slctQuery + sort;
            cursor = db.rawQuery(slctQuery, new String[]{"%" + text + "%"});
        }

        if (cursor != null)
            cursor.move(0);
        return cursor;

    }
    public Cursor ShowSelectedBooks(String text, String sort){
        //SQLiteDatabase db = getReadableDatabase();


        Cursor cursor;

        String slctQuery = "SELECT " + TABLE_BOOKS + "." + TB_ID + ", " +
                "GROUP_CONCAT(DISTINCT " + TABLE_AUTHOR + "." + TA_FIRST_NAME + " || ' ' || " + TABLE_AUTHOR + "." + TA_LAST_NAME +") AS Autor, " +
                TABLE_BOOKS + "." + TB_TITLE + ", " + TABLE_BOOKS + "." + TB_YEAR + ", " + TABLE_BOOKS + "." + TB_DESC + ", " +
                TABLE_BOOKS + "." + TB_CYKLE + ", " + TABLE_BOOKS + "." + TB_COVER + ", GROUP_CONCAT(DISTINCT " + TABLE_GENRE + "." + TG_GENRE + ") AS Gatunek, " +
                TABLE_BOOKS + "." + TB_FAVORITE + ", " + TABLE_BOOKS + "." + TB_ON_SHELF + ", " + TABLE_BOOKS + "." + TB_WISHES +
                " FROM " + TABLE_BOOKS +
                " LEFT JOIN " + TABLE_AUTHOR_BOOKS + " ON ("+ TABLE_BOOKS + "." + TB_ID +"=" + TABLE_AUTHOR_BOOKS + "." + TAB_BOOK_ID +") " +
                "LEFT JOIN " + TABLE_AUTHOR + " ON ("+ TABLE_AUTHOR_BOOKS + "." + TAB_AUTHOR_ID +"=" + TABLE_AUTHOR + "." + TA_ID + ") " +
                "LEFT JOIN " + TABLE_BOOKS_GENRE + " ON (" + TABLE_BOOKS + "." + TB_ID + "=" + TABLE_BOOKS_GENRE + "." + TBG_BOOK_ID + ") " +
                "LEFT JOIN " + TABLE_GENRE + " ON (" + TABLE_BOOKS_GENRE + "." + TBG_GENRE_ID + "=" + TABLE_GENRE + "." + TG_ID + ") ";

        if(text.equals(TB_FAVORITE)){
            slctQuery = slctQuery + " WHERE " + TABLE_BOOKS + "." + TB_FAVORITE + " = 1 " +
                    "GROUP BY " + TABLE_BOOKS + "." + TB_ID;
            if(sort != null)
                slctQuery = slctQuery + sort;
            cursor = db.rawQuery(slctQuery, null);
        }
        else if(text.equals(TB_ON_SHELF)){
            slctQuery = slctQuery + " WHERE " + TABLE_BOOKS + "." + TB_ON_SHELF + " = 1 " +
                    "GROUP BY " + TABLE_BOOKS + "." + TB_ID;
            if(sort != null)
                slctQuery = slctQuery + sort;
            cursor = db.rawQuery(slctQuery, null);
        }
        else if(text.equals(TB_WISHES)){
            slctQuery = slctQuery + " WHERE " + TABLE_BOOKS + "." + TB_WISHES + " = 1 " +
                    "GROUP BY " + TABLE_BOOKS + "." + TB_ID;
            if(sort != null)
                slctQuery = slctQuery + sort;
            cursor = db.rawQuery(slctQuery, null);
        }
        else{
            slctQuery = slctQuery + " WHERE " + TABLE_BOOKS + "." + TB_TITLE + " LIKE ? " +
                    "GROUP BY " + TABLE_BOOKS + "." + TB_ID;
            if(sort != null)
                slctQuery = slctQuery + sort;
            cursor = db.rawQuery(slctQuery, new String[]{ text });
        }

        if (cursor != null)
            cursor.move(0);
        return cursor;

    }

    public void RecordCount(Context context,Cursor cursor){
        int count = cursor.getCount();
        if(count == 1) Toast.makeText(context, "Znaleziono " + cursor.getCount()+ " wynik", Toast.LENGTH_SHORT).show();
        else if(count > 1 && count < 5 ) Toast.makeText(context, "Znaleziono " + cursor.getCount()+ " wyniki", Toast.LENGTH_SHORT).show();
        else if (count > 4) Toast.makeText(context, "Znaleziono " + cursor.getCount()+ " wyników", Toast.LENGTH_SHORT).show();
        else  Toast.makeText(context, "Nie znaleziono wyników", Toast.LENGTH_SHORT).show();
    }

    //Adding books
    public void AddBook(String title,  String first_name, String last_name, int year, String desc,  String cycle, byte[] cover,
                        String type, String birth_date, String birth_place, String sex, String bio, byte[] photo){


        ContentValues books = new ContentValues();
        books.put(TB_TITLE, title);
        books.put(TB_YEAR, year);
        books.put(TB_DESC, desc);
        books.put(TB_CYKLE, cycle);
        books.put(TB_COVER, cover);
        long book_id = db.insert(TABLE_BOOKS, null, books);

        ContentValues author = new ContentValues();
        author.put(TA_FIRST_NAME, first_name);
        author.put(TA_LAST_NAME, last_name);
        author.put(TA_BIRTH_DATE, birth_date);
        author.put(TA_SEX, sex);
        author.put(TA_BIRTH_PLACE, birth_place);
        author.put(TA_BIO, bio);
        author.put(TA_PHOTO, photo);
        long author_id = db.insert(TABLE_AUTHOR, null, author);

        ContentValues genre = new ContentValues();
        genre.put(TG_GENRE, type);
        long genre_id = db.insert(TABLE_GENRE, null, genre);

        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);

    }
    public void AddBook(String first_name, String last_name, String type, String birth_date, String birth_place, String sex,
                        String bio, byte[] photo, long book_id){
        //SQLiteDatabase db = getWritableDatabase();



        ContentValues author = new ContentValues();
        author.put(TA_FIRST_NAME, first_name);
        author.put(TA_LAST_NAME, last_name);
        author.put(TA_BIRTH_DATE, birth_date);
        author.put(TA_SEX, sex);
        author.put(TA_BIRTH_PLACE, birth_place);
        author.put(TA_BIO, bio);
        author.put(TA_PHOTO, photo);
        long author_id = db.insert(TABLE_AUTHOR, null, author);

        ContentValues genre = new ContentValues();
        genre.put(TG_GENRE, type);
        long genre_id = db.insert(TABLE_GENRE, null, genre);

        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);

    }
    public void AddBook(String title, String first_name, String last_name, int year, String desc,  String cycle, byte[] cover, String birth_date, String birth_place, String sex,
                        String bio, byte[] photo, long genre_id){
        //SQLiteDatabase db = getWritableDatabase();



        ContentValues books = new ContentValues();
        books.put(TB_TITLE, title);
        books.put(TB_YEAR, year);
        books.put(TB_DESC, desc);
        books.put(TB_CYKLE, cycle);
        books.put(TB_COVER, cover);
        long book_id = db.insert(TABLE_BOOKS, null, books);

        ContentValues author = new ContentValues();
        author.put(TA_FIRST_NAME, first_name);
        author.put(TA_LAST_NAME, last_name);
        author.put(TA_BIRTH_DATE, birth_date);
        author.put(TA_SEX, sex);
        author.put(TA_BIRTH_PLACE, birth_place);
        author.put(TA_BIO, bio);
        author.put(TA_PHOTO, photo);
        long author_id = db.insert(TABLE_AUTHOR, null, author);

        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);
    }
    public void AddBook(String title, int year, String desc, String cycle, byte[] cover, String type, long author_id){
        //SQLiteDatabase db = getWritableDatabase();



        ContentValues books = new ContentValues();
        books.put(TB_TITLE, title);
        books.put(TB_YEAR, year);
        books.put(TB_DESC, desc);
        books.put(TB_CYKLE, cycle);
        books.put(TB_COVER, cover);
        long book_id = db.insert(TABLE_BOOKS, null, books);

        ContentValues genre = new ContentValues();
        genre.put(TG_GENRE, type);
        long genre_id = db.insert(TABLE_GENRE, null, genre);

        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);
    }
    public void AddBook(String first_name, String last_name, String birth_date, String birth_place, String sex,
                        String bio, byte[] photo, long book_id, long genre_id){
        //SQLiteDatabase db = getWritableDatabase();



        ContentValues author = new ContentValues();
        author.put(TA_FIRST_NAME, first_name);
        author.put(TA_LAST_NAME, last_name);
        author.put(TA_BIRTH_DATE, birth_date);
        author.put(TA_SEX, sex);
        author.put(TA_BIRTH_PLACE, birth_place);
        author.put(TA_BIO, bio);
        author.put(TA_PHOTO, photo);
        long author_id = db.insert(TABLE_AUTHOR, null, author);

        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);
    }
    public void AddBook(String title, int year, String desc, String cycle, byte[] cover, long author_id, long genre_id){
        //SQLiteDatabase db = getWritableDatabase();


        ContentValues books = new ContentValues();
        books.put(TB_TITLE, title);
        books.put(TB_YEAR, year);
        books.put(TB_DESC, desc);
        books.put(TB_CYKLE, cycle);
        books.put(TB_COVER, cover);
        long book_id = db.insert(TABLE_BOOKS, null, books);

        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);
    }

    public void Bound(String type, long book_id, long author_id){
        //SQLiteDatabase db = getWritableDatabase();



        ContentValues genre = new ContentValues();
        genre.put(TG_GENRE, type);
        long genre_id = db.insert(TABLE_GENRE, null, genre);

        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);

    }
    public void Bound(long genre_id, long book_id, long author_id){
        //SQLiteDatabase db = getWritableDatabase();



        ContentValues ids = new ContentValues();
        ids.put(TBG_GENRE_ID, genre_id);
        ids.put(TBG_BOOK_ID, book_id);
        db.insert(TABLE_BOOKS_GENRE, null, ids);

        ContentValues ids2 = new ContentValues();
        ids2.put(TAB_AUTHOR_ID, author_id);
        ids2.put(TAB_BOOK_ID, book_id);
        db.insert(TABLE_AUTHOR_BOOKS, null, ids2);
    }

    public long GetID(String text, int n){
        //SQLiteDatabase db = getWritableDatabase();


        String slctquery;
        if(n == 0) {
            slctquery = "SELECT " + TB_ID +" FROM " + TABLE_BOOKS + " WHERE " + TB_TITLE + " LIKE ?;";
        }
        else if(n == 1) {
            slctquery = "SELECT " + TA_ID + " FROM " + TABLE_AUTHOR + " WHERE (" + TA_FIRST_NAME + " || ' ' || " + TA_LAST_NAME +") LIKE ?";
        }
        else {
            slctquery = "SELECT " + TG_ID + " FROM " + TABLE_GENRE + " WHERE " + TG_GENRE + " LIKE ?";
        }
        Cursor cursor = db.rawQuery(slctquery, new String [] {"%" + text + "%"});
        cursor.moveToFirst();
        long id = Integer.valueOf(cursor.getString(0));
        return id;
    }

    public boolean IsBoundedToThatBook(long book_id, long genre_author_id, int n){
        //SQLiteDatabase db = getWritableDatabase();


        String slctquery;
        Cursor cursor;
        if (n == 0) slctquery = "SELECT * FROM Gatunki_Ksiazek WHERE id_Ksiazki = ? AND id_Gatunku = ?";
        else slctquery = "SELECT * FROM Ksiazki_Autorów WHERE id_Ksiazki = ? AND id_Autora = ?";

        cursor = db.rawQuery(slctquery, new String[] {String.valueOf(book_id), String.valueOf(genre_author_id)});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public boolean IsAlreadyInDatabase(String text, int n){
        //SQLiteDatabase db = getReadableDatabase();


        String slctQuery;
        Cursor cursor;
        if(n == 0) {
            slctQuery = "SELECT " + TB_ID + " FROM " + TABLE_BOOKS + " WHERE " + TB_TITLE + " LIKE ?";
        }
        else if(n == 1) {
            slctQuery = "SELECT " + TA_ID + " FROM " + TABLE_AUTHOR + " WHERE (" + TA_FIRST_NAME + " || ' ' || " + TA_LAST_NAME + ") LIKE ?";
        }
        else {
            slctQuery = "SELECT " + TG_ID + " FROM " + TABLE_GENRE + " WHERE " + TG_GENRE + " LIKE ?";
        }
        cursor = db.rawQuery(slctQuery, new String[] {"%" + text + "%"});
        if(cursor.getCount() > 0) return true;
        else return false;
    }

    public byte[] GetImage(Drawable drawable){
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        return bitmapdata;
    }
    //Dealing with book covers
    public Bitmap GetImage(Cursor c, int n){

        String column;
        if(n == 0) column = "Okładka";
        else column = "Zdjęcie";
        int img = c.getColumnIndex(column);

        byte[] image = c.getBlob(img);
        if (image != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            return bmp;
        }
        else return null;
    }
    public byte[] SaveImageFromGallery(ContentResolver cr, Uri imageUri){

        try{
            InputStream in = cr.openInputStream(imageUri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;                              // to get image height and width
            BitmapFactory.decodeStream(in, null, options);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, imageUri);
            Bitmap scaled;
            ByteArrayOutputStream img = new ByteArrayOutputStream();
            if(options.outWidth > CoverWidth + 100 && options.outHeight > CoverHeight + 100) {
                scaled = Bitmap.createScaledBitmap(bitmap, CoverWidth, CoverHeight, true);
                scaled.compress(Bitmap.CompressFormat.JPEG, 100, img);
            }
            else bitmap.compress(Bitmap.CompressFormat.JPEG, 100, img);

            in.close();
            byte[] image = img.toByteArray();
            return image;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //Editing one row
    public void DeleteBook(int id){
        //SQLiteDatabase db = getWritableDatabase();

        String[] ksiazki = {""+id};
        db.delete(TABLE_BOOKS, "_id=?", ksiazki);
    }

    public void UpdateFavorite(int id, int favorite){
        //SQLiteDatabase db = getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(TB_FAVORITE, favorite);
        db.update(TABLE_BOOKS, values,"_id="+id, null);
    }
    public void UpdateMyShelf(int id, int shelf){

        // SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_ON_SHELF, shelf);
        db.update(TABLE_BOOKS, values,"_id="+id, null);
    }
    public void UpdateWishList(int id, int wish){
        //SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TB_WISHES, wish);
        db.update(TABLE_BOOKS, values,"_id="+id, null);
    }

}
