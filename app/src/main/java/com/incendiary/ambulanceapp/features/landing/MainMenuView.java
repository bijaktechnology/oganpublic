package com.incendiary.ambulanceapp.features.landing;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;

public interface MainMenuView extends MvpLoadingView, MvpErrorView{
    void navigateToBooking(int markerType);
    void navigateToBookingWait(int markerType, String bookingId);
}
