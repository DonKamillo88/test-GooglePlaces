package com.donkamillo.googleplaces.ui.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.donkamillo.googleplaces.R;
import com.donkamillo.googleplaces.data.DataRepository;
import com.donkamillo.googleplaces.data.DataSource;
import com.donkamillo.googleplaces.data.model.PlaceData;
import com.donkamillo.googleplaces.data.model.RequestPlacesDataModel;
import com.donkamillo.googleplaces.data.remote.RemoteDataSource;
import com.donkamillo.googleplaces.util.PermissionUtils;
import com.donkamillo.googleplaces.util.Utils;

import java.util.Collections;

import static com.donkamillo.googleplaces.ui.main.MainActivity.LOCATION_PERMISSION_ARG;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class MainPresenter implements MainContract.Presenter {
    private static final String REQUEST_TYPE = "bar";
    private static final long REQUEST_RADIUS = 1000;

    private MainContract.View view;
    private DataRepository dataRepository;
    private PlaceData placeData;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        dataRepository = DataRepository.getInstance(RemoteDataSource.getInstance());

    }


    private void getPlaces(final Context context, final double currentLat, final double currentLng) {
        if (view == null) {
            return;
        }

        view.setProgressBar(true);
        RequestPlacesDataModel requestPlacesModelData = new RequestPlacesDataModel(REQUEST_TYPE, REQUEST_RADIUS, currentLat, currentLng);

        dataRepository.getPlaces(context, requestPlacesModelData, new DataSource.GetPlacesCallback() {
            @Override
            public void onSuccess(PlaceData placeData) {
                view.setProgressBar(false);
                view.setInfoViewVisible(false);
                view.setPlacesViewVisible(true);

                addCurrentLocationToCurrentData(placeData, currentLat, currentLng);

                Collections.sort(placeData.getResults());
                MainPresenter.this.placeData = placeData;
            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
                view.setProgressBar(false);
                view.setInfoViewVisible(false);
                view.showErrorMessage(context.getString(R.string.error_msg));
            }

        });
    }

    @Override
    public PlaceData getPlaceData() {
        return placeData;
    }

    @Override
    public void setPlaceData(PlaceData placeData) {
        this.placeData = placeData;
    }

    private void addCurrentLocationToCurrentData(PlaceData placeData, double currentLat, double currentLng) {
        for (PlaceData.Result place : placeData.getResults()) {
            double distance = Utils.getDistance(currentLat, currentLng, place.getGeometry().getLocation());
            place.setDistance(distance);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void updateData(final Context context) {
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locationManager.removeUpdates(this);
                getPlaces(context, location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        view.setProgressBar(true);
        view.setPlacesViewVisible(false);
        view.setInfoViewVisible(false);

        if (PermissionUtils.isLocationGranted(context)) {
            Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (currentLocation == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, locationListener);
            } else {
                getPlaces(context, currentLocation.getLatitude(), currentLocation.getLongitude());
            }
        } else {
            PermissionUtils.requestLocationPermission((Activity) context, LOCATION_PERMISSION_ARG);

        }
    }

}