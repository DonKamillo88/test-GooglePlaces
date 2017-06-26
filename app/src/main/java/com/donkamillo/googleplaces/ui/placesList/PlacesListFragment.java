package com.donkamillo.googleplaces.ui.placesList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donkamillo.googleplaces.R;
import com.donkamillo.googleplaces.data.model.PlaceData;

import java.util.List;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class PlacesListFragment extends Fragment implements PlacesListContract.View {
    private OnItemSelectedListener onItemSelectedListener;
    private PlacesCardAdapter adapter;
    private List<PlaceData.Result> places;

    public interface OnItemSelectedListener {
        void onItemSelected(PlaceData.Result result);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            onItemSelectedListener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implemenet PlacesListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            onItemSelectedListener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString() + " must implemenet PlacesListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_tab_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new PlacesCardAdapter(getContext(), new PlacesCardAdapter.PlaceCardsInterface() {
            @Override
            public void onItemClick(PlaceData.Result place) {
                onItemSelectedListener.onItemSelected(place);
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (places != null && adapter != null) {
            adapter.updateData(places);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updatePlacesData(List<PlaceData.Result> places) {
        this.places = places;
        if (places != null && adapter != null) {
            adapter.updateData(places);
            adapter.notifyDataSetChanged();
        }
    }

}