package com.incendiary.ambulanceapp.features.booking.waiting;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.BaseResponse;
import com.incendiary.ambulanceapp.data.model.detail.BookingDetail;

public interface BookingWaitView extends MvpLoadingView, MvpErrorView {
    void showBookingComplete(BaseResponse response, boolean isCanceled);
    void showBookingDetail(BookingDetail bookingDetail);
}
