package com.incendiary.ambulanceapp.features.booking.map;

import android.support.v4.util.Pair;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.utils.rx.Transformers;
import com.incendiary.androidcommon.etc.Logger;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class MapMarkerManager {

    @Inject
    public MapMarkerManager() {
    }

    private HashMap<String, MapMarker> ambulanceHashMap = new HashMap<>();
    private WeakReference<GoogleMap> mGoogleMap;

    public MapMarkerManager with(GoogleMap googleMap) {
        mGoogleMap = new WeakReference<>(googleMap);
        return this;
    }

    public MapMarker getItem(Marker marker) {
        return ambulanceHashMap.get(marker.getId());
    }

    public void draw(List<MapMarker> mapMarkers) {
        if (mGoogleMap.get() == null) {
            throw new IllegalStateException("You must set the map first");
        }

        ambulanceHashMap.clear();
        mGoogleMap.get().clear();

        Observable.from(mapMarkers)
                .map(mapMarker ->
                        Pair.create(
                                new MarkerOptions()
                                        .position(new LatLng(Double.valueOf(mapMarker.getLat()),
                                                Double.valueOf(mapMarker.getLang())))
                                        .title(mapMarker.getTitle())
                                        .icon(BitmapDescriptorFactory.fromResource(mapMarker.getIcon())),
                                mapMarker
                        )
                )
                .compose(Transformers.applyScheduler())
                .subscribe(pair -> {
                    ambulanceHashMap.put(mGoogleMap.get().addMarker(pair.first).getId(), pair.second);
                }, Logger::log);
    }
}
