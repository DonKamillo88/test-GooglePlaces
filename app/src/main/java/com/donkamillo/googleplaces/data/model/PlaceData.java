package com.donkamillo.googleplaces.data.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class PlaceData implements Serializable {

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result implements Serializable, Comparable {

        private Geometry geometry;
        private String icon;
        private String name;
        private double distance;


        @Override
        public int compareTo(@NonNull Object o) {
            return (int) (distance - ((Result) o).distance);
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }


    }

    public class Geometry implements Serializable {
        private Location location;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    public class Location implements Serializable {
        private float lat;
        private float lng;

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }
    }
}
