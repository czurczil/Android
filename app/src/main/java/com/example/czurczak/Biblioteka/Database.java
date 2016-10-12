package com.example.czurczak.Biblioteka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by czurczak on 12.09.2016.
 */
public class Database extends SQLiteOpenHelper {
    public Database(Context context){
        super(context, "Biblioteka.db", null, 2);
    }

    private static final String DATABASE_NAME = "Biblioteka.db";
    private static final String TABLE_NAME = "Ksiazki";

    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "Tytuł";
    public static final String KEY_AUTHOR = "Autor";
    public static final String KEY_YEAR = "Rok_wydania";
    public static final String KEY_DESC = "Opis";
    public static final String KEY_CYKLE = "Cykl";
    public static final String KEY_COVER = "Okładka";
    public static final String KEY_GENRE = "Gatunek";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table Ksiazki (" +
                "id integer primary key autoincrement," +
                "Tytuł text," +
                "Autor text," +
                "Rok_wydania integer," +
                "Opis text," +
                "Cykl text," +
                "Okładka text," +
                "Gatunek text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Ksiazki");
        onCreate(db);
    }

    public Cursor ShowAll(){
        SQLiteDatabase db = getReadableDatabase();

        //String[] kolumny = {KEY_ID, KEY_TITLE, KEY_AUTHOR, KEY_YEAR, KEY_DESC, KEY_CYKLE, KEY_COVER, KEY_GENRE};
        //Cursor cursor = db.query(TABLE_NAME, kolumny, null, null, null, null, null); //

        String newID = "_id";
        String slctQuery = "SELECT " + KEY_ID + " AS " + newID + ", " + KEY_TITLE + ", " + KEY_AUTHOR + ", " + KEY_YEAR + ", "
                + KEY_DESC + ", " + KEY_CYKLE + ", " + KEY_COVER + ", " + KEY_GENRE + " FROM " + TABLE_NAME;
       /* String slctQuery = "SELECT " + KEY_ID + " AS " + newID + ", " + KEY_TITLE + ", " + KEY_AUTHOR + ", " + KEY_YEAR + ", "
                + KEY_DESC + ", " + KEY_CYKLE + ", " + KEY_COVER + ", " + KEY_GENRE + " FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=1";*/
        Cursor cursor = db.rawQuery(slctQuery, null);
        if(cursor != null)
            cursor.move(0);
        return cursor;
    }

    public Cursor ShowSelected (String spinner, String text){
        Cursor k;
        SQLiteDatabase db = getReadableDatabase();
        String[] args = new String[1];
        args[0] = "%" + text + "%";
        if(spinner.equals("Rok wydania")) spinner = "Rok_wydania";
        String slctQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + spinner + " ";
        k = db.rawQuery(slctQuery + "LIKE ?", args);
        return k;
    }

    public void AddBook(String title,  String author, int year, String desc,  String cycle, String cover, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_YEAR, year);
        values.put(KEY_DESC, desc);
        values.put(KEY_CYKLE, cycle);
        values.put(KEY_COVER, cover);
        values.put(KEY_GENRE, type);
        db.insert(TABLE_NAME, null, values);
    }
    public void AddBook(String title,  String author, int year, String desc,  String cycle, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_YEAR, year);
        values.put(KEY_DESC, desc);
        values.put(KEY_CYKLE, cycle);
        values.put(KEY_GENRE, type);
        db.insert("Ksiazki", null, values);
    }
    public void AddBook(String title,  String author, int year,  String cycle, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_YEAR, year);
        values.put(KEY_CYKLE, cycle);
        values.put(KEY_GENRE, type);
        db.insert(TABLE_NAME, null, values);
    }
    public void AddBook(String title,  String author, int year,  String cycle){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_YEAR, year);
        values.put(KEY_CYKLE, cycle);
        db.insert(TABLE_NAME, null, values);
    }
    public void AddBook(String title,  String author, int year){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_YEAR, year);
        db.insert(TABLE_NAME, null, values);
    }
    public void AddBook(String title,  String author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        db.insert(TABLE_NAME, null, values);
    }

    public void DeleteBook(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] ksiazki = {""+id};
        db.delete(TABLE_NAME, "id=?", ksiazki);
    }

    public void UpdateBook(int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Okładka","a180c50d773.jpg");
        db.update(TABLE_NAME,values,"id="+id, null);
    }

    public Database open(){
        SQLiteDatabase db = getReadableDatabase();
        return this;
    }

}
