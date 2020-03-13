package com.incendiary.ambulanceapp.features.auth.forgotpassword;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.BaseResponse;

public interface ForgotPasswordView extends MvpLoadingView, MvpErrorView {
    void showSuccessResetPassword(BaseResponse response);
}
