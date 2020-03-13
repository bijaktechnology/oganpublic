package com.incendiary.ambulanceapp.features.sos;

import android.location.Location;
import android.support.v4.util.Pair;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.data.model.sos.SosType;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.location.LocationManager;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class SosPresenter extends BasePresenter<SosView> {

    private final LocationManager locationManager;

    @Inject
    public SosPresenter(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void doEmergencyCall(SosType sosType) {
        Observable<Location> locationObservable = locationManager.getCurrentLocation();
        Observable<Profile> profileObservable = DataStore.getProfile();

        Observable.zip(locationObservable, profileObservable, Pair::create)
                .observeOn(Schedulers.io())
                .switchMap(r -> api.emergencyCall(
                        r.second.getUserId(),
                        DataStore.getKeyCode(),
                        String.valueOf(r.first.getLatitude()),
                        String.valueOf(r.first.getLongitude()),
                        sosType.getValue()))
                .compose(Transformers.notifyProgress(getView()))
                .compose(Transformers.applyApiCallBase())
                .subscribe(res -> {

                    if (res.isSuccess()) {
                        getView().showSuccess(sosType);
                    } else {
                        throw new IllegalStateException(res.getMessage());
                    }

                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
