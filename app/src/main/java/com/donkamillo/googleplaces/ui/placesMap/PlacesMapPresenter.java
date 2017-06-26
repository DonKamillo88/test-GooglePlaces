package com.donkamillo.googleplaces.ui.placesMap;

import com.donkamillo.googleplaces.data.model.PlaceData;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class PlacesMapPresenter implements PlacesMapContract.Presenter {

    private PlacesMapContract.View view;

    public void setView(PlacesMapContract.View view) {
        this.view = view;
    }

    @Override
    public void getPlaceSuccess(PlaceData placeData) {
        if (view != null) view.updatePlacesData(placeData.getResults());
    }

    @Override
    public void setSelectPlace(PlaceData.Result result) {
        view.selectMarker(result);
    }
}