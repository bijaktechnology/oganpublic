package com.incendiary.ambulanceapp.features.authconfirm;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.features.auth.register.DomicileStatus;
import com.incendiary.ambulanceapp.features.auth.register.RegisterController;
import com.incendiary.ambulanceapp.features.auth.register.RegisterProps;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.Toasts;

import butterknife.BindView;
import butterknife.OnClick;

public class DomicileConfirmationController extends AbsController {

    private static final String ARG_IS_EDIT = "DomicileConfirmation.IsEdit";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.confirmation_radio_group) RadioGroup radioGroup;

    public DomicileConfirmationController(boolean isEdit) {
        this(new BundleBuilder()
                .putBoolean(ARG_IS_EDIT, isEdit)
                .build());
    }

    public DomicileConfirmationController(Bundle args) {
        super(args);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_auth_confirmation_domicile;
    }

    @Override
    protected void onViewBound(View view) {
        final boolean isEdit = getArgs().getBoolean(ARG_IS_EDIT);

        toolbar.setTitle(isEdit ? R.string.profile_change_domicile : R.string.register);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
    }

    /* --------------------------------------------------- */
    /* > OnClicks */
    /* --------------------------------------------------- */

    private boolean isValid() {
        if (radioGroup.getCheckedRadioButtonId() < 0) {
            Toasts.show("Anda harus memilih terlabih dahulu");
            return false;
        }
        return true;
    }

    @OnClick(R.id.auth_confirm_btn_next)
    void onNextClick() {
        if (!isValid()) return;

        final DomicileStatus domicileStatus = radioGroup.getCheckedRadioButtonId() == R.id.confirmation_radio_purwakarta
                ? DomicileStatus.PURWAKARTA
                : DomicileStatus.NON_PURWAKARTA;

        final boolean isEdit = getArgs().getBoolean(ARG_IS_EDIT);

        if (domicileStatus == DomicileStatus.NON_PURWAKARTA && !isEdit) {
            goToRegister(domicileStatus);
        } else {
            goToKtpConfirmation(domicileStatus, isEdit);
        }
    }

    private void goToKtpConfirmation(DomicileStatus domicileStatus, boolean isEdit) {
        getRouter().pushController(Routes.simpleTransaction(
                new KtpConfirmationController(domicileStatus, isEdit),
                new HorizontalChangeHandler()
        ));
    }

    private void goToRegister(DomicileStatus status) {
        RegisterController.goTo(getRouter(), new RegisterProps(
                status,
                ""
        ));
    }
}
