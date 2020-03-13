package com.incendiary.ambulanceapp.features.booking;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.events.EventLocationReady;
import com.incendiary.ambulanceapp.data.model.BookingResponse;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;
import com.incendiary.ambulanceapp.data.model.poi.PoiCategory;
import com.incendiary.ambulanceapp.features.booking.map.MapMarkerManager;
import com.incendiary.ambulanceapp.features.booking.map.overlay.AmbulanceMapOverlay;
import com.incendiary.ambulanceapp.features.booking.map.overlay.AtmBerasOverlay;
import com.incendiary.ambulanceapp.features.booking.map.overlay.MapOverlayManager;
import com.incendiary.ambulanceapp.features.booking.map.overlay.MedPersonOverlay;
import com.incendiary.ambulanceapp.features.booking.map.overlay.PoiOverlay;
import com.incendiary.ambulanceapp.features.booking.waiting.BookingWaitController;
import com.incendiary.ambulanceapp.features.booking.waiting.BookingWaitProps;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.MapUtils;
import com.incendiary.ambulanceapp.utils.conductor.Controllers;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.rx.RxGoogleMap;
import com.incendiary.ambulanceapp.utils.rx.RxSpinner;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.Animates;
import com.incendiary.androidcommon.etc.Values;
import com.incendiary.androidcommon.utils.Preconditions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Lazy;

import static com.incendiary.ambulanceapp.R.id.map;

public class BookingController extends AbsController
        implements BookingView, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String ARG_MAP_TYPE = "MapType";

    @BindView(R.id.booking_btn_my_location) View btnLocation;
    @BindView(R.id.booking_spinner_container) View spinnerContainer;
    @BindView(R.id.booking_spinner_location_type) Spinner spinnerType;
    @BindView(R.id.booking_btn_book) Button btnBooking;

    /* Marker Window */
    @BindView(R.id.lyMarker) ViewGroup markerContainer;

    /* Loading */
    @BindView(R.id.pbLoad) View loadingView;
    @BindView(R.id.lyContent) View bookingLoadingView;

    @Inject BookingPresenter presenter;
    @Inject MapMarkerManager markerManager;
    @Inject ProgressManager progressManager;
    @Inject MapOverlayManager mapOverlayManager;
    @Inject Lazy<PoiCategoryAdapter> spinnerAdapter;

    private GoogleMap googleMap;
    private int mapType = MarkerType.AMBULANCE;

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    public BookingController(int mapType) {
        this(new BundleBuilder()
                .putInt(ARG_MAP_TYPE, mapType)
                .build());
    }

    public BookingController(Bundle args) {
        super(args);
    }

    /* --------------------------------------------------- */
    /* > Configuration */
    /* --------------------------------------------------- */

    @Override
    protected void onInit() {
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_booking;
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    /* --------------------------------------------------- */

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);
        Controllers.bindEventBus(this);

         /* Init Overlay */
        mapOverlayManager.setViewGroup(markerContainer);
        mapOverlayManager.addOverlay(new AtmBerasOverlay());
        mapOverlayManager.addOverlay(new AmbulanceMapOverlay());
        mapOverlayManager.addOverlay(new MedPersonOverlay());
        mapOverlayManager.addOverlay(new PoiOverlay(poi -> presenter.directionToPoi(poi)));

        mapType = getArgs().getInt(ARG_MAP_TYPE, MarkerType.AMBULANCE);
        presenter.init(mapType);

        btnBooking.setText(BookingUtils.getButtonText(mapType));

        if (mapType == MarkerType.POI) {
            btnBooking.setVisibility(View.GONE);
            spinnerContainer.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventLocationReady e) {
        Controllers.findFragment(this, map, fragment -> {
            RxGoogleMap.bind((SupportMapFragment) fragment)
                    .compose(bindToLifecycle())
                    .subscribe(this::onMapReady);
        });
    }


  /* --------------------------------------------------- */
  /* > OnClick */
  /* --------------------------------------------------- */

    @OnClick(R.id.booking_btn_my_location)
    public void onCurrentLocationClick() {
        presenter.moveToCurrentLocation();
    }

    @OnClick(R.id.booking_btn_book)
    public void onBookingClick() {
        presenter.requestBooking(mapType);
    }

  /* --------------------------------------------------- */
  /* > Map */
  /* --------------------------------------------------- */

    @Override
    public boolean onMarkerClick(Marker marker) {
        MapMarker mapMarker = markerManager.getItem(marker);
        mapOverlayManager.showOverlay(mapMarker);
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mapOverlayManager.hideOverlay();
    }

    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (googleMap == null) {
            Toasts.show("Sorry! unable to create maps");
        } else {
            loadData();
        }
    }

    private void loadData() {
        if (mapType == MarkerType.POI) {
            presenter.loadPoiCategories();
        } else {
            presenter.loadMarkers(mapType);
        }
    }

  /* --------------------------------------------------- */
  /* > View Methods */
  /* --------------------------------------------------- */

    @Override
    public void showBookingSuccess(BookingResponse res) {
        if (res != null) {
            Toasts.show(res.getMessage());
        } else {
            Toasts.show("Booking Failed");
            return;
        }
        navigateToBookingWait(mapType, res.getBookingDetail().getBookingId());
    }

    private void navigateToBookingWait(int markerType, String bookingId) {
        getRouter().pushController(Routes.simpleTransaction(
                new BookingWaitController(new BookingWaitProps(markerType, bookingId)),
                new HorizontalChangeHandler()
        ));
    }

    private void setViewState(BookingViewState state) {
        state.visibleIf(
                BookingViewState.NORMAL,
                btnLocation, btnBooking
        );

        state.visibleIf(
                BookingViewState.MARKER_SELECTED,
                markerContainer
        );
    }

    @Override
    public void showMarkers(List<MapMarker> mapMarkers) {
        markerManager.with(googleMap)
                .draw(mapMarkers);
    }

    @Override
    public void showPoiCategories(List<PoiCategory> categories) {
        if (spinnerType.getAdapter() == null) {
            spinnerType.setAdapter(spinnerAdapter.get());

            RxSpinner.position(spinnerType)
                    .forEach(position -> {
                        final PoiCategory category = spinnerAdapter.get().getItem(position);
                        Preconditions.checkNotNull(category);
                        loadPoi(category);
                    }, ErrorHandler::handleError);

            loadPoi(Values.first(categories));
        }
        spinnerAdapter.get().addAll(categories);
    }

    private void loadPoi(PoiCategory category) {
        presenter.loadPoi(category.getKategoriId());
    }

    @Override
    public void showLoading(boolean isLoading, int type) {
        switch (type) {
            case BookingView.LOADING_MARKER:
                Animates.visibility(loadingView, isLoading);
                break;
            case BookingView.LOADING_BOOKING:
                Animates.visibility(bookingLoadingView, isLoading);
                break;
            case BookingView.LOADING_ACTION_BOOKING:
                progressManager.show(isLoading);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLocationEmpty() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setMessage(R.string.error_location_empty)
                .setPositiveButton(R.string.try_again, (dialog, which) -> {
                    loadData();
                });
        builder.show();
    }

    @Override
    public void moveMap(double lat, double lng) {
        if (googleMap == null) {
            return;
        }
        MapUtils.moveMap(googleMap, lat, lng);
    }
}
