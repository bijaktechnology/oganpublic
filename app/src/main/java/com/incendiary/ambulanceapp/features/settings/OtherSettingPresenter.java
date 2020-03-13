package com.incendiary.ambulanceapp.features.settings;

import com.esafirm.emvipi.AbsPresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.network.ApiService;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class OtherSettingPresenter extends AbsPresenter<OtherSettingView> {

    private final ApiService apiService;

    @Inject
    public OtherSettingPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    public void init() {
        DataStore.getProfile()
                .compose(Transformers.applyScheduler())
                .subscribe(profile -> {
                    getView().setupSwitch(profile.isPushNotifEnabled());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void setPushNotifStatus(boolean isEnabled) {
        apiService.setPushNotificationStatus(isEnabled ? "1" : "0")
                .doOnNext(res -> {
                    if (res.isSuccess()) {
                        final Profile profile = res.getProfile();
                        DataStore.saveProfile(profile);
                    }
                })
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(res -> {
                    getView().showPushNotificationStatusUpdate(res.isSuccess());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
