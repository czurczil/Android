package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private Intent menu_intent;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        Button show_all_books_but = (Button)findViewById(R.id.button);
        show_all_books_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowBooks.class);
                startActivity(intent);
            }
        });

        Button show_all_authors_but = (Button)findViewById(R.id.button4);
        show_all_authors_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowAuthors.class);
                startActivity(intent);
            }
        });

        Button add_one_but = (Button) findViewById(R.id.button2);
        add_one_but.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBook.class);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if (toggle.onOptionsItemSelected(item)) return true;
            else return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.all_books:
                menu_intent = new Intent(getApplicationContext(), ShowBooks.class);
                startActivity(menu_intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.all_authors:
                menu_intent = new Intent(getApplicationContext(), ShowAuthors.class);
                startActivity(menu_intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.my_shelf:
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.my_favorites:
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.wish_list:
                drawer.closeDrawer(GravityCompat.START);
                return true;
            default:
                drawer.closeDrawer(GravityCompat.START);
                return super.onOptionsItemSelected(item);
        }
    }
}
