package com.donkamillo.googleplaces.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.donkamillo.googleplaces.ui.placesList.PlacesListFragment;
import com.donkamillo.googleplaces.ui.placesMap.PlacesMapFragment;

/**
 * Created by DonKamillo on 26.06.2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    public static final int LIST_TAB_ID = 0;
    public static final int LIST_MAP_ID = 1;

    public interface PagerListener {
        PlacesListFragment onCreatePlacesListFragment();

        PlacesMapFragment onCreatePlacesMapFragment();

        void onInstantiatePlacesListFragment(Fragment createdFragment);

        void onInstantiatePlacesMapFragment(Fragment createdFragment);
    }

    private PagerListener pagerListener;

    public MainPagerAdapter(FragmentManager fm, PagerListener pagerListener) {
        super(fm);
        this.pagerListener = pagerListener;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case LIST_TAB_ID:
                return pagerListener.onCreatePlacesListFragment();
            case LIST_MAP_ID:
                return pagerListener.onCreatePlacesMapFragment();
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case 0:
                pagerListener.onInstantiatePlacesListFragment(createdFragment);
                break;
            case 1:
                pagerListener.onInstantiatePlacesMapFragment(createdFragment);
                break;
        }
        return createdFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

}
