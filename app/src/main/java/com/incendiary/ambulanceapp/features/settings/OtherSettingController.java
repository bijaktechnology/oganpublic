package com.incendiary.ambulanceapp.features.settings;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.features.config.ConfigManager;
import com.incendiary.ambulanceapp.features.settings.misc.AboutController;
import com.incendiary.ambulanceapp.features.settings.misc.DisclaimerController;
import com.incendiary.ambulanceapp.utils.AppUtils;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.ambulanceapp.utils.rx.Transformers;
import com.incendiary.androidcommon.android.Intents;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;

public class OtherSettingController extends AbsController implements OtherSettingView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.other_setting_recyclerview) RecyclerView recyclerView;

    @Inject OtherSettingsListAdapter adapter;
    @Inject OtherSettingPresenter presenter;
    @Inject ConfigManager configManager;
    @Inject ProgressManager progressManager;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_other_setting;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.other_setting_title);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        adapter.setOnItemClickListener(this::handleItemClick);
        adapter.pushData(SettingsData.getOtherSettings());

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        presenter.init();
    }

    private void handleItemClick(int position) {
        switch (position) {
            case 1:
                goToTutorial();
                break;
            case 2:
                goToAboutController();
                break;
            case 3:
                goToDisclaimerController();
                break;
            case 4:
                checkForUpdate();
                break;
        }
    }

    private void goToTutorial() {
        Context context = getActivity();
        Preconditions.checkNotNull(context);
        Intents.viewAction(context, "https://youtu.be/41UcbfvOyOU");
    }

    private void goToAboutController() {
        getRouter().pushController(Routes.simpleTransaction(
                new AboutController(),
                new HorizontalChangeHandler()
        ));
    }

    private void goToDisclaimerController() {
        getRouter().pushController(Routes.simpleTransaction(
                new DisclaimerController(),
                new HorizontalChangeHandler()
        ));
    }

    private void checkForUpdate() {
        progressManager.show(true);

        configManager.shouldUpdate()
                .compose(Transformers.asyncTask())
                .doAfterTerminate(() -> progressManager.show(false))
                .subscribe(isShouldUpdate -> {

                    Activity activity = getActivity();
                    if (activity == null) {
                        return;
                    }

                    new AlertDialog.Builder(activity)
                            .setTitle(isShouldUpdate
                                    ? "Update Available"
                                    : "No Update")
                            .setMessage(isShouldUpdate
                                    ? "Ada update versi terbaru yang tersedia, lakukan update?"
                                    : "Tidak ada pembaharuan")
                            .setPositiveButton(isShouldUpdate
                                    ? "Update"
                                    : "OK", (dialog, which) -> {
                                if (isShouldUpdate) {
                                    AppUtils.goToStore();
                                }
                            })
                            .show();
                });
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void setupSwitch(boolean isSwitchOn) {
        adapter.setSwitch(isSwitchOn);
        adapter.setOnSwitch((isChecked, integer) -> presenter.setPushNotifStatus(isChecked));
    }

    @Override
    public void showPushNotificationStatusUpdate(boolean isSuccess) {
        Toasts.show(isSuccess
                ? "Push Notification Setting Updated!"
                : "Failed to update Push Notification");
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean b, int i) {
        progressManager.show(b);
    }
}
