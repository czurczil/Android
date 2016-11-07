package com.example.czurczak.Biblioteka;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ActionBarDrawerToggle toggle;
    private Intent menu_intent;
    private DrawerLayout drawer;
    private Database db = new Database(this);
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

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
        Intent intent = new Intent (getApplicationContext(), ShowBooksDetails.class);
        String title = ((TextView)(v.findViewById(R.id.tvTitle))).getText().toString();
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    public void onClickAuthors(View view){
        Intent intent = new Intent (getApplicationContext(), ShowAuthorsDetails.class);
        String autor = ((TextView)(view.findViewById(R.id.authors_name))).getText().toString();
        intent.putExtra("Author", autor);
        startActivity(intent);
    }
}
