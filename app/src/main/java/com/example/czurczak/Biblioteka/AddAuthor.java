package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by czurczak on 29.10.2016.
 */

public class AddAuthor extends AppCompatActivity {

    private final static int PICK_IMAGE = 100;
    private final Database db = new Database(this);
    private static ImageView imageView;
    private static byte[] photo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_author);

        getSupportActionBar().setHomeButtonEnabled(true);

        final EditText first_name = (EditText)findViewById(R.id.first_name);
        final EditText last_name = (EditText)findViewById(R.id.last_name);
        final EditText birth_date = (EditText)findViewById(R.id.birth_date);
        final EditText birth_place = (EditText)findViewById(R.id.birth_place);

        final Spinner sex = (Spinner)findViewById(R.id.sex);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, R.layout.spinner_items);
        sex.setAdapter(adapter);
        //sex.setOnItemSelectedListener(this);

        final EditText bio = (EditText)findViewById(R.id.bio);
        imageView = (ImageView)findViewById(R.id.photo);

        photo = null;
        Button choose_photo = (Button)findViewById(R.id.choose_photo);
        choose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseImage();
            }
        });

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBook a = new AddBook();
                Bundle b = getIntent().getExtras();
                String title = b.getString("Tytuł");
                String year = b.getString("Rok wydania");
                String description = b.getString("Opis");
                String cycle = b.getString("Cykl");
                byte[] cover = b.getByteArray("Okładka");
                String genre = b.getString("Gatunek");
                if(a.isEmpty(first_name) == false && a.isEmpty(last_name)==false) {
                    //Jeśli nie ma jeszcze ani takiej ksiazki, ani autora, ani gatunku
                    if(db.IsAlreadyInDatabase(title, 0) == false
                            && db.IsAlreadyInDatabase((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1) == false
                            && db.IsAlreadyInDatabase(genre, 3) == false) {
                        db.AddBook(title,
                            (first_name.getText()).toString(),
                            (last_name.getText()).toString(),
                            Integer.parseInt(year),
                            description,
                            cycle,
                            cover,
                            genre,
                            birth_date.getText().toString(),
                            birth_place.getText().toString(),
                            String.valueOf(sex.getSelectedItem()),
                            bio.getText().toString(),
                            photo);
                    Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    }
                    //Jeśli jest juz taka ksiazkia ale nie ma ani autora, ani gatunku
                    else if(db.IsAlreadyInDatabase(title, 0) == true
                            && db.IsAlreadyInDatabase((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1) == false
                            && db.IsAlreadyInDatabase(genre, 3) == false){
                        db.AddBook((first_name.getText()).toString(),
                                (last_name.getText()).toString(),
                                genre,
                                birth_date.getText().toString(),
                                birth_place.getText().toString(),
                                String.valueOf(sex.getSelectedItem()),
                                bio.getText().toString(),
                                photo,
                                db.BoundBook(title , 0)
                        );
                        Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    //Jeśli nie ma jeszcze ani takiej ksiazki, ani gatunku ale jest autor
                    else if(db.IsAlreadyInDatabase(title, 0) == false
                            && db.IsAlreadyInDatabase((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1) == true
                            && db.IsAlreadyInDatabase(genre, 3) == false){
                        db.AddBook(title,
                                Integer.parseInt(year),
                                description,
                                cycle,
                                cover,
                                genre,
                                db.BoundBook((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1)
                        );
                        Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    //Jeśli nie ma jeszcze ani takiej ksiazki, ani autora ale jest gatunek
                    else if(db.IsAlreadyInDatabase(title, 0) == false
                            && db.IsAlreadyInDatabase((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1) == false
                            && db.IsAlreadyInDatabase(genre, 2) == true){
                        db.AddBook(title,
                                (first_name.getText()).toString(),
                                (last_name.getText()).toString(),
                                Integer.parseInt(year),
                                description,
                                cycle,
                                cover,
                                birth_date.getText().toString(),
                                birth_place.getText().toString(),
                                String.valueOf(sex.getSelectedItem()),
                                bio.getText().toString(),
                                photo,
                                db.BoundBook(genre, 2)
                        );
                        Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    //Jeśli jest juz taka ksiazka i autor ,ale nie ma gatunku
                    else if(db.IsAlreadyInDatabase(title, 0) == true
                            && db.IsAlreadyInDatabase((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1) == true
                            && db.IsAlreadyInDatabase(genre, 3) == false) {
                        db.AddGenre(genre,db.BoundBook(title, 0), db.BoundBook((first_name.getText()).toString() + " " + (last_name.getText()).toString(),1));
                        Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    //Jeśli jest juz taka ksiazka i gatunek ale nie ma autora
                    else if(db.IsAlreadyInDatabase(title, 0) == true
                            && db.IsAlreadyInDatabase((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1) == false
                            && db.IsAlreadyInDatabase(genre, 3) == true) {
                        db.AddBook((first_name.getText()).toString(),
                                (last_name.getText()).toString(),
                                birth_date.getText().toString(),
                                birth_place.getText().toString(),
                                String.valueOf(sex.getSelectedItem()),
                                bio.getText().toString(),
                                photo,
                                db.BoundBook(title, 0),
                                db.BoundBook(genre, 2)
                        );
                        Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    //Jeśli jest juz taki autor i gatunek ale nie ma ksiazki
                    else if(db.IsAlreadyInDatabase(title, 0) == false
                            && db.IsAlreadyInDatabase((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1) == true
                            && db.IsAlreadyInDatabase(genre, 3) == true) {
                        db.AddBook(title,
                                Integer.parseInt(year),
                                description,
                                cycle,
                                cover,
                                db.BoundBook((first_name.getText()).toString() + " " + (last_name.getText()).toString(), 1),
                                db.BoundBook(genre, 2)
                        );
                        Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    //Jeśli wszystko juz jest
                    else {
                        Toast.makeText(getApplicationContext(), "Taka książka znajduje się już w zbiorze", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    db.close();
                }
                else Toast.makeText(getApplicationContext(), "Pola z gwiazdkami są wymagane", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requested, int result, Intent data){
        super.onActivityResult(requested, result, data);
        if(result == RESULT_OK && requested == PICK_IMAGE){
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            photo = db.SaveImageFromGallery(this.getContentResolver(), imageUri);
        }
    }

    public void ChooseImage(){
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }
}