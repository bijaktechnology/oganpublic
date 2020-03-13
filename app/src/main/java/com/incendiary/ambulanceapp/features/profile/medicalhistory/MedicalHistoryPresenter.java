package com.incendiary.ambulanceapp.features.profile.medicalhistory;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class MedicalHistoryPresenter extends BasePresenter<MedicalHistoryView> {

    @Inject
    public MedicalHistoryPresenter() {
    }

    public void loadRekamMedis() {
        DataStore.getProfile()
                .flatMap(p -> api.getRekamMedis(p.getUserId()))
                .compose(Transformers.applyApiCall())
                .compose(Transformers.applyScheduler())
                .doAfterTerminate(() -> showLoading(false))
                .subscribe(res -> {
                    getView().showMedicalHistories(res);
                }, e -> ErrorHandler.handleError(e, getView()));
    }
}
