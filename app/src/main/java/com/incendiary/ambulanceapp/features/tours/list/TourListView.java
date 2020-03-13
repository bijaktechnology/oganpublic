package com.incendiary.ambulanceapp.features.tours.list;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;

import java.util.List;

public interface TourListView extends MvpLoadingView, MvpErrorView {
    void showContent(List<TourPlace> tours);
}
