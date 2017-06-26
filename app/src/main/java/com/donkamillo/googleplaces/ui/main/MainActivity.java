package com.donkamillo.googleplaces.ui.main;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.donkamillo.googleplaces.R;
import com.donkamillo.googleplaces.data.model.PlaceData;
import com.donkamillo.googleplaces.ui.placesList.PlacesListFragment;
import com.donkamillo.googleplaces.ui.placesList.PlacesListPresenter;
import com.donkamillo.googleplaces.ui.placesMap.PlacesMapFragment;
import com.donkamillo.googleplaces.ui.placesMap.PlacesMapPresenter;
import com.donkamillo.googleplaces.util.PermissionUtils;

import static com.donkamillo.googleplaces.ui.main.MainPagerAdapter.LIST_MAP_ID;


public class MainActivity extends AppCompatActivity implements PlacesListFragment.OnItemSelectedListener, MainContract.View {
    private static final String PLACE_DATA_SAVE_INSTANCE_ARG = "place_data_save_instance";
    public static final int LOCATION_PERMISSION_ARG = 1;

    private LinearLayout tabContainer, infoContainer;
    private Button grantPermissionBtn;
    private ProgressBar progressBar;
    private TextView errorMsg;

    private MainPresenter mainPresenter;
    private PlacesListPresenter placesListPresenter;
    private PlacesMapPresenter placesMapPresenter;

    private PlacesListFragment placesListFragment;
    private PlacesMapFragment placesMapFragment;
    private ViewPager viewPager;
    private PlaceData placeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabContainer = (LinearLayout) findViewById(R.id.tab_container);
        infoContainer = (LinearLayout) findViewById(R.id.info_container);
        grantPermissionBtn = (Button) findViewById(R.id.grant_permission_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        errorMsg = (TextView) findViewById(R.id.error_msg);

        setSupportActionBar(toolbar);

        grantPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.requestLocationPermission(MainActivity.this, LOCATION_PERMISSION_ARG);
            }
        });

        setupTabs();


        placesListPresenter = new PlacesListPresenter();
        placesMapPresenter = new PlacesMapPresenter();

        mainPresenter = new MainPresenter(this);


        if (savedInstanceState != null) {
            placeData = (PlaceData) savedInstanceState.getSerializable(PLACE_DATA_SAVE_INSTANCE_ARG);
        }
        if (placeData == null) {
            mainPresenter.updateData(MainActivity.this);
        } else {
            mainPresenter.setPlaceData(placeData);
            setProgressBar(false);
            setPlacesViewVisible(true);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(PLACE_DATA_SAVE_INSTANCE_ARG, mainPresenter.getPlaceData());
        super.onSaveInstanceState(outState);
    }

    private void setupTabs() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.bar_list));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.bar_map));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(getMainPagerAdapter());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @NonNull
    private MainPagerAdapter getMainPagerAdapter() {
        return new MainPagerAdapter(getSupportFragmentManager(), new MainPagerAdapter.PagerListener() {
            @Override
            public PlacesListFragment onCreatePlacesListFragment() {
                return new PlacesListFragment();
            }

            @Override
            public PlacesMapFragment onCreatePlacesMapFragment() {
                return new PlacesMapFragment();
            }

            @Override
            public void onInstantiatePlacesListFragment(Fragment createdFragment) {
                placesListFragment = (PlacesListFragment) createdFragment;
                placesListPresenter.setView(placesListFragment);
                placesListPresenter.getPlaceSuccess(mainPresenter.getPlaceData());
            }

            @Override
            public void onInstantiatePlacesMapFragment(Fragment createdFragment) {
                placesMapFragment = (PlacesMapFragment) createdFragment;
                placesMapPresenter.setView(placesMapFragment);
                placesMapPresenter.getPlaceSuccess(mainPresenter.getPlaceData());
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_ARG: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (PermissionUtils.isLocationGranted(this)) {
                        mainPresenter.updateData(MainActivity.this);
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    tabContainer.setVisibility(View.GONE);
                    infoContainer.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onItemSelected(PlaceData.Result result) {
        viewPager.setCurrentItem(LIST_MAP_ID);
        placesMapPresenter.setSelectPlace(result);
    }

    @Override
    public void setProgressBar(boolean b) {
        progressBar.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMessage(String message) {
        errorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPlacesViewVisible(boolean b) {
        tabContainer.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setInfoViewVisible(boolean b) {
        infoContainer.setVisibility(b ? View.VISIBLE : View.GONE);
    }

}