package com.incendiary.ambulanceapp.features.booking.map.overlay;

import android.view.ViewGroup;

import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.androidcommon.android.views.Animates;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapOverlayManager {

    private ViewGroup viewGroup;
    private List<MapOverlay> overlays = new ArrayList<>();

    @Inject
    public MapOverlayManager() {
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public void showOverlay(MapMarker mapMarker) {
        if (viewGroup == null) {
            throw new IllegalStateException("ViewGroup must not be empty!");
        }

        for (MapOverlay overlay : overlays) {
            if (overlay.isForType(mapMarker)) {
                overlay.handleMarker(mapMarker, viewGroup);
                break;
            }
        }
        Animates.visibility(viewGroup, true);
    }

    public void hideOverlay() {
        Animates.visibility(viewGroup, false);
    }

    public void addOverlay(MapOverlay mapOverlay) {
        overlays.add(mapOverlay);
    }
}
