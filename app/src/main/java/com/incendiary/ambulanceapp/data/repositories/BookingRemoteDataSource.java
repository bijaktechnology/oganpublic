package com.incendiary.ambulanceapp.data.repositories;

import android.location.Location;

import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.BaseResponse;
import com.incendiary.ambulanceapp.data.model.BookingResponse;
import com.incendiary.ambulanceapp.data.model.common.ApiResponse;
import com.incendiary.ambulanceapp.data.model.detail.AmbulanceDetail;
import com.incendiary.ambulanceapp.data.model.detail.BidanDetail;
import com.incendiary.ambulanceapp.data.model.detail.DoctorDetail;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;
import com.incendiary.ambulanceapp.network.ApiService;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

public class BookingRemoteDataSource implements BookingDataSource {

    private final ApiService api;

    @Inject
    public BookingRemoteDataSource(ApiService api) {
        this.api = api;
    }

    @Override
    public Observable<List<MapMarker>> getMarkers(int mapType, String userId) {
        return getMarkerObservable(mapType, userId)
                .compose(Transformers.applyScheduler());
    }

    private Observable<List<MapMarker>> getMarkerObservable(int mapType, String userId) {
        switch (mapType) {
            default:
                return api.getAmbulanceMarkers(userId)
                        .map(ApiResponse::getData)
                        .compose(toMarkerList());
            case MarkerType.DOCTOR:
                return api.getDoctorMarkers(userId)
                        .map(ApiResponse::getData)
                        .compose(toMarkerList());
            case MarkerType.BIDAN:
                return api.getBidanMarkers(userId)
                        .map(ApiResponse::getData)
                        .compose(toMarkerList());
        }
    }

    private <T extends List<? extends MapMarker>> Observable.Transformer<T, List<MapMarker>> toMarkerList() {
        return tObservable -> tObservable.flatMap(Observable::from).toList();
    }

    /* --------------------------------------------------- */
    /* > Get Detail */
    /* --------------------------------------------------- */

    public Observable<AmbulanceDetail> getAmbulanceDetail(String bookingId) {
        return DataStore.getProfile()
                .switchMap(profile -> api.getDetailAmbulance(profile.getUserId(), DataStore.getKeyCode(), bookingId))
                .compose(Transformers.applyApiCall());
    }

    public Observable<DoctorDetail> getDoctorDetail(String bookingId) {
        return DataStore.getProfile()
                .switchMap(profile -> api.getDetailDoctor(profile.getUserId(), DataStore.getKeyCode(), bookingId))
                .compose(Transformers.applyApiCall());
    }

    public Observable<BidanDetail> getBidanDetail(String bookingId) {
        return DataStore.getProfile()
                .switchMap(profile -> api.getDetailBidan(profile.getUserId(), DataStore.getKeyCode(), bookingId))
                .compose(Transformers.applyApiCall());
    }

  /* --------------------------------------------------- */
  /* > Booking */
  /* --------------------------------------------------- */

    @Override
    public Observable<BookingResponse> booking(Location location, int mapType, String userId) {
        return getBookingObservable(location, mapType, userId)
                .compose(Transformers.applyScheduler());
    }

    private Observable<BookingResponse> getBookingObservable(Location location, int mapType,
                                                             String userId) {

        String lat = String.valueOf(location.getLatitude());
        String lng = String.valueOf(location.getLongitude());

        switch (mapType) {
            default:
                return api.bookingAmbulance(userId, lat, lng);
            case MarkerType.BIDAN:
                return api.bookingBidan(userId, lat, lng);
            case MarkerType.DOCTOR:
                return api.bookingDoctor(userId, lat, lng);
        }
    }

  /* --------------------------------------------------- */
  /* > Complete Booking */
  /* --------------------------------------------------- */

    @Override
    public Observable<BaseResponse> completeBooking(int mapType, String userId, String bookingId) {
        return getCompleteBookingObservable(mapType, userId, bookingId)
                .compose(Transformers.applyScheduler());
    }

    private Observable<BaseResponse> getCompleteBookingObservable(int mapType, String userId,
                                                                  String bookingId) {
        switch (mapType) {
            default:
                return api.completeBooking(userId, bookingId);
            case MarkerType.BIDAN:
                return api.completeBookingBidan(userId, bookingId);
            case MarkerType.DOCTOR:
                return api.completeBookingDoctor(userId, bookingId);
        }
    }

  /* --------------------------------------------------- */
  /* > Cancel Booking */
  /* --------------------------------------------------- */

    @Override
    public Observable<BaseResponse> cancelBooking(int mapType, String userId, String bookingId) {
        return getCancelBookingObservable(mapType, userId, bookingId)
                .compose(Transformers.applyScheduler());
    }

    private Observable<BaseResponse> getCancelBookingObservable(int mapType, String userId,
                                                                String bookingId) {
        switch (mapType) {
            default:
                return api.cancelBooking(userId, bookingId);
            case MarkerType.BIDAN:
                return api.cancelBookingBidan(userId, bookingId);
            case MarkerType.DOCTOR:
                return api.cancelBookingDoctor(userId, bookingId);
        }
    }
}
