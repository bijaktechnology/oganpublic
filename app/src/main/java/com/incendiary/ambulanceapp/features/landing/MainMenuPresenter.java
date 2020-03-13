package com.incendiary.ambulanceapp.features.landing;

import com.esafirm.emvipi.AbsPresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.common.ApiResponse;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.network.ApiService;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class MainMenuPresenter extends AbsPresenter<MainMenuView> {

    private final ApiService api;

    @Inject
    public MainMenuPresenter(ApiService api) {
        this.api = api;
    }

    public void checkActiveBooking(final int markerType) {
        DataStore.getProfile()
                .flatMap(profile -> api.getBookingStatus(profile.getUserId()))
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .map(ApiResponse::getData)
                .subscribe(bookingStatus -> {

                    if (bookingStatus != null) {
                        if (bookingStatus.getBookingAmbulance() != null) {
                            getView().navigateToBookingWait(MarkerType.AMBULANCE,
                                    bookingStatus.getBookingAmbulance().getBookingId());
                            return;
                        }

                        if (bookingStatus.getBookingDoctor() != null) {
                            getView().navigateToBookingWait(MarkerType.DOCTOR,
                                    bookingStatus.getBookingDoctor().getBookingId());
                            return;
                        }

                        if (bookingStatus.getBookingBidan() != null) {
                            getView().navigateToBookingWait(MarkerType.BIDAN,
                                    bookingStatus.getBookingBidan().getBookingId());
                        }
                    }
                    getView().navigateToBooking(markerType);

                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
