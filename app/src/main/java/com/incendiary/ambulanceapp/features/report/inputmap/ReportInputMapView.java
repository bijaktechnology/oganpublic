package com.incendiary.ambulanceapp.features.report.inputmap;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.google.android.gms.maps.model.LatLng;

public interface ReportInputMapView extends MvpLoadingView, MvpErrorView {
    void showSuccess(String message);
    void showAddress(LatLng latLng);
}
