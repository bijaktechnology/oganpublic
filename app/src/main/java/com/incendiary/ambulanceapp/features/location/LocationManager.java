package com.incendiary.ambulanceapp.features.location;

import android.location.Location;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.incendiary.ambulanceapp.data.model.exceptions.LocationResolutionException;
import com.incendiary.ambulanceapp.data.model.exceptions.NoLocationException;

import javax.inject.Inject;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.schedulers.Schedulers;

public class LocationManager {

    private final ReactiveLocationProvider provider;

    @Inject
    public LocationManager(ReactiveLocationProvider locationProvider) {
        this.provider = locationProvider;
    }

    public Observable<Location> getCurrentLocation() {
        return provider.getLastKnownLocation()
                .subscribeOn(Schedulers.computation())
                .take(1)
                .switchIfEmpty(Observable.error(new NoLocationException()));
    }

    private LocationRequest getOnceLocationRequest() {
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100);
    }

    public Observable<Location> getOnceLocation() {
        return provider.checkLocationSettings(
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(getOnceLocationRequest())
                        .setAlwaysShow(true)
                        .build()
        ).switchMap(locationSettingsResult -> {
            Status status = locationSettingsResult.getStatus();
            if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                return Observable.error(new LocationResolutionException(status));
            }
            return provider.getLastKnownLocation();
        });
    }
}
