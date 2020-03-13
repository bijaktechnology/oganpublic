package com.incendiary.ambulanceapp.features.profile.bookinghistory;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.HistoryBooking;

import java.util.List;

public interface HistoryBookingView extends MvpLoadingView, MvpErrorView {
    void showHistoryBooking(List<HistoryBooking> historyBookingList);
}
