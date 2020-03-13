package com.incendiary.ambulanceapp.features.booking.map.overlay;

import android.view.ViewGroup;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;

public interface MapOverlay {
    boolean isForType(MapMarker mapMarker);

    void handleMarker(MapMarker mapMarker, ViewGroup viewGroup);
}
