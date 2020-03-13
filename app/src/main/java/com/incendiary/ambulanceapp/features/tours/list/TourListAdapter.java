package com.incendiary.ambulanceapp.features.tours.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.adapters.BaseListAdapter;
import com.incendiary.ambulanceapp.common.adapters.BaseViewHolder;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;
import com.incendiary.androidcommon.android.text.Strings;

import javax.inject.Inject;

import butterknife.BindView;

public class TourListAdapter extends BaseListAdapter<TourPlace> {

    @Inject
    public TourListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBind(RecyclerView.ViewHolder holder, int position) {
        final TourViewHolder vh = (TourViewHolder) holder;
        final TourPlace tourPlace = getItem(position);

        vh.txtTitle.setText(tourPlace.getNamaDestinasiWisata());
        vh.txtDescription.setText(tourPlace.getAlamat());
        vh.txtDistance.setText(Strings.format("%s KM", tourPlace.getDistance()));

        Glide.with(getContext())
                .load(tourPlace.getImage())
                .into(vh.imgBackground);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TourViewHolder(
                getInflater().inflate(R.layout.item_tours, parent, false)
        );
    }

    static class TourViewHolder extends BaseViewHolder {

        @BindView(R.id.item_tours_img) ImageView imgBackground;
        @BindView(R.id.item_tours_txt_title) TextView txtTitle;
        @BindView(R.id.item_tours_txt_description) TextView txtDescription;
        @BindView(R.id.item_tours_txt_distance) TextView txtDistance;

        public TourViewHolder(View itemView) {
            super(itemView);
        }
    }
}
