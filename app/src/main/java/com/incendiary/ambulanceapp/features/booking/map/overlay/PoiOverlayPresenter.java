package com.incendiary.ambulanceapp.features.booking.map.overlay;

import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.OganApp;
import com.incendiary.ambulanceapp.data.model.poi.Poi;
import com.incendiary.ambulanceapp.data.model.poi.hospital.AvailableRoom;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.network.ApiService;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import rx.functions.Action1;

public class PoiOverlayPresenter {

    private final ApiService apiService = OganApp.component().apiService();

    public void loadAvailableRooms(Poi poi, MvpLoadingView loadingView, Action1<AvailableRoom> onSuccess) {
        apiService.getAvailableRoom(poi.getLokasiId())
                .compose(Transformers.notifyProgress(loadingView))
                .compose(Transformers.applyApiCall())
                .subscribe(onSuccess, ErrorHandler::handleError);
    }
}
