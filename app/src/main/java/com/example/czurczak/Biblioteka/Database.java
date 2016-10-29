package com.example.czurczak.Biblioteka;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by czurczak on 12.09.2016.
 */
public class Database extends SQLiteOpenHelper {
    public Database(Context context){
        super(context, "Biblioteka2.db", null, 6);
    }

    public static final String DATABASE_NAME = "Biblioteka2.db";
    private static final String TABLE_BOOKS = "Ksiazki";
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

    private static final String TABLE_AUTHOR = "Autor";
    public static final String TA_ID = "_id";
    public static final String TA_FIRST_NAME = "Imię";
    public static final String TA_LAST_NAME = "Nazwisko";
    public static final String TA_BIRTH_DATE = "Data_urodzenia";
    public static final String TA_SEX = "Płeć";
    public static final String TA_BIRTH_PLACE = "Miejsce_urodzenia";
    public static final String TA_BIO = "Biografia";
    public static final String TA_PHOTO = "Zdjęcie";


    private static final String TABLE_AUTHOR_BOOKS = "Ksiazki_Autorów";
    public static final String TAB_ID = "_id";
    public static final String TAB_AUTHOR_ID = "id_Autora";
    public static final String TAB_BOOK_ID = "id_Ksiazki";

    private static final String TABLE_GENRE = "Gatunek";
    public static final String TG_ID = "_id";
    public static final String TG_GENRE = "Gatunek";

    private static final String TABLE_BOOKS_GENRE = "Gatunki_Ksiazek";
    public static final String TBG_ID = "_id";
    public static final String TBG_BOOK_ID = "id_Ksiazki";
    public static final String TBG_GENRE_ID = "id_Gatunku";

    public static final int CoverHeight = 300;
    public static final int CoverWidth = 200;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table " + TABLE_BOOKS + " (" +
                TB_ID + " integer primary key autoincrement," +
                TB_TITLE + " text not null," +
                TB_YEAR + " integer not null," +
                TB_DESC + " text," +
                TB_CYKLE + " text," +
                TB_COVER + " blob," +
                TB_FAVORITE + " integer not null default 0," +
                TB_ON_SHELF + " integer not null default 0," +
                TB_WISHES + " integer not null default 0" +
                ");");
        db.execSQL("CREATE TABLE " + TABLE_AUTHOR + " (" +
                TA_ID + " integer primary key autoincrement," +
                TA_FIRST_NAME + " text not null," +
                TA_LAST_NAME + " text not null," +
                TA_BIRTH_DATE + " text," +
                TA_SEX + " text," +
                TA_BIRTH_PLACE + " text," +
                TA_BIO + " text," +
                TA_PHOTO + " blob" +
        ");");
        db.execSQL("CREATE TABLE " + TABLE_AUTHOR_BOOKS + " (" +
                TAB_ID + " integer primary key autoincrement," +
                TAB_AUTHOR_ID + " integer not null," +
                TAB_BOOK_ID + " integer not null," +
                "FOREIGN KEY " + "("+TAB_AUTHOR_ID+")" + " REFERENCES " + TABLE_AUTHOR + "("+TA_ID+")," +
                "FOREIGN KEY " + "("+TAB_BOOK_ID+")" + " REFERENCES " + TABLE_BOOKS + "("+TB_ID+")" +
                ");");
        db.execSQL("CREATE TABLE " + TABLE_GENRE + " (" +
                TG_ID + " integer primary key autoincrement," +
                TG_GENRE + " text not null" +
                ");");
        db.execSQL("CREATE TABLE " + TABLE_BOOKS_GENRE + " (" +
                TBG_ID + " integer primary key autoincrement," +
                TBG_GENRE_ID + " integer not null," +
                TBG_BOOK_ID + " integer not null," +
                "FOREIGN KEY " + "("+TBG_GENRE_ID+")" + " REFERENCES " + TABLE_GENRE + "("+TG_ID+")," +
                "FOREIGN KEY " + "("+TBG_BOOK_ID+")" + " REFERENCES " + TABLE_BOOKS + "("+TB_ID+")" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS_GENRE);
        onCreate(db);
    }

    //Displaying results
    public Cursor ShowAll(){
        SQLiteDatabase db = getReadableDatabase();

/*        String slctQuery = "SELECT " + TB_ID + "." + TABLE_BOOKS + ", " + TB_TITLE +
                ", CONCAT(" + TA_FIRST_NAME + "." + TABLE_AUTHOR + ", ' ', " + TA_LAST_NAME + "." + TABLE_AUTHOR + ") AS Autor, " +
                TB_YEAR + ", " + TB_DESC + ", " + TB_CYKLE + ", " + TB_COVER + ", " + TG_GENRE + "." + TABLE_GENRE +
                " FROM " + TABLE_BOOKS + ", " + TABLE_AUTHOR + ", " + TABLE_GENRE + ";";*/
        String slctQuery = "SELECT k._id, (a.Imię || ' ' || a.Nazwisko) AS Autor, k.Tytuł, k.Rok_wydania, k.Opis, k.Cykl, k.Okładka, g.Gatunek from Ksiazki k " +
                "LEFT JOIN Ksiazki_Autorów ka ON (k._id=ka.id_Ksiazki) " +
                "LEFT JOIN Autor a ON (ka.id_Autora=a._id)" +
                "LEFT JOIN Gatunki_Ksiazek gk ON (k._id=gk.id_Ksiazki) " +
                "LEFT JOIN Gatunek g ON (gk.id_Gatunku=g._id)";

        Cursor cursor = db.rawQuery(slctQuery, null);
        if(cursor != null)
            cursor.move(0);
        return cursor;
    }
    public Cursor ShowSelected (String spinner, String text){
        SQLiteDatabase db = getReadableDatabase();

        String slctQuery = "SELECT k._id, (a.Imię || ' ' || a.Nazwisko) AS Autor, k.Tytuł, k.Rok_wydania, k.Opis, k.Cykl, k.Okładka, g.Gatunek from Ksiazki k " +
                "LEFT JOIN Ksiazki_Autorów ka ON (k._id=ka.id_Ksiazki) " +
                "LEFT JOIN Autor a ON (ka.id_Autora=a._id) " +
                "LEFT JOIN Gatunki_Ksiazek gk ON (k._id=gk.id_Ksiazki) " +
                "LEFT JOIN Gatunek g ON (gk.id_Gatunku=g._id)";

        if(spinner.equals("Tytuł")) slctQuery = slctQuery + " WHERE k.Tytuł LIKE '%" + text + "%';";
        else if (spinner.equals("Autor")) {
            if(text.contains(" ")) {
                String[] parts = text.split(" ");
                String imie = parts[0];
                String nazwisko = parts[1];
                slctQuery = slctQuery + " WHERE (a.Imię LIKE '%" + imie + "%' AND a.Nazwisko LIKE '%" + nazwisko +"%')" +
                        " OR (a.Nazwisko LIKE '%" + imie + "%' AND a.Imię LIKE '%" + nazwisko + "%');";
            }
            else slctQuery = slctQuery + " WHERE a.Imię LIKE '" + text + "' OR a.Nazwisko LIKE '%" + text +"%';";
        }
        else if (spinner.equals("Rok wydania")) slctQuery = slctQuery + " WHERE k.Rok_wydania  LIKE '" + text + "';";
        else if (spinner.equals("Cykl")) slctQuery = slctQuery + " WHERE k.Cykl LIKE '%" + text + "%';";
        else slctQuery = slctQuery + " WHERE g.Gatunek LIKE '%" + text + "&';";

        Cursor cursor = db.rawQuery(slctQuery, null);
        if(cursor != null)
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
        SQLiteDatabase db = getWritableDatabase();

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
        SQLiteDatabase db = getWritableDatabase();

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
        SQLiteDatabase db = getWritableDatabase();

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
        SQLiteDatabase db = getWritableDatabase();

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
        SQLiteDatabase db = getWritableDatabase();

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
        SQLiteDatabase db = getWritableDatabase();

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
    public void AddGenre(String type, long book_id, long author_id){
        SQLiteDatabase db = getWritableDatabase();

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

    public long BoundBook(String text, int n){
        SQLiteDatabase db = getWritableDatabase();
        String slctquery;
        if(n == 0) {
            slctquery = "Select _id from Ksiazki WHERE Tytuł LIKE '" + text + "';";
            Cursor cursor = db.rawQuery(slctquery, null);
            cursor.moveToFirst();
            long id = Integer.valueOf(cursor.getString(0));
            return id;
        }
        else if(n == 1) {
            slctquery = "SELECT a._id from Autor a WHERE (a.Imię || ' ' || a.Nazwisko) LIKE '" + text + "'";
            Cursor cursor = db.rawQuery(slctquery, null);
            cursor.moveToFirst();
            long id = Integer.valueOf(cursor.getString(0));
            return id;
        }
        else {
            slctquery = "SELECT _id from Gatunek WHERE Gatunek LIKE '" + text + "'";
            Cursor cursor = db.rawQuery(slctquery, null);
            cursor.moveToFirst();
            long id = Integer.valueOf(cursor.getString(0));
            return id;
        }
    }

    public boolean IsAlreadyInDatabase(String text, int n){
        SQLiteDatabase db = getReadableDatabase();
        String slctQuery;
        Cursor cursor;
        if(n == 0) {
            slctQuery = "SELECT _id from Ksiazki WHERE Tytuł LIKE '" + text + "'";
            cursor = db.rawQuery(slctQuery,null);
            if(cursor.getCount() > 0) return true;
            else return false;
        }
        else if(n == 1) {
            slctQuery = "SELECT a._id from Autor a WHERE (a.Imię || ' ' || a.Nazwisko) LIKE '" + text + "'";
            cursor = db.rawQuery(slctQuery,null);
            if(cursor.getCount() > 0) return true;
            else return false;
        }
        else {
            slctQuery = "SELECT _id from Gatunek WHERE Gatunek LIKE '" + text + "'";
            cursor = db.rawQuery(slctQuery,null);
            if(cursor.getCount() > 0) return true;
            else return false;
        }
    }

    //Dealing with book covers
    public Bitmap GetImage(Cursor c){
                byte[] image = c.getBlob(6);
        if(image != null) {
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
            if(options.outWidth > CoverWidth && options.outHeight > CoverHeight) {
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
        SQLiteDatabase db = getWritableDatabase();
        String[] ksiazki = {""+id};
        db.delete(TABLE_BOOKS, "_id=?", ksiazki);
    }
    public void UpdateBook(int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Okładka","a180c50d773");
        db.update(TABLE_BOOKS,values,"_id="+id, null);
    }

    public SQLiteDatabase open(){
        SQLiteDatabase db = getWritableDatabase();
        return db;
    }
}
