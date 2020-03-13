package com.incendiary.ambulanceapp.features.booking;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.BookingResponse;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.poi.PoiCategory;

import java.util.List;

public interface BookingView extends MvpLoadingView, MvpErrorView {

    int LOADING_BOOKING = 0;
    int LOADING_MARKER = 1;
    int LOADING_ACTION_BOOKING = 2;

    void showBookingSuccess(BookingResponse bookingResponse);
    void showLocationEmpty();
    void showMarkers(List<MapMarker> mapMarkers);
    void showPoiCategories(List<PoiCategory> categories);
    void moveMap(double lat, double lng);
}
