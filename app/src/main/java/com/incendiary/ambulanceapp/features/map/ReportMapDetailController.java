package com.incendiary.ambulanceapp.features.map;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.events.EventLocationReady;
import com.incendiary.ambulanceapp.data.events.EventLocationResolveRequest;
import com.incendiary.ambulanceapp.data.model.exceptions.LocationResolutionException;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.data.model.report.Reports;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.location.LocationManager;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.EBus;
import com.incendiary.ambulanceapp.utils.MapUtils;
import com.incendiary.ambulanceapp.utils.conductor.Controllers;
import com.incendiary.ambulanceapp.utils.rx.RxGoogleMap;
import com.incendiary.androidcommon.android.text.Strings;
import com.incendiary.androidcommon.utils.Preconditions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;

import static com.incendiary.ambulanceapp.R.id.map;

public class ReportMapDetailController extends AbsController {

    private static final String ARG_REPORT = "ReportMap.Report";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.map_detail_txt_title) TextView txtTitle;
    @BindView(R.id.map_detail_txt_address_time) TextView txtAddressTime;
    @BindView(R.id.map_detail_txt_category) TextView txtCategory;
    @BindView(R.id.map_detail_img_category) ImageView imgCategory;
    @BindView(R.id.map_detail_txt_status) TextView txtStatus;
    @BindView(R.id.map_detail_btn_my_location) FloatingActionButton btnMyLocation;

    @Inject LocationManager locationManager;
    @Inject ProgressManager progressManager;

    public ReportMapDetailController(Report report) {
        this(new BundleBuilder()
                .putParcelable(ARG_REPORT, report)
                .build());
    }

    public ReportMapDetailController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_map;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(R.string.map);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        Controllers.bindEventBus(this);
        initData();
    }

    private void initData() {
        progressManager.show(true);

        locationManager.getOnceLocation()
                .doAfterTerminate(() -> progressManager.show(false))
                .forEach(location -> {
                    showData(
                            Preconditions.checkNotNull(getArgs().getParcelable(ARG_REPORT)),
                            location
                    );
                }, throwable -> {
                    ErrorHandler.handleError(throwable);

                    if (throwable instanceof LocationResolutionException) {
                        LocationResolutionException exception = (LocationResolutionException) throwable;
                        EBus.post(new EventLocationResolveRequest(exception.getStatus()));
                    }
                });
    }

    private void showData(Report report, Location location) {
        txtTitle.setText(report.getJudul());
        txtCategory.setText(report.getNamaKategori());

        txtStatus.setText(Reports.getStatusText(report));
        txtStatus.setBackgroundResource(Reports.getStatusBackground(report));

        txtAddressTime.setText(Strings.format("%s\n%s", Reports.getReportLocation(report), Reports.getRelativeTime(report)));

        Glide.with(getActivity())
                .load(report.getIcon())
                .into(imgCategory);

        showMap(report, location);
    }

    private void showMap(Report report, Location location) {
        Controllers.findFragment(this, map, fragment -> {
            RxGoogleMap.bind((SupportMapFragment) fragment)
                    .compose(bindToLifecycle())
                    .subscribe(googleMap -> {

                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setMapToolbarEnabled(false);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(
                                        Double.valueOf(report.getLat()),
                                        Double.valueOf(report.getLng())
                                )));

                        MapUtils.moveMap(googleMap,
                                Double.valueOf(report.getLat()),
                                Double.valueOf(report.getLng()));

                        btnMyLocation.setOnClickListener(v ->
                                MapUtils.moveMap(googleMap, location.getLatitude(), location.getLongitude()));

                    });
        });
    }

    @Override
    protected void onDetach(@NonNull View view) {
        Controllers.removeFragment(this, R.id.map);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationReady(EventLocationReady locationReady) {
        initData();
    }
}
