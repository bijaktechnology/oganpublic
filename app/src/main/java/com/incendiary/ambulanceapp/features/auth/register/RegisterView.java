package com.incendiary.ambulanceapp.features.auth.register;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.BaseResponse;

public interface RegisterView extends MvpLoadingView, MvpErrorView {
    void showRegisterSuccess(BaseResponse baseResponse);
}
