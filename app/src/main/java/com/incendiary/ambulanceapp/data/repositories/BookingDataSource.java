package com.incendiary.ambulanceapp.data.repositories;

import android.location.Location;
import com.incendiary.ambulanceapp.data.model.BaseResponse;
import com.incendiary.ambulanceapp.data.model.BookingResponse;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;

import java.util.List;

import rx.Observable;

public interface BookingDataSource {
    Observable<List<MapMarker>> getMarkers(int mapType, String userId);

    Observable<BookingResponse> booking(Location location, int mapType, String userId);

    Observable<BaseResponse> cancelBooking(int mapType, String userId, String bookingId);

    Observable<BaseResponse> completeBooking(int mapType, String userId, String bookingId);
}
