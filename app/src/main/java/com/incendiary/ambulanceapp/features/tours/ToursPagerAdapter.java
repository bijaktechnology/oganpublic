package com.incendiary.ambulanceapp.features.tours;

import android.support.annotation.NonNull;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.support.RouterPagerAdapter;
import com.incendiary.ambulanceapp.data.model.tours.TourPlaceCategory;
import com.incendiary.ambulanceapp.features.tours.list.TourListController;

import javax.inject.Inject;

public class ToursPagerAdapter extends RouterPagerAdapter {

    private static final int CHILD_COUNT = 3;

    @Inject
    public ToursPagerAdapter(@NonNull Controller host) {
        super(host);
    }

    @Override
    public void configureRouter(@NonNull Router router, int position) {
        router.setRoot(RouterTransaction.with(new TourListController(getCategory(position))));
    }

    @Override
    public int getCount() {
        return CHILD_COUNT;
    }

    private TourPlaceCategory getCategory(int position) {
        switch (position) {
            case 0:
                return TourPlaceCategory.WISATA;
            case 1:
                return TourPlaceCategory.KULINER;
            default:
                return TourPlaceCategory.AKOMODASI;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tempat Wisata";
            case 1:
                return "Restoran";
            case 2:
                return "Hotel";
        }
        return null;
    }
}
