package com.incendiary.ambulanceapp.features.profile.medicalhistory;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.RekamMedis;

import java.util.List;

public interface MedicalHistoryView extends MvpLoadingView, MvpErrorView {
    void showMedicalHistories(List<RekamMedis> rekamMedisList);
}
