package com.incendiary.ambulanceapp.features.profile.view;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.Profile;

public interface ProfileView extends MvpLoadingView, MvpErrorView {
    void showProfile(Profile profile);
    void showEditSuccess();
}
