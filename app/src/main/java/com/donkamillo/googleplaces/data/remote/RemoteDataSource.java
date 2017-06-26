package com.donkamillo.googleplaces.data.remote;

import android.content.Context;

import com.donkamillo.googleplaces.R;
import com.donkamillo.googleplaces.data.DataSource;
import com.donkamillo.googleplaces.data.model.PlaceData;
import com.donkamillo.googleplaces.data.model.RequestPlacesDataModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class RemoteDataSource extends DataSource {

    private static RemoteDataSource remoteDataSource;

    public static synchronized RemoteDataSource getInstance() {
        if (remoteDataSource == null) {
            remoteDataSource = new RemoteDataSource();
        }
        return remoteDataSource;
    }


    @Override
    public void getPlaces(Context context, RequestPlacesDataModel requestPlacesModelData, final GetPlacesCallback callback) {
        Call<PlaceData> call;
        GooglePlaces service = GooglePlacesService.getService();

        String apiKey = context.getString(R.string.google_places_api_key);

        call = service.getPlaces(requestPlacesModelData.getLatitudeLongitude(), requestPlacesModelData.getRadius(), requestPlacesModelData.getTypes(), apiKey);
        call.enqueue(new Callback<PlaceData>() {
            @Override
            public void onResponse(Call<PlaceData> call, Response<PlaceData> placeDataResponse) {
                callback.onSuccess(placeDataResponse.body());
            }

            @Override
            public void onFailure(Call<PlaceData> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}