package com.incendiary.ambulanceapp.features.auth.forgotpassword;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordView> {

    @Inject
    public ForgotPasswordPresenter() {
    }

    public void resetPassword(String email) {
        showLoading(true);
        api.resetPassword(email)
                .compose(Transformers.applyApiCallBase())
                .doOnTerminate(() -> showLoading(false))
                .subscribe(res -> {
                    if (res.isSuccess()) {
                        getView().showSuccessResetPassword(res);
                    } else {
                        showError(new IllegalStateException(res.getMessage()));
                    }
                }, e -> ErrorHandler.handleError(e, getView()));
    }
}
