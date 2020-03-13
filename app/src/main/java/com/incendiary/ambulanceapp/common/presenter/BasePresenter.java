package com.incendiary.ambulanceapp.common.presenter;

import com.esafirm.emvipi.AbsPresenter;
import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.esafirm.emvipi.view.MvpView;
import com.incendiary.ambulanceapp.OganApp;
import com.incendiary.ambulanceapp.network.ApiService;

public abstract class BasePresenter<V extends MvpView> extends AbsPresenter<V> {

    protected ApiService api = OganApp.component().apiService();

    /* --------------------------------------------------- */
    /* > Convenience */
    /* --------------------------------------------------- */

    protected void showLoading(boolean isLoading) {
        showLoading(isLoading, 0);
    }

    protected void showLoading(boolean isLoading, int type) {
        if (getView() instanceof MvpLoadingView) {
            MvpLoadingView view = (MvpLoadingView) getView();
            view.showLoading(isLoading, type);
        } else {
            throw new IllegalStateException("Must implement MvpLoadingView!");
        }
    }

    protected void showError(Throwable throwable) {
        if (getView() instanceof MvpLoadingView && getView() instanceof MvpErrorView) {
            ((MvpLoadingView) getView()).showLoading(false, 0);
            ((MvpErrorView) getView()).showError(throwable);
        }
    }
}
