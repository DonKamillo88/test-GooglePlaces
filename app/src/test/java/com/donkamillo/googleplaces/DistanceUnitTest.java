package com.donkamillo.googleplaces;

import com.donkamillo.googleplaces.util.Utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by DonKamillo on 25.06.2017.
 */

public class DistanceUnitTest {

    @Test
    public void getDistance_same_places() throws Exception {

        double lat1 = 0;
        double lat2 = 0;
        double lng1 = 0;
        double lng2 = 0;

        double distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(0, distance, 1);

        lat1 = 50.2;
        lat2 = 50.2;
        lng1 = 10;
        lng2 = 10;

        distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(0, distance, 1);
    }

    @Test
    public void getDistance_different_places() throws Exception {

        double lat1 = 51.48050061;
        double lat2 = 51.4847115802915;
        double lng1 = 0.01071881;
        double lng2 = 0.00916773029150203;

        double distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(480, distance, 1);

        lat1 = 51.52000061;
        lat2 = 51.5047115802915;
        lng1 = 0.05071881;
        lng2 = -0.05916773029150203;

        distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(7792, distance, 1);
    }

    @Test
    public void getDistance_wrong_data() throws Exception {

        double lat1 = 95;
        double lat2 = 51.4847115802915;
        double lng1 = 0.01071881;
        double lng2 = 0.00916773029150203;

        double distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(0, distance, 1);

        lat1 = 51.48050061;
        lat2 = -100;
        lng1 = 0.01071881;
        lng2 = 0.00916773029150203;

        distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(0, distance, 1);

        lat1 = 51.48050061;
        lat2 = 51.4847115802915;
        lng1 = -200;
        lng2 = 0.00916773029150203;

        distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(0, distance, 1);

        lat1 = 51.48050061;
        lat2 = 51.4847115802915;
        lng1 = 0.01071881;
        lng2 = 40000;

        distance = Utils.getDistance(lat1, lat2, lng1, lng2, 0, 0);
        assertEquals(0, distance, 1);
    }
}
