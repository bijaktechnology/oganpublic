package com.incendiary.ambulanceapp.features.landing;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.incendiary.ambulanceapp.OganApp;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;
import com.incendiary.ambulanceapp.features.auth.login.LoginAct;
import com.incendiary.ambulanceapp.features.auth.register.DomicileStatus;
import com.incendiary.ambulanceapp.features.main.MainAct;
import com.incendiary.ambulanceapp.features.main.MainProps;
import com.incendiary.ambulanceapp.features.report.ReportListActivity;
import com.incendiary.ambulanceapp.features.settings.SettingsController;
import com.incendiary.ambulanceapp.features.sos.SosController;
import com.incendiary.ambulanceapp.features.tours.TourActivity;
import com.incendiary.ambulanceapp.utils.AppUtils;
import com.incendiary.ambulanceapp.utils.Launcher;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.Intents;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action0;

public class MainMenuController extends AbsController implements MainMenuView {

    @BindView(R.id.main_menu_child_container) ViewGroup childContainer;

    @Inject MainMenuPresenter presenter;
    @Inject ProgressManager progressManager;

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_main_menu;
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_rate_app:
                AppUtils.goToStore();
                break;
            case R.id.action_logout:
                DataStore.clearProfile();
                Launcher.launchThenFinish(getActivity(), LoginAct.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /* --------------------------------------------------- */
    /* > OnClicks */
    /* --------------------------------------------------- */

    @OnClick(R.id.menu_btn_setting)
    void onSettingClick() {
        getRouter().pushController(Routes.simpleTransaction(
                new SettingsController(),
                new VerticalChangeHandler()
        ));
    }

    @OnClick(R.id.main_menu_sos)
    void onSosClick() {
        showIfPossible(() -> getRouter().pushController(Routes.simpleTransaction(
                new SosController(),
                new VerticalChangeHandler()
        )));
    }

    @OnClick({
            R.id.main_menu_semar,
            R.id.main_menu_bidan,
            R.id.main_menu_doctor,
            R.id.main_menu_laporan,
            R.id.main_menu_wisata,
            R.id.main_menu_lokasi_penting,
            R.id.main_menu_bursa
    })
    public void onClick(View view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(0.4f)
                .scaleY(0.8f)
                .scaleX(0.8f)
                .withEndAction(() -> {
                    navigateTo(view);

                    OganApp.component().mainHandler().postDelayed(() -> {
                        ViewCompat.setScaleX(view, 1f);
                        ViewCompat.setScaleY(view, 1f);
                        ViewCompat.setAlpha(view, 1f);
                    }, 300);
                });
    }

    private void navigateTo(View view) {
        Context context = Preconditions.checkNotNull(getActivity());;

        switch (view.getId()) {
            case R.id.main_menu_semar:
                showIfPossible(() -> presenter.checkActiveBooking(MarkerType.AMBULANCE));
                break;
            case R.id.main_menu_lokasi_penting:
                MainAct.start(context, MarkerType.POI);
                break;
            case R.id.main_menu_doctor:
                showIfPossible(() -> presenter.checkActiveBooking(MarkerType.DOCTOR));
                break;
            case R.id.main_menu_bidan:
                showIfPossible(() -> presenter.checkActiveBooking(MarkerType.BIDAN));
                break;
            case R.id.main_menu_laporan:
                showIfPossible(() -> ReportListActivity.start(getActivity()));
                break;
            case R.id.main_menu_wisata:
                TourActivity.start(context);
                break;
            case R.id.main_menu_bursa:
                Intents.viewAction(context, "http://disnakertrans.purwakartakab.go.id/lowongan");
        }
    }

    private void showIfPossible(Action0 ifPossible) {
        DataStore.getProfile()
                .forEach(profile -> {
                    if (profile.getStatusDomisili() == DomicileStatus.NON_PURWAKARTA) {
                        InputDataPromptController.show(getChildRouter(childContainer));
                    } else {
                        ifPossible.call();
                    }
                });
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void navigateToBooking(int markerType) {
        MainAct.start(getActivity(), markerType);
    }

    @Override
    public void navigateToBookingWait(int markerType, String bookingId) {
        Toasts.show("Anda masih memiliki panggilan yang aktif");
        MainAct.start(getActivity(), new MainProps(
                markerType,
                true,
                bookingId
        ));
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isShow, int i) {
        progressManager.show(isShow);
    }
}
