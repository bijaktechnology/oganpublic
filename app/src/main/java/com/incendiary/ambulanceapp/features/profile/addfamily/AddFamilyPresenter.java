package com.incendiary.ambulanceapp.features.profile.addfamily;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class AddFamilyPresenter extends BasePresenter<AddFamilyView> {

    @Inject
    public AddFamilyPresenter() {
    }

    public void addFamily(Family family) {
        DataStore.getProfile()
                .flatMap(profile -> api.addFamily(
                        profile.getUserId(),
                        DataStore.getKeyCode(),
                        family.getNik(),
                        family.getNamaLengkap(),
                        family.getTglLahir(),
                        family.getNamaIbuKandung(),
                        family.getStatusKeluarga()))
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(apiResponse -> {
                    showLoading(false);
                    getView().showSuccess(apiResponse.getMessage());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void editFamily(Family family) {
        DataStore.getProfile()
                .flatMap(profile -> api.editFamily(
                        profile.getUserId(),
                        DataStore.getKeyCode(),
                        family.getAnggotaId(),
                        family.getNik(),
                        family.getNamaLengkap(),
                        family.getTglLahir(),
                        family.getNamaIbuKandung(),
                        family.getStatusKeluarga()))
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(apiResponse -> {
                    showLoading(false);
                    getView().showSuccess(apiResponse.getMessage());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
