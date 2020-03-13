package com.incendiary.ambulanceapp.features.tours.search;

import android.location.Location;
import android.support.v4.util.Pair;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.location.LocationFactory;
import com.incendiary.ambulanceapp.features.location.LocationManager;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class TourSearchPresenter extends BasePresenter<TourSearchView> {

    private final LocationManager locationManager;

    @Inject
    public TourSearchPresenter(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public void searchPlace(String searchQuery) {

        Observable<Profile> profileObservable = DataStore.getProfile();
        Observable<Location> locationObservable = locationManager.getCurrentLocation()
                .firstOrDefault(LocationFactory.newEmptyLocation());

        Observable.zip(profileObservable, locationObservable, Pair::create)
                .observeOn(Schedulers.io())
                .switchMap(profileLocationPair -> {
                    final Profile profile = profileLocationPair.first;
                    final Location location = profileLocationPair.second;

                    return api.searchTourPlaces(
                            profile.getUserId(),
                            searchQuery,
                            String.valueOf(location.getLatitude()),
                            String.valueOf(location.getLongitude())
                    );
                })
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(places -> {
                    getView().showResult(places);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
