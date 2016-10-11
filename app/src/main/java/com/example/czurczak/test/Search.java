package com.example.czurczak.test;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by czurczak on 22.09.2016.
 */
public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        final EditText phrase = (EditText)findViewById(R.id.editText);


        final Database db = new Database(this);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_array, R.layout.spinner_items);

        // Specify the layout to use when the list of choices appears
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        Button ok = (Button)findViewById(R.id.button5);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView text = (TextView) findViewById(R.id.textView2);
                text.setMovementMethod(new ScrollingMovementMethod());
                Cursor k = db.ShowSelected(String.valueOf(spinner.getSelectedItem()), (phrase.getText().toString()));
                k.move(0);
                text.setText("");
                while(k.moveToNext()){
                    int id = k.getInt(0);
                    String title = k.getString(1);
                    String author = k.getString(2);
                    int year = k.getInt(3);
                    String desc = k.getString(4);
                    String cycle = k.getString(5);
                    String cover = k.getString(6);
                    String type = k.getString(7);
                    text.setText(text.getText() + "\n" + id + " | " + title + " | " +  author + " | " +
                            year + " | " +  desc + " | " + cycle + " | " + cover + " | " + type +"\n");
                }
                k.close();
            }
        });



    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
