package com.donkamillo.googleplaces.ui.placesMap;

import com.donkamillo.googleplaces.data.model.PlaceData;

import java.util.List;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public interface PlacesMapContract {

    interface View {

        void updatePlacesData(List<PlaceData.Result> result);

        void selectMarker(PlaceData.Result result);
    }

    interface Presenter {

        void getPlaceSuccess(PlaceData placeData);

        void setSelectPlace(PlaceData.Result result);
    }

}
