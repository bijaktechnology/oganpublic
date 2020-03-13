package com.incendiary.ambulanceapp.features.profile.addfamily;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;

public interface AddFamilyView extends MvpLoadingView, MvpErrorView {
    void showSuccess(String message);
}
