package com.donkamillo.googleplaces.data.model;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class RequestPlacesDataModel {

    private String types;
    private long radius;
    private double lat, lng;

    public RequestPlacesDataModel(String types, long radius, double lat, double lng) {
        this.types = types;
        this.radius = radius;
        this.lat = lat;
        this.lng = lng;
    }

    public String getTypes() {
        return types;
    }

    public long getRadius() {
        return radius;
    }

    public String getLatitudeLongitude() {
        return lat + "," + lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
