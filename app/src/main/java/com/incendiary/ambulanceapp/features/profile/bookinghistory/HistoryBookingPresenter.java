package com.incendiary.ambulanceapp.features.profile.bookinghistory;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class HistoryBookingPresenter extends BasePresenter<HistoryBookingView> {

    @Inject
    public HistoryBookingPresenter() {
    }

    public void loadHistory(boolean isShowLoading) {
        if (isShowLoading) {
            showLoading(true);
        }

        DataStore.getProfile()
                .flatMap(p -> api.getHistoryBooking(p.getUserId()))
                .compose(Transformers.applyApiCall())
                .doOnTerminate(() -> showLoading(false))
                .subscribe(res -> getView().showHistoryBooking(res),
                        e -> ErrorHandler.handleError(e, getView()));
    }
}
