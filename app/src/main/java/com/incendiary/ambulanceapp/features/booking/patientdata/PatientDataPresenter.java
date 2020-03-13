package com.incendiary.ambulanceapp.features.booking.patientdata;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.common.ApiResponse;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

import rx.Observable;

public class PatientDataPresenter extends BasePresenter<PatientDataView> {

    @Inject
    public PatientDataPresenter() {
    }

    public void submitData(int type, String bookingId, String nik, String name, String serviceId) {
        DataStore.getProfile()
                .switchMap(profile -> getInsertDataApi(
                        type,
                        profile.getUserId(),
                        bookingId,
                        nik,
                        name,
                        serviceId
                ))
                .compose(Transformers.applyApiCallBase())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(response -> {
                    getView().showSuccess(response.getMessage());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    private Observable<ApiResponse> getInsertDataApi(int type, String userId, String bookingId, String nik, String name,
                                                     String serviceId) {

        final String keycode = DataStore.getKeyCode();
        switch (type) {
            case MarkerType.DOCTOR:
                return api.insertDoctorPatientData(userId, keycode, bookingId, nik, name, serviceId);
            case MarkerType.BIDAN:
                return api.insertBidanPatientData(userId, keycode, bookingId, nik, name, serviceId);
            default:
                return api.insertAmulancePatientData(userId, keycode, bookingId, nik, name, serviceId);
        }
    }

    public void loadServices() {
        DataStore.getProfile()
                .switchMap(profile -> api.getLayanan(profile.getUserId()))
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(layanen -> {
                    getView().showServices(layanen);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
