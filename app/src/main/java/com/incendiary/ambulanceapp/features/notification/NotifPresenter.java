package com.incendiary.ambulanceapp.features.notification;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class NotifPresenter extends BasePresenter<NotifView> {

    @Inject
    public NotifPresenter() {
    }

    public void loadNotif() {
        api.getNotifications()
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(notifications -> {
                    getView().showContent(notifications);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
