package com.incendiary.ambulanceapp.utils.rx;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import rx.Observable;

public class RxGoogleMap {

    public static Observable<GoogleMap> bind(SupportMapFragment supportMapFragment) {
        return Observable.create(subscriber ->
                supportMapFragment.getMapAsync(googleMap -> {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(googleMap);
                        subscriber.onCompleted();
                    }
                }));
    }
}
