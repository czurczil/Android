package com.example.czurczak.test;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by czurczak on 14.09.2016.
 */
public class ShowResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all);
        TextView txt = (TextView)findViewById(R.id.textView);
        txt.setMovementMethod(new ScrollingMovementMethod());

        Database db = new Database(this);
        Cursor k = db.ShowAll();
        k.move(0);
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

}
