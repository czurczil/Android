package com.example.czurczak.test;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by czurczak on 12.09.2016.
 */
public class Database extends SQLiteOpenHelper {
    public Database(Context context){
        super(context, "Biblioteka.db", null, 2);
    }

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

    public void ShowAll(TextView txt){
        String[] kolumny = {"id","Tytuł", "Autor", "Rok_wydania", "Opis", "Cykl", "Okładka", "Gatunek"};
        //List<String> kolumny = new LinkedList<String>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor k = db.query("Ksiazki", kolumny, null, null, null, null, null); //rawQuery("Select * from Ksiazki",null);
        k.moveToFirst();
        txt.setText("");
        while(k.moveToNext()){
            int id = k.getInt(0);
            String title = k.getString(1);
            String author = k.getString(2);
            int year = k.getInt(3);
            String desc = k.getString(4);
            String cycle = k.getString(5);
            String cover = k.getString(6);
            String type = k.getString(7);
            txt.setText(txt.getText() + "\n" + id + " | " + title + " | " +  author + " | " +
                    year + " | " +  desc + " | " + cycle + " | " + cover + " | " + type +"\n");
        }
        k.close();
    }

    public void AddBook(String title,  String author, int year, String desc,  String cycle, String cover, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tytuł", title);
        values.put("Autor", author);
        values.put("Rok_wydania", year);
        values.put("Opis", desc);
        values.put("Cykl", cycle);
        values.put("Okładka", cover);
        values.put("Gatunek", type);
        db.insert("Ksiazki", null, values);
    }
    public void AddBook(String title,  String author, int year, String desc,  String cycle, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tytuł", title);
        values.put("Autor", author);
        values.put("Rok_wydania", year);
        values.put("Opis", desc);
        values.put("Cykl", cycle);
        values.put("Gatunek", type);
        db.insert("Ksiazki", null, values);
    }
    public void AddBook(String title,  String author, int year,  String cycle, String type){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tytuł", title);
        values.put("Autor", author);
        values.put("Rok_wydania", year);
        values.put("Cykl", cycle);
        values.put("Gatunek", type);
        db.insert("Ksiazki", null, values);
    }
    public void AddBook(String title,  String author, int year,  String cycle){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tytuł", title);
        values.put("Autor", author);
        values.put("Rok_wydania", year);
        values.put("Cykl", cycle);
        db.insert("Ksiazki", null, values);
    }
    public void AddBook(String title,  String author){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Tytuł", title);
        values.put("Autor", author);
        db.insert("Ksiazki", null, values);
    }

    public void DeleteBook(int id){
        SQLiteDatabase db = getWritableDatabase();
        String[] ksiazki = {""+id};
        db.delete("Ksiazki", "id=?", ksiazki);
    }

}
