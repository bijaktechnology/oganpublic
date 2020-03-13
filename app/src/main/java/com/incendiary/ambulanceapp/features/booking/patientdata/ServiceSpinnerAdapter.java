package com.incendiary.ambulanceapp.features.booking.patientdata;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.incendiary.ambulanceapp.data.model.patient.Layanan;

import javax.inject.Inject;

public class ServiceSpinnerAdapter extends ArrayAdapter<Layanan> {

    @Inject
    public ServiceSpinnerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_dropdown_item_1line);
    }
}
