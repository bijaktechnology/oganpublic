package com.incendiary.ambulanceapp.features.booking;

import android.content.Intent;
import android.location.Location;
import android.support.v4.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.exceptions.NoLocationException;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.poi.Poi;
import com.incendiary.ambulanceapp.data.repositories.BookingRemoteDataSource;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.location.LocationManager;
import com.incendiary.ambulanceapp.utils.MapUtils;
import com.incendiary.ambulanceapp.utils.rx.Transformers;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.etc.Logger;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class BookingPresenter extends BasePresenter<BookingView> {

    private final BookingRemoteDataSource remoteDataSource;
    private final LocationManager locationManager;

    @Inject
    public BookingPresenter(BookingRemoteDataSource remoteDataSource, LocationManager locationManager) {
        this.remoteDataSource = remoteDataSource;
        this.locationManager = locationManager;
    }

    public void init(int mapType) {
    }

    private Observable<Location> getCurrentLocation() {
        return locationManager.getCurrentLocation();
    }

    public void moveToCurrentLocation() {
        getCurrentLocation()
                .filter(l -> l != null)
                .subscribe(location ->
                                getView().moveMap(location.getLatitude(), location.getLongitude()),
                        Logger::log);
    }

    public void loadMarkers(int mapType) {
        showLoading(true, BookingView.LOADING_MARKER);
        Observable.zip(getCurrentLocation(), DataStore.getProfile(), Pair::create)
                .compose(Transformers.applyScheduler())
                .doOnNext(p -> getView().moveMap(p.first.getLatitude(), p.first.getLongitude()))
                .flatMap(p -> remoteDataSource.getMarkers(mapType, p.second.getUserId()))
                .doOnTerminate(() -> showLoading(false, BookingView.LOADING_MARKER))
                .subscribe(o -> {
                    getView().showMarkers(o);
                }, e -> {
                    if (e instanceof NoLocationException) {
                        getView().showLocationEmpty();
                    } else {
                        ErrorHandler.handleError(e, getView());
                    }
                });
    }

    public void requestBooking(int mapType) {
        showLoading(true, BookingView.LOADING_BOOKING);
        Observable.zip(getCurrentLocation(), DataStore.getProfile(), Pair::create)
                .compose(Transformers.applyScheduler())
                .flatMap(p -> remoteDataSource.booking(p.first, mapType, p.second.getUserId()))
                .doOnTerminate(() -> showLoading(false, BookingView.LOADING_BOOKING))
                .subscribe(res -> {
                    if (res.isSuccess()) {
                        getView().showBookingSuccess(res);
                        DataStore.setActiveBookingId(res.getBookingDetail().getBookingId(), mapType);
                    } else {
                        showError(new IllegalStateException(res.getMessage()));
                    }
                }, e -> ErrorHandler.handleError(e, getView()));
    }

    public void directionToPoi(Poi poi) {
        getCurrentLocation()
                .subscribe(location -> {
                    LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                    LatLng dest = new LatLng(Double.valueOf(poi.getLat()), Double.valueOf(poi.getLang()));
                    ContextProvider.get()
                            .startActivity(MapUtils.getDirectionIntent(current, dest)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                });
    }

    public void loadPoiCategories() {
        DataStore.getProfile()
                .switchMap(profile -> api.getCatgoryLokasiPenting(profile.getUserId()))
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(categories -> {
                    getView().showPoiCategories(categories);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void loadPoi(String categoryId) {
        showLoading(true, BookingView.LOADING_MARKER);

        Observable.zip(getCurrentLocation(), DataStore.getProfile(), Pair::create)
                .doOnNext(pair -> getView().moveMap(pair.first.getLatitude(), pair.first.getLongitude()))
                .observeOn(Schedulers.io())
                .map(pair -> pair.second)
                .switchMap(profile -> api.getLokasiPenting(profile.getUserId(), categoryId))
                .compose(Transformers.applyApiCall())
                .doOnTerminate(() -> showLoading(false, BookingView.LOADING_MARKER))
                .subscribe(pois -> {
                    Observable.from(pois)
                            .map(poi -> ((MapMarker) poi))
                            .toList()
                            .forEach(mapMarkers -> getView().showMarkers(mapMarkers), ErrorHandler::handleError);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
