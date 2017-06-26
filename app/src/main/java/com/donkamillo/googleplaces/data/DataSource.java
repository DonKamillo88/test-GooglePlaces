package com.donkamillo.googleplaces.data;

import android.content.Context;

import com.donkamillo.googleplaces.data.model.PlaceData;
import com.donkamillo.googleplaces.data.model.RequestPlacesDataModel;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public abstract class DataSource {

    public interface GetPlacesCallback {

        void onSuccess(PlaceData placeData);

        void onFailure(Throwable throwable);

    }

    public abstract void getPlaces(Context context, RequestPlacesDataModel requestPlacesModelData, GetPlacesCallback callback);

}
