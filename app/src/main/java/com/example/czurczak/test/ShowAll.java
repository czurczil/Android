package com.example.czurczak.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by czurczak on 14.09.2016.
 */
public class ShowAll extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all);

        TextView txt = (TextView)findViewById(R.id.textView);
        txt.setMovementMethod(new ScrollingMovementMethod());
        Database db = new Database(this);

        db.ShowAll(txt);

    }

}
