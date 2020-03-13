package com.incendiary.ambulanceapp.features.tours;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.location.LocationFactory;
import com.incendiary.ambulanceapp.features.location.LocationManager;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.androidcommon.android.text.Strings;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;

public class TourDetailController extends AbsController {

    private static final String ARG_PLACE = "TourDetail.Place";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tour_detail_img_header) ImageView imgHeader;
    @BindView(R.id.tour_detail_txt_content) TextView txtContent;
    @BindView(R.id.tour_detail_txt_title) TextView txtTitle;
    @BindView(R.id.tour_detail_txt_description) TextView txtDescription;
    @BindView(R.id.tour_detail_txt_distance) TextView txtDistance;
    @BindView(R.id.tour_detail_btn_direction) View btnDirection;

    @Inject Context context;
    @Inject LocationManager locationManager;

    public TourDetailController(TourPlace tourPlace) {
        this(new BundleBuilder()
                .putParcelable(ARG_PLACE, tourPlace)
                .build());
    }

    public TourDetailController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_tour_detail;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        final TourPlace tourPlace = getArgs().getParcelable(ARG_PLACE);
        Preconditions.checkNotNull(tourPlace);

        txtTitle.setText(tourPlace.getNamaDestinasiWisata());
        txtDescription.setText(tourPlace.getAlamat());
        txtDistance.setText(Strings.format("%s KM", tourPlace.getDistance()));

        Glide.with(context)
                .load(tourPlace.getImage())
                .into(imgHeader);

        txtContent.setText(tourPlace.getDeskripsi());

        btnDirection.setOnClickListener(v -> goToGoogleMap(tourPlace.getLat(), tourPlace.getLng()));
    }

    private void goToGoogleMap(String lat, String lng) {
        locationManager.getCurrentLocation()
                .onErrorReturn(throwable -> LocationFactory.newEmptyLocation())
                .subscribe(location -> {

                    final String origin = Strings.format("%s,%s",
                            String.valueOf(location.getLatitude()),
                            String.valueOf(location.getLongitude()));

                    final String dest = Strings.format("%s,%s", lat, lng);

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(String.format("http://maps.google.com/maps?saddr=%s&daddr=%s", origin, dest)));
                    startActivity(intent);

                }, ErrorHandler::handleError);
    }
}
