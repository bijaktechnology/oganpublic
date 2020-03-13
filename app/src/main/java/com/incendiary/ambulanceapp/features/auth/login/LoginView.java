package com.incendiary.ambulanceapp.features.auth.login;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.Profile;

public interface LoginView extends MvpLoadingView, MvpErrorView {
    void showLoginSuccess(Profile profile);
}
