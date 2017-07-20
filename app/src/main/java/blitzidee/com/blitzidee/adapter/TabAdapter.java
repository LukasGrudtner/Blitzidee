package blitzidee.com.blitzidee.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import blitzidee.com.blitzidee.fragments.AnimesFragment;
import blitzidee.com.blitzidee.fragments.BooksFragment;
import blitzidee.com.blitzidee.fragments.IdeasFragment;

/**
 * Created by lukas on 19/07/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles = {"IDEAS", "BOOKS", "ANIMES"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new IdeasFragment();
                break;
            case 1:
                fragment = new BooksFragment();
                break;
            case 2:
                fragment = new AnimesFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
