package com.incendiary.ambulanceapp.features.tours.list;

import android.location.Location;
import android.support.v4.util.Pair;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.data.model.tours.TourPlaceCategory;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.location.LocationFactory;
import com.incendiary.ambulanceapp.features.location.LocationManager;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import java.util.Locale;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class TourListPresenter extends BasePresenter<TourListView> {

    private final LocationManager locationManager;

    @Inject
    public TourListPresenter(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void loadTour(TourPlaceCategory category) {

        Observable<Profile> profileObservable = DataStore.getProfile();
        Observable<Location> locationObservable = locationManager.getOnceLocation()
                .firstOrDefault(LocationFactory.newEmptyLocation());

        Observable.zip(profileObservable, locationObservable, Pair::create)
                .observeOn(Schedulers.io())
                .switchMap(profileLocationPair -> {
                    final Profile profile = profileLocationPair.first;
                    final Location location = profileLocationPair.second;

                    return api.getTourPlaces(
                            profile.getUserId(),
                            category.name().toLowerCase(Locale.getDefault()),
                            String.valueOf(location.getLatitude()),
                            String.valueOf(location.getLongitude())
                    );
                })
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(tours -> {
                    getView().showContent(tours);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
