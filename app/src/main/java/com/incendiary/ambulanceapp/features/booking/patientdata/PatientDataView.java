package com.incendiary.ambulanceapp.features.booking.patientdata;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.patient.Layanan;

import java.util.List;

public interface PatientDataView extends MvpLoadingView, MvpErrorView {
    void showSuccess(String message);
    void showServices(List<Layanan> services);
}
