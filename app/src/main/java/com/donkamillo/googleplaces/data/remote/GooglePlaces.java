package com.donkamillo.googleplaces.data.remote;

import com.donkamillo.googleplaces.data.model.PlaceData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public interface GooglePlaces {

    @GET("/maps/api/place/nearbysearch/json?")
    Call<PlaceData> getPlaces(@Query("location") String latitudeLongitude, @Query("radius") long radius, @Query("types") String types, @Query("key") String key);


}
