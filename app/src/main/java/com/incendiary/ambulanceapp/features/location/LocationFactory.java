package com.incendiary.ambulanceapp.features.location;

import android.location.*;
import android.location.LocationManager;

public class LocationFactory {

    public static Location newEmptyLocation(){
        Location location = new Location(LocationManager.PASSIVE_PROVIDER);
        location.setLatitude(0);
        location.setLongitude(0);
        return location;
    }

}
