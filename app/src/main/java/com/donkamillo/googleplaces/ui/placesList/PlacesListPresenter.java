package com.donkamillo.googleplaces.ui.placesList;

import com.donkamillo.googleplaces.data.model.PlaceData;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class PlacesListPresenter implements PlacesListContract.Presenter {

    private PlacesListContract.View view;

    public void setView(PlacesListContract.View view) {
        this.view = view;
    }

    @Override
    public void getPlaceSuccess(PlaceData placeData) {
        if (view != null && placeData != null) {
            view.updatePlacesData(placeData.getResults());
        }
    }

}