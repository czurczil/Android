package com.example.czurczak.test;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;
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

    public Cursor ShowAll(){
        String[] kolumny = {"id","Tytuł", "Autor", "Rok_wydania", "Opis", "Cykl", "Okładka", "Gatunek"};
        //List<String> kolumny = new LinkedList<String>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor k = db.query("Ksiazki", kolumny, null, null, null, null, null); //rawQuery("Select * from Ksiazki",null);

        return k;
    }

    public Cursor ShowSelected (String spinner, String text){
        Cursor k;
        SQLiteDatabase db = getReadableDatabase();
        String[] args = new String[1];
        args[0] = "%" + text + "%";
        if(spinner.equals("Rok wydania")) spinner = "Rok_wydania";
        String slctQuery = "SELECT * FROM Ksiazki WHERE " + spinner + " ";
        k = db.rawQuery(slctQuery + "LIKE ?", args);
        return k;
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
