package com.incendiary.ambulanceapp.features.profile.familylist;

import com.esafirm.emvipi.view.LoadingType;
import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class FamilyListPresenter extends BasePresenter<FamilyListView> {

    @Inject
    public FamilyListPresenter() {
    }

    public void loadFamilies() {
        DataStore.getProfile()
                .switchMap(profile -> api.getFamilies(profile.getUserId()))
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(families -> {
                    getView().showContent(families);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void deleteFamily(Family family) {
        getView().showLoading(true, LoadingType.LOAD_MORE);

        DataStore.getProfile()
                .switchMap(profile -> api.deleteFamily(profile.getUserId(), DataStore.getKeyCode(), family.getAnggotaId()))
                .compose(Transformers.applyApiCallBase())
                .doAfterTerminate(() -> getView().showLoading(false, LoadingType.LOAD_MORE))
                .subscribe(apiResponse -> {
                    getView().showDeleteSuccess(family);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
