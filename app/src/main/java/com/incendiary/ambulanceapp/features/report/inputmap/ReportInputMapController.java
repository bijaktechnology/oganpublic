package com.incendiary.ambulanceapp.features.report.inputmap;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.events.EventLocationReady;
import com.incendiary.ambulanceapp.data.events.EventLocationResolveRequest;
import com.incendiary.ambulanceapp.data.model.exceptions.LocationResolutionException;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.location.LocationManager;
import com.incendiary.ambulanceapp.features.report.etc.ReportSuccessController;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.EBus;
import com.incendiary.ambulanceapp.utils.MapUtils;
import com.incendiary.ambulanceapp.utils.conductor.Controllers;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.rx.RxGoogleMap;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.Toasts;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;

import static com.incendiary.ambulanceapp.R.id.map;

public class ReportInputMapController extends AbsController implements ReportInputMapView {

    private static final String ARG_REPORT_CAT_ID = "ReportInputMap.Report.CatId";
    private static final String ARG_REPORT_TITLE = "ReportInputMap.Report.Title";
    private static final String ARG_REPORT_DESC = "ReportInputMap.Report.Desc";

    @BindView(R.id.map_input_txt_address) TextView txtAddress;
    @BindView(R.id.map_input_btn_my_location) FloatingActionButton btnMyLocation;
    @BindView(R.id.map_input_btn_done) View btnDone;

    @Inject ReportInputMapPresenter presenter;
    @Inject LocationManager locationManager;
    @Inject ProgressManager progressManager;

    private GoogleMap googleMap;

    public ReportInputMapController(String categoryId, String title, String desc) {
        this(new BundleBuilder()
                .putString(ARG_REPORT_CAT_ID, categoryId)
                .putString(ARG_REPORT_TITLE, title)
                .putString(ARG_REPORT_DESC, desc)
                .build());
    }

    public ReportInputMapController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_input_map;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);
        Controllers.bindEventBus(this);
        initData();
    }

    private void initData() {
        progressManager.show(true);

        locationManager.getOnceLocation()
                .doAfterTerminate(() -> progressManager.show(false))
                .forEach(this::showData, throwable -> {
                    ErrorHandler.handleError(throwable);

                    if (throwable instanceof LocationResolutionException) {
                        LocationResolutionException exception = (LocationResolutionException) throwable;
                        EBus.post(new EventLocationResolveRequest(exception.getStatus()));
                    }
                });
    }

    private void showData(Location location) {
        showMap(location);
    }

    private void showMap(Location location) {
        Controllers.findFragment(this, map, fragment -> {
            RxGoogleMap.bind((SupportMapFragment) fragment)
                    .compose(bindToLifecycle())
                    .subscribe(map -> {

                        googleMap = map;
                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setMapToolbarEnabled(false);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                        btnDone.setOnClickListener(v -> {
                            LatLng center = map.getCameraPosition().target;
                            String catId = getArgs().getString(ARG_REPORT_CAT_ID);
                            String title = getArgs().getString(ARG_REPORT_TITLE);
                            String desc = getArgs().getString(ARG_REPORT_DESC);

                            presenter.submitReport(center,
                                    catId,
                                    title,
                                    desc);
                        });

                        MapUtils.moveMap(map,
                                location.getLatitude(),
                                location.getLongitude());

                        final int RADIUS = 1000; // 1km

                        googleMap.addCircle(new CircleOptions()
                                .center(new LatLng(
                                        location.getLatitude(),
                                        location.getLongitude()
                                ))
                                .strokeWidth(2)
                                .strokeColor(ContextProvider.getColor(R.color.colorBlue))
                                .fillColor(ContextProvider.getColor(R.color.blueTransparent))
                                .radius(RADIUS));

                        googleMap.setOnCameraChangeListener(cameraPosition -> {
                            final LatLng center = cameraPosition.target;
                            final double distance = MapUtils.calculateDistance(
                                    location.getLatitude(), location.getLongitude(), center.latitude, center.longitude);

                            if (distance > RADIUS) {
                                MapUtils.moveMap(map,
                                        location.getLatitude(),
                                        location.getLongitude());
                                Toasts.show("Lokasi harus berada di dalam radius");
                            } else {
                                showAddress(center);
                            }
                        });

                        btnMyLocation.setOnClickListener(v ->
                                MapUtils.moveMap(map, location.getLatitude(), location.getLongitude()));

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

    @Override
    public void showSuccess(String message) {
        Toasts.show(message);
        getRouter().replaceTopController(Routes.simpleTransaction(
                new ReportSuccessController(),
                new HorizontalChangeHandler()
        ));
    }

    @Override
    public void showAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAddressLine(1);

            txtAddress.setText(String.format("%s %s", address, city));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int i) {
        progressManager.show(isLoading);
    }
}
