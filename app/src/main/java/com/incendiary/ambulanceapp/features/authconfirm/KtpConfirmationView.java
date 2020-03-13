package com.incendiary.ambulanceapp.features.authconfirm;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;

public interface KtpConfirmationView extends MvpLoadingView, MvpErrorView{
    void showVerificationComplete(String message, String nik);
    void showEditSuccess(String message);
}
