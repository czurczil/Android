package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by czurczak on 22.09.2016.
 */
public class AddOne extends AppCompatActivity {

    private final static int PICK_IMAGE = 100;
    private final Database db = new Database(this);
    private static ImageView imageView;
    private static byte[] cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_one);

        //Refresh the gallery
        /*sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse
                ("file://"
                        + Environment.getExternalStorageDirectory())));*/

        final EditText title = (EditText) findViewById(R.id.title);
        final EditText author = (EditText) findViewById(R.id.author);
        final EditText year = (EditText) findViewById(R.id.year);
        final EditText description = (EditText) findViewById(R.id.desc);
        final EditText cycle = (EditText) findViewById(R.id.cycle);
        final EditText genre = (EditText) findViewById(R.id.genre);
        imageView = (ImageView)findViewById(R.id.imageView);


        Button gallery = (Button)findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ChooseImage();
            }
        });


        Button save = (Button) findViewById(R.id.button4);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                if(cover == null)
                    cover =*/
                if(isEmpty(title) == false && isEmpty(author) == false && isEmpty(year) == false) {
                    db.AddBook((title.getText()).toString(),
                            (author.getText()).toString(),
                            Integer.parseInt((year.getText()).toString()),
                            (description.getText()).toString(),
                            (cycle.getText()).toString(),
                            (cover),
                            (genre.getText()).toString());
                    Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                    RestartActivity();
                }
                else Toast.makeText(getApplicationContext(), "Pola z gwiazdkami są wymagane", Toast.LENGTH_SHORT).show();
                cover = null;
            }
        });
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    public void ChooseImage(){
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requested, int result, Intent data){
        super.onActivityResult(requested, result, data);
            if(result == RESULT_OK && requested == PICK_IMAGE){
                Uri imageUri = data.getData();
                imageView.setImageURI(imageUri);
                cover = db.SaveImageFromGallery(this.getContentResolver(), imageUri);
            }
    }

    public void RestartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}