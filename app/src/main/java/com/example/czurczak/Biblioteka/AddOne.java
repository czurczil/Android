package com.example.czurczak.Biblioteka;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by czurczak on 22.09.2016.
 */
public class AddOne extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_one);

        final Database db = new Database(this);

        final EditText cover = (EditText) findViewById(R.id.cover);
        final EditText title = (EditText) findViewById(R.id.title);
        final EditText author = (EditText) findViewById(R.id.author);
        final EditText year = (EditText) findViewById(R.id.year);
        final EditText description = (EditText) findViewById(R.id.desc);
        final EditText cycle = (EditText) findViewById(R.id.cycle);
        final EditText genre = (EditText) findViewById(R.id.genre);


        Button save = (Button) findViewById(R.id.button4);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(title) == false && isEmpty(author) == false) {
                    db.AddBook((title.getText()).toString(),
                            (author.getText()).toString(),
                            Integer.parseInt((year.getText()).toString()),
                            (description.getText()).toString(),
                            (cycle.getText()).toString(),
                            (cover.getText()).toString(),
                            (genre.getText()).toString());
                    Toast.makeText(getApplicationContext(), "Zapisano do bazy danych", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getApplicationContext(), "Pola z gwiazdkami są wymagane", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }
}