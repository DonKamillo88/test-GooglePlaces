package com.donkamillo.googleplaces.ui.placesMap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donkamillo.googleplaces.R;
import com.donkamillo.googleplaces.data.model.PlaceData;
import com.donkamillo.googleplaces.util.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class PlacesMapFragment extends Fragment implements PlacesMapContract.View,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener {

    public static final int CAMERA_ANIMATION_DURATION = 2000;
    public static final float CAMERA_ZOOM = 15.5f;

    private Marker mSelectedMarker;
    private GoogleMap mMap;
    private MapView mapView;
    private List<PlaceData.Result> places;
    private Map<PlaceData.Result, Marker> placeMarkerMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placeMarkerMap = new HashMap<>();

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        if (places != null) {
            addMarkersToMap(places);
        }
    }

    @Override
    public void updatePlacesData(List<PlaceData.Result> result) {

        this.places = result;
        if (mMap != null) {
            addMarkersToMap(result);
        }
    }

    @Override
    public void selectMarker(PlaceData.Result result) {
        Marker marker = placeMarkerMap.get(result);

        if (marker != null) {
            marker.showInfoWindow();

            CameraPosition newCamPos = new CameraPosition(marker.getPosition(),
                    CAMERA_ZOOM, mMap.getCameraPosition().tilt, mMap.getCameraPosition().bearing);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), CAMERA_ANIMATION_DURATION, null);

        }
    }

    public void addMarkersToMap(List<PlaceData.Result> places) {
        for (PlaceData.Result place : places) {
            LatLng latLng = new LatLng(place.getGeometry().getLocation().getLat(), place.getGeometry().getLocation().getLng());

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(place.getName())
                    .snippet(Utils.getFormattedDistance(getContext(), place.getDistance())));
            placeMarkerMap.put(place, marker);
        }
    }


    @Override
    public void onMapClick(final LatLng point) {
        mSelectedMarker = null;
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(mSelectedMarker)) {
            mSelectedMarker = null;
            return true;
        }
        mSelectedMarker = marker;
        return false;
    }

}