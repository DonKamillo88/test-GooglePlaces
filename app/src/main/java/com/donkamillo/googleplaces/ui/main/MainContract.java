package com.donkamillo.googleplaces.ui.main;

import android.content.Context;

import com.donkamillo.googleplaces.data.model.PlaceData;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public interface MainContract {

    interface View {

        void setProgressBar(boolean b);

        void showErrorMessage(String message);

        void setPlacesViewVisible(boolean isVisible);

        void setInfoViewVisible(boolean isVisible);
    }

    interface Presenter {

        void updateData(Context context);

        PlaceData getPlaceData();

        void setPlaceData(PlaceData placeData);

    }

}
