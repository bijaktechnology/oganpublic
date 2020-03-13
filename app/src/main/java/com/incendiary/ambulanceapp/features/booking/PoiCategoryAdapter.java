package com.incendiary.ambulanceapp.features.booking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.incendiary.ambulanceapp.data.model.poi.PoiCategory;

import javax.inject.Inject;

public class PoiCategoryAdapter extends ArrayAdapter<PoiCategory> {

    @Inject
    public PoiCategoryAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_dropdown_item_1line);
    }
}
