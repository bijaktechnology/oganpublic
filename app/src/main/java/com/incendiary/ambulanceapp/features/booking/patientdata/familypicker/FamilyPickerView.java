package com.incendiary.ambulanceapp.features.booking.patientdata.familypicker;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.family.Family;

import java.util.List;

public interface FamilyPickerView extends MvpLoadingView, MvpErrorView {
    void showContents(List<Family> families);
}
