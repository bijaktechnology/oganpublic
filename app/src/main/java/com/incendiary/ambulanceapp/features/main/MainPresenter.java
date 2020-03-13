package com.incendiary.ambulanceapp.features.main;

import android.util.Log;

import com.esafirm.emvipi.AbsPresenter;
import com.google.firebase.iid.FirebaseInstanceId;
import com.incendiary.ambulanceapp.network.ApiService;
import com.incendiary.ambulanceapp.utils.rx.Transformers;
import com.incendiary.androidcommon.etc.Logger;

import javax.inject.Inject;

public class MainPresenter extends AbsPresenter<MainView> {

    private final ApiService apiService;

    @Inject
    public MainPresenter(ApiService apiService) {
        this.apiService = apiService;
    }

    public void registerDevice() {
        String token = FirebaseInstanceId.getInstance().getToken();

        apiService.registerDevice(token)
                .compose(Transformers.applyApiCallBase())
                .subscribe(res -> Logger.log(Log.DEBUG, res.getMessage()),
                        e -> Logger.log(Log.DEBUG, e.getMessage()));
    }
}
