package com.donkamillo.googleplaces.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.donkamillo.googleplaces.R;
import com.donkamillo.googleplaces.data.model.PlaceData;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class Utils {
    public static final int LOCATION_PERMISSION_ARG = 1;
    public static final int MINIMAL_DISTANCE = 10;


    public static void requestLocationPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_ARG);
    }

    public static double getDistance(double currentLat, double currentLng, PlaceData.Location pointLocation) {
        return getDistance(currentLat, pointLocation.getLat(), currentLng, pointLocation.getLng(), 0, 0);
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    public static double getDistance(double lat1, double lat2, double lon1,
                                     double lon2, double el1, double el2) {

        if (lat1 >= 90 || lat2 >= 90 || lat1 <= -90 || lat2 <= -90) {
            return 0;
        }

        if (lon1 >= 180 || lon1 <= -180 || lon2 >= 180 || lon2 <= -180) {
            return 0;
        }

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public static String getFormattedDistance(Context context, double distance) {
        if (distance < MINIMAL_DISTANCE) {
            return context.getString(R.string.you_are_here);
        } else {
            return context.getString(R.string.formatted_distance, (int) distance);
        }

    }

}