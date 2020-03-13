package com.incendiary.ambulanceapp.data.model.map;

public interface MapMarker {
    int getMarkerType();

    String getLat();

    String getLang();

    String getTitle();

    int getIcon();
}
