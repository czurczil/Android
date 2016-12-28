package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBarDrawerToggle toggle;
    private Intent menu_intent;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private AlphaAnimation buttonClick = new AlphaAnimation(1.0F, 0.5F);
    private AlphaAnimation drawerClick = new AlphaAnimation(1.0F, 0.8F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonClick.setDuration(80);

        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new SimpleFragmentPageAdapter(getSupportFragmentManager(), this));

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        db.close();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if (toggle.onOptionsItemSelected(item)) return true;
            else return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        DatabaseAccess db = DatabaseAccess.getInstance(this);
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
                menu_intent = new Intent(getApplicationContext(), ShowBooks.class);
                menu_intent.putExtra("Table", db.TB_ON_SHELF);
                startActivity(menu_intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.my_favorites:
                menu_intent = new Intent(getApplicationContext(), ShowBooks.class);
                menu_intent.putExtra("Table", db.TB_FAVORITE);
                startActivity(menu_intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.wish_list:
                menu_intent = new Intent(getApplicationContext(), ShowBooks.class);
                menu_intent.putExtra("Table", db.TB_WISHES);
                startActivity(menu_intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.add_one:
                menu_intent = new Intent(getApplicationContext(), AddBook.class);
                startActivity(menu_intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.search:
                menu_intent = new Intent(getApplicationContext(), Search.class);
                startActivity(menu_intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            default:
                drawer.closeDrawer(GravityCompat.START);
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickBooks(View v){
        v.startAnimation(buttonClick);
        Intent intent = new Intent (getApplicationContext(), ShowBooksDetails.class);
        String title = ((TextView)(v.findViewById(R.id.tvTitle))).getText().toString();
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    public void onClickAuthors(View view){
        view.startAnimation(buttonClick);
        Intent intent = new Intent (getApplicationContext(), ShowAuthorsDetails.class);
        String autor = ((TextView)(view.findViewById(R.id.authors_name))).getText().toString();
        intent.putExtra("Author", autor);
        startActivity(intent);
    }

    public void onClickGenre(View view){
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        view.startAnimation(buttonClick);
        Intent intent = new Intent (getApplicationContext(), ShowBooks.class);
        String genre = ((TextView)(view.findViewById(R.id.genre_name))).getText().toString();
        String tb = db.TG_GENRE;
        intent.putExtra("Phrase", genre);
        intent.putExtra("Spinner", tb);
        startActivity(intent);
    }

    public void onClickSeries(View view){
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        view.startAnimation(buttonClick);
        Intent intent = new Intent (getApplicationContext(), ShowBooks.class);
        String series = ((TextView)(view.findViewById(R.id.series_name))).getText().toString();
        String tb = db.TB_CYKLE;
        intent.putExtra("Phrase", series);
        intent.putExtra("Spinner", tb);
        startActivity(intent);
    }
}
