package com.donkamillo.googleplaces.ui.placesList;

import com.donkamillo.googleplaces.data.model.PlaceData;

import java.util.List;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public interface PlacesListContract {

    interface View {

        void updatePlacesData(List<PlaceData.Result> result);

    }

    interface Presenter {

        void getPlaceSuccess(PlaceData placeData);
    }

}
