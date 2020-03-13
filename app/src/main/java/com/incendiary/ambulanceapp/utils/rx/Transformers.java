package com.incendiary.ambulanceapp.utils.rx;

import android.os.Handler;
import android.os.Looper;

import com.esafirm.emvipi.view.LoadingType;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.common.ApiResponse;

import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Transformers {

    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    public static <T> Single.Transformer<T, T> asyncTask() {
        return tSingle -> tSingle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Observable.Transformer<T, T> applyScheduler() {
        return tObservable -> tObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable.Transformer<ApiResponse, ApiResponse> applyApiCallBase() {
        return apiResponseObservable -> apiResponseObservable.compose(applyScheduler())
                .flatMap(apiResponse -> {
                    if (apiResponse.isSuccess()) {
                        return Observable.just(apiResponse);
                    } else {
                        return Observable.error(new IllegalStateException(apiResponse.getMessage()));
                    }
                });
    }

    public static <T> Observable.Transformer<ApiResponse<T>, T> applyApiCall() {
        return tObservable -> tObservable.compose(applyScheduler())
                .flatMap(res -> {
                    if (res.isSuccess()) {
                        return Observable.just(res.getData());
                    } else {
                        return Observable.error(new IllegalStateException(res.getMessage()));
                    }
                });
    }

    public static <T> rx.Observable.Transformer<T, T> notifyProgress(MvpLoadingView loadingView) {
        return notifyProgress(loadingView, LoadingType.ANY);
    }


    public static <T> rx.Observable.Transformer<T, T> notifyProgress(MvpLoadingView loadingView, int loadingType) {
        return tObservable -> tObservable.doOnSubscribe(() ->
                MAIN_HANDLER.post(() -> loadingView.showLoading(true, loadingType))).doAfterTerminate(() ->
                MAIN_HANDLER.post(() -> loadingView.showLoading(false, loadingType)));
    }
}
