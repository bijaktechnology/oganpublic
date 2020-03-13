package com.incendiary.ambulanceapp.utils;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapUtils {

    private static final int ZOOM_LEVEL = 17;

    public static double calculateDistance(double startLat, double startLng, double endLat, double endLng){
        Location origin = new Location(MapUtils.class.getSimpleName());
        origin.setLatitude(startLat);
        origin.setLongitude(startLng);

        Location dest = new Location(MapUtils.class.getSimpleName());
        dest.setLatitude(endLat);
        dest.setLongitude(endLng);

        return origin.distanceTo(dest);
    }

    public static void moveMap(GoogleMap googleMap, double lat, double lng) {
        CameraPosition position =
                new CameraPosition.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(ZOOM_LEVEL)
                        .bearing(0)
                        .tilt(0)
                        .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
    }

    public static Intent getDirectionIntent(LatLng current, LatLng dest) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="
                + current.latitude
                + ","
                + current.longitude
                + "&daddr="
                + dest.latitude
                + ","
                + dest.longitude));
    }
}
