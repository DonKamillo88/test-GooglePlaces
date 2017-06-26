package com.donkamillo.googleplaces.ui.placesList;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkamillo.googleplaces.R;
import com.donkamillo.googleplaces.data.model.PlaceData;
import com.donkamillo.googleplaces.util.Utils;

import java.util.List;

/**
 * Created by DonKamillo on 24.06.2017.
 */

public class PlacesCardAdapter extends RecyclerView.Adapter<PlacesCardAdapter.PlaceViewHolder> {

    public interface PlaceCardsInterface {
        void onItemClick(PlaceData.Result place);
    }

    private PlaceCardsInterface placeCardsInterface;
    private List<PlaceData.Result> places;
    private Context context;

    public PlacesCardAdapter(Context context, PlaceCardsInterface cardsInterface) {
        this.placeCardsInterface = cardsInterface;
        this.context = context;
    }

    public void updateData(List<PlaceData.Result> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (places == null) return 0;
        return places.size();
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int i) {
        PlaceData.Result place = this.places.get(i);

        holder.name.setText(place.getName());
        holder.distance.setText(Utils.getFormattedDistance(context, place.getDistance()));
        holder.setMainLayoutBackground(i);
        holder.setOnClickListener(place);
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.places_list_item, viewGroup, false);

        return new PlaceViewHolder(itemView);
    }


    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        TextView name, distance;
        ImageView icon;
        RelativeLayout mainLayout;

        public PlaceViewHolder(View v) {
            super(v);

            mainLayout = (RelativeLayout) v.findViewById(R.id.main_layout);
            icon = (ImageView) v.findViewById((R.id.image));
            name = (TextView) v.findViewById(R.id.name);
            distance = (TextView) v.findViewById(R.id.distance);

        }

        private void setMainLayoutBackground(int position) {
            if (position % 2 == 1) {
                mainLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray_bg));
            } else {
                mainLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
        }

        private void setOnClickListener(final PlaceData.Result place) {
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    placeCardsInterface.onItemClick(place);
                }
            });

        }

    }


}