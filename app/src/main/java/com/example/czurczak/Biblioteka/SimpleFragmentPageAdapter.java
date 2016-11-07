package com.example.czurczak.Biblioteka;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by czurczak on 06.11.2016.
 */

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {
    private String[] titles = new String[] {"Książki", "Autorzy", "Ulubione", "Na mojej pólce", "Do przeczytania"};
    private Context context;
    private int pagecount = 5;
    public SimpleFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        Fragment fragment;
        if (position == 0){
           fragment = new BooksFragment();
        }

        else if (position == 1){
            fragment = new AuthorsFragment();
        }
        else if (position == 2){
            bundle.putString("Tabela", "Ulubione");
            fragment = new BooksFragment();
            fragment.setArguments(bundle);
        }
        else if(position == 3){
            bundle.putString("Tabela", "Na_polce");
            fragment = new BooksFragment();
            fragment.setArguments(bundle);
        }
        else {
            bundle.putString("Tabela", "Do_przeczytania");
            fragment = new BooksFragment();
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return pagecount;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }
}
