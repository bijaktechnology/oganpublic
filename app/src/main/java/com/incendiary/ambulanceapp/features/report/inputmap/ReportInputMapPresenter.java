package com.incendiary.ambulanceapp.features.report.inputmap;

import android.graphics.Bitmap;

import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.maps.model.LatLng;
import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.report.ReportParameter;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.Bitmaps;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ReportInputMapPresenter extends BasePresenter<ReportInputMapView> {

    @Inject
    public ReportInputMapPresenter() {
    }

    public void submitReport(LatLng latLng, String categoryId, String title, String desc) {
        Observable<String> imageObservable = DataStore.getReportImage()
                .map(Image::getPath)
                .map(path -> Bitmaps.getBitmap(path, 480))
                .map(bitmap -> Bitmaps.getScaledBitmap(bitmap, Bitmap.CompressFormat.JPEG, 480))
                .map(bitmap -> Bitmaps.bitmapToBase64String(bitmap, 90))
                .subscribeOn(Schedulers.io());

        Observable.zip(imageObservable, DataStore.getProfile(), (photo, profile) ->
                new ReportParameter(
                        String.valueOf(latLng.latitude),
                        String.valueOf(latLng.longitude),
                        photo,
                        profile.getUserId()
                ))
                .switchMap(param -> api.submitReport(
                        param.getUserId(),
                        DataStore.getKeyCode(),
                        categoryId,
                        title,
                        desc,
                        param.getLat(),
                        param.getLng(),
                        param.getPhoto()))
                .compose(Transformers.applyApiCallBase())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(res -> {
                    getView().showSuccess(res.getMessage());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
