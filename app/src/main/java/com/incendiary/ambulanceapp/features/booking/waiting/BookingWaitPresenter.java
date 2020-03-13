package com.incendiary.ambulanceapp.features.booking.waiting;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.detail.BookingDetail;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;
import com.incendiary.ambulanceapp.data.repositories.BookingRemoteDataSource;
import com.incendiary.ambulanceapp.features.booking.BookingView;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class BookingWaitPresenter extends BasePresenter<BookingWaitView> {

    private final BookingRemoteDataSource remoteDataSource;

    private BookingWaitProps props;
    private Subscription subscription;

    @Inject
    public BookingWaitPresenter(BookingRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public BookingWaitPresenter setProps(BookingWaitProps props) {
        this.props = props;
        return this;
    }

    public void cancelBooking(int mapType, String bookingId) {
        doActionBooking(mapType, false, bookingId);
    }

    public void completeBooking(int mapType, String bookingId) {
        doActionBooking(mapType, true, bookingId);
    }

    private void doActionBooking(final int mapType, boolean isComplete, String bookingId) {
        showLoading(true, BookingView.LOADING_ACTION_BOOKING);
        DataStore.getProfile()
                .flatMap(profile -> {
                    if (isComplete) {
                        return remoteDataSource.completeBooking(mapType, profile.getUserId(), bookingId);
                    } else {
                        return remoteDataSource.cancelBooking(mapType, profile.getUserId(), bookingId);
                    }
                })
                .compose(Transformers.applyScheduler())
                .doOnTerminate(() -> showLoading(false, BookingView.LOADING_ACTION_BOOKING))
                .subscribe(res -> {

                    if (res.isSuccess()) {
                        getView().showBookingComplete(res, !isComplete);
                    } else {
                        showError(new IllegalStateException(res.getMessage()));
                    }
                }, e -> ErrorHandler.handleError(e, getView()));
    }

    private Observable<BookingDetail> loadDetail(final int mapType, final String bookingId) {
        switch (mapType) {
            case MarkerType.AMBULANCE:
                return remoteDataSource.getAmbulanceDetail(bookingId)
                        .cast(BookingDetail.class);
            case MarkerType.DOCTOR:
                return remoteDataSource.getDoctorDetail(bookingId)
                        .cast(BookingDetail.class);
            default:
                return remoteDataSource.getBidanDetail(bookingId)
                        .cast(BookingDetail.class);
        }
    }

    @Override
    protected void onViewAttached() {
        subscription = Observable.interval(0, 5, TimeUnit.MINUTES, Schedulers.io())
                .flatMap(aLong -> loadDetail(props.getType(), props.getBookingId())
                        .onErrorResumeNext(Observable.empty()))
                .retry()
                .subscribe(bookingDetail -> {
                    getView().showBookingDetail(bookingDetail);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));

    }

    @Override
    protected void onViewDetached() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
