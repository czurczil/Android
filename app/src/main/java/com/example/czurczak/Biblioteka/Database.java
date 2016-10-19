package com.example.czurczak.Biblioteka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by czurczak on 12.09.2016.
 */
public class Database extends SQLiteOpenHelper {
    public Database(Context context){
        super(context, "Biblioteka.db", null, 5);
    }

    private static final String DATABASE_NAME = "Biblioteka.db";
    private static final String TABLE_NAME = "Ksiazki";

    public static final String KEY_ID = "_id";
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
                "_id integer primary key autoincrement," +
                "Tytuł text," +
                "Autor text," +
                "Rok_wydania integer," +
                "Opis text," +
                "Cykl text," +
                "Okładka blob," +
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

        String slctQuery = "SELECT " + KEY_ID  + ", " + KEY_TITLE + ", " + KEY_AUTHOR + ", " + KEY_YEAR + ", "
                + KEY_DESC + ", " + KEY_CYKLE + ", " + KEY_COVER + ", " + KEY_GENRE + " FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(slctQuery, null);
        if(cursor != null)
            cursor.move(0);
        return cursor;
    }

    public Cursor ShowSelected (String spinner, String text){
        SQLiteDatabase db = getReadableDatabase();

        String[] args = new String[1];
        args[0] = "%" + text + "%";
        if(spinner.equals("Rok wydania")) spinner = "Rok_wydania";
        String slctQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + spinner + " ";
        Cursor cursor = db.rawQuery(slctQuery + "LIKE ?", args);
        return cursor;
    }

    public void AddBook(String title,  String author, int year, String desc,  String cycle, String cover, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, title);
        values.put(KEY_AUTHOR, author);
        values.put(KEY_YEAR, year);
        values.put(KEY_DESC, desc);
        values.put(KEY_CYKLE, cycle);
        values.put(KEY_COVER, SaveImage(cover));
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
            //values.put(KEY_COVER, image);
            values.put(KEY_GENRE, type);
            db.insert(TABLE_NAME, null, values);
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

    public byte[] SaveImage(String path){
        String extr = Environment.getExternalStorageDirectory().toString();
        try{
            FileInputStream im = new FileInputStream(extr + "/Biblioteka/covers/" + path);
            byte[] image = new byte[im.available()];
            im.read(image);

            im.close();
            return image;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public Bitmap GetImage(Cursor c){

            byte[] image = c.getBlob(6);
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

            return bmp;
    }

    public void DeleteBook(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] ksiazki = {""+id};
        db.delete(TABLE_NAME, "_id=?", ksiazki);
    }

    public void UpdateBook(int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Okładka","a180c50d773");
        db.update(TABLE_NAME,values,"_id="+id, null);
    }

    public Database open(){
        SQLiteDatabase db = getReadableDatabase();
        return this;
    }

}
