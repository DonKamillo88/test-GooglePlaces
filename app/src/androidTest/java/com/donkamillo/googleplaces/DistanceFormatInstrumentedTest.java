package com.donkamillo.googleplaces;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.donkamillo.googleplaces.util.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by DonKamillo on 25.06.2017.
 */

@RunWith(AndroidJUnit4.class)
public class DistanceFormatInstrumentedTest {

    @Test
    public void getFormattedDistance_close_distance() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        double distance = 0;
        String formattedDistance = Utils.getFormattedDistance(appContext, distance);
        assertEquals("You are here", formattedDistance);
        distance = 5.55;
        formattedDistance = Utils.getFormattedDistance(appContext, distance);
        assertEquals("You are here", formattedDistance);
    }
    @Test
    public void getFormattedDistance_long_distance() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        double distance = 10;
        String formattedDistance = Utils.getFormattedDistance(appContext, distance);
        assertEquals("10 m", formattedDistance);
        distance = 1000.123456789;
        formattedDistance = Utils.getFormattedDistance(appContext, distance);
        assertEquals("1000 m", formattedDistance);
    }
}
