package com.incendiary.ambulanceapp.features.booking.map.overlay;

import com.incendiary.ambulanceapp.OganApp;
import com.incendiary.ambulanceapp.data.model.poi.Poi;
import com.incendiary.ambulanceapp.data.model.poi.atmberas.AtmBerasInfo;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.network.ApiService;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import rx.functions.Action1;

public class AtmBerasOverlayPresenter {

    private ApiService api = OganApp.component().apiService();

    public void loadAtmBerasInfo(Poi poi, Action1<AtmBerasInfo> onSuccess, Action1<Throwable> onError) {
        api.getAtmBerasInfo(poi.getLokasiId())
                .compose(Transformers.applyScheduler())
                .subscribe(res -> onSuccess.call(res.getData()), throwable -> {
                    ErrorHandler.handleError(throwable);
                    onError.call(throwable);
                });
    }
}
