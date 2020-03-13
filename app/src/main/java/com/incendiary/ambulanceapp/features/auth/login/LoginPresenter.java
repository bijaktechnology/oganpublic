package com.incendiary.ambulanceapp.features.auth.login;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.HashUtils;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class LoginPresenter extends BasePresenter<LoginView> {

    @Inject
    public LoginPresenter() {
    }

    public void login(String email, String password) {
        api.login(email, HashUtils.md5(password))
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(p -> {
                    if (p.isSuccess()) {
                        final Profile profile = p.getData();
                        DataStore.saveProfile(profile);
                        getView().showLoginSuccess(profile);
                    } else {
                        showError(new IllegalStateException(p.getMessage()));
                    }
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
