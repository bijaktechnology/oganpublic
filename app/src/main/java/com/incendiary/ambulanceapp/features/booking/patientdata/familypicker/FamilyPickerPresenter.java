package com.incendiary.ambulanceapp.features.booking.patientdata.familypicker;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class FamilyPickerPresenter extends BasePresenter<FamilyPickerView> {

    @Inject
    public FamilyPickerPresenter() {
    }

    public void loadFamilies() {
        DataStore.getProfile()
                .switchMap(profile -> api.getFamilies(profile.getUserId()))
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(families -> {
                    getView().showContents(families);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
