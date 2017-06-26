package com.donkamillo.googleplaces.data;

import android.content.Context;

import com.donkamillo.googleplaces.data.model.RequestPlacesDataModel;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class DataRepository {

    private DataSource remoteDataSource;

    private static DataRepository dataRepository;

    public DataRepository(DataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static synchronized DataRepository getInstance(DataSource remoteDataSource) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(remoteDataSource);
        }
        return dataRepository;
    }

    public void getPlaces(Context context, RequestPlacesDataModel requestPlacesModelData, final DataSource.GetPlacesCallback callback) {
        remoteDataSource.getPlaces(context, requestPlacesModelData, callback);
    }
}
