package com.incendiary.ambulanceapp.features.profile.presenter;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.data.model.presentermodel.EditProfileParam;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.features.profile.view.ProfileView;
import com.incendiary.ambulanceapp.utils.HashUtils;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class ProfilePresenter extends BasePresenter<ProfileView> {

    @Inject
    public ProfilePresenter() {
    }

    public void loadProfile() {
        DataStore.getProfile()
                .compose(Transformers.applyScheduler())
                .subscribe(profile -> getView().showProfile(profile), ErrorHandler::handleError);
    }

    public void editProfile(EditProfileParam param) {
        DataStore.getProfile()
                .flatMap(p ->
                        api.editProfile(
                                p.getUserId(),
                                DataStore.getKeyCode(),
                                param.getUsername(),
                                param.getEmail(),
                                param.getName(),
                                HashUtils.md5(param.getPassword()),
                                param.getNik(),
                                param.getNoTelp(),
                                param.getMotehrName(),
                                param.getDob(),
                                param.getUserImg()
                        ))
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(res -> {

                    if (res.isSuccess()) {
                        final Profile profile = res.getProfile();
                        DataStore.saveProfile(profile);
                        getView().showProfile(profile);
                        getView().showEditSuccess();
                    } else {
                        showError(new IllegalStateException(res.getMessage()));

                    }
                }, e -> ErrorHandler.handleError(e, getView()));
    }
}
