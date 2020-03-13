package com.incendiary.ambulanceapp.features.authconfirm;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.auth.register.DomicileStatus;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class KtpConfirmationPresenter extends BasePresenter<KtpConfirmationView> {

    @Inject
    public KtpConfirmationPresenter() {
    }

    public void verifyNik(String nik) {
        api.verifyNik(nik)
                .compose(Transformers.applyApiCallBase())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(apiResponse -> {
                    getView().showVerificationComplete(apiResponse.getMessage(), nik);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void editDomicile(DomicileStatus status, String nik) {
        DataStore.getProfile()
                .switchMap(profile -> api.editDomicile(profile.getUserId(), DataStore.getKeyCode(), nik, status.getValue()))
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(res -> {
                    if (res.isSuccess()) {
                        DataStore.saveProfile(res.getProfile());
                        getView().showEditSuccess(res.getMessage());
                    } else {
                        showError(new IllegalStateException(res.getMessage()));
                    }
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
