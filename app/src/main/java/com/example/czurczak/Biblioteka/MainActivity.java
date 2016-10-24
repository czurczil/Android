package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Database db = new Database(this);
        db.temp();*/


        Button show_all_but = (Button)findViewById(R.id.button);
        show_all_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowResults.class);
                startActivity(intent);
            }
        });

        Button add_one_but = (Button) findViewById(R.id.button2);
        add_one_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddOne.class);
                startActivity(intent);
            }
        });

        Button search_but = (Button) findViewById(R.id.button3);
        search_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });

    }
}
