package com.incendiary.ambulanceapp.features.profile.familylist;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.family.Family;

import java.util.List;

public interface FamilyListView extends MvpLoadingView, MvpErrorView {
    void showContent(List<Family> families);
    void showDeleteSuccess(Family family);
}
