package com.incendiary.ambulanceapp.features.tours.search;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;

import java.util.List;

public interface TourSearchView extends MvpLoadingView, MvpErrorView {
    void showResult(List<TourPlace> places);
}
