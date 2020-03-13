package com.incendiary.ambulanceapp.features.auth.register;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.model.presentermodel.RegisterParam;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.HashUtils;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class RegisterPresenter extends BasePresenter<RegisterView> {

    @Inject
    public RegisterPresenter() {
    }

    public void register(RegisterParam data) {
        showLoading(true);

        api.register(
                data.getUsername(),
                data.getEmail(),
                data.getName(),
                HashUtils.md5(data.getPassword()),
                data.getNoKtp(),
                data.getNoTelp(),
                data.getMotherName(),
                data.getDob(),
                data.getDomicileStatus()
        ).compose(Transformers.applyApiCallBase())
                .doAfterTerminate(() -> showLoading(false))
                .subscribe(res -> {
                    getView().showRegisterSuccess(res);
                }, e -> ErrorHandler.handleError(e, getView()));
    }
}
