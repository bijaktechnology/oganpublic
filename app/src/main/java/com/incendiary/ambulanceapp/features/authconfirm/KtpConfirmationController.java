package com.incendiary.ambulanceapp.features.authconfirm;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.features.auth.register.DomicileStatus;
import com.incendiary.ambulanceapp.features.auth.register.RegisterController;
import com.incendiary.ambulanceapp.features.auth.register.RegisterProps;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.androidcommon.android.Toasts;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class KtpConfirmationController extends AbsController implements KtpConfirmationView {

    private static final int NIK_LENGTH = 16;

    private static final String ARG_DOMICILE = "KtpConfirmation.Domicile";
    private static final String ARG_IS_EDIT = "KtpConfirmation.IsEdit";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.auth_confirm_inp_ktp) EditText inpKtp;
    @BindView(R.id.auth_confirm_btn_change_domicile) View btnChangeDomicile;
    @BindView(R.id.auth_confirm_btn_next) View btnNext;

    @Inject KtpConfirmationPresenter presenter;
    @Inject ProgressManager progressManager;

    public KtpConfirmationController(DomicileStatus domicileStatus, boolean isEdit) {
        this(new BundleBuilder()
                .putSerializable(ARG_DOMICILE, domicileStatus)
                .putBoolean(ARG_IS_EDIT, isEdit)
                .build());
    }

    public KtpConfirmationController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_auth_confirmation_ktp;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        final boolean isEdit = isEdit();

        toolbar.setTitle(isEdit ? R.string.profile_change_domicile : R.string.register);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        btnNext.setVisibility(isEdit ? View.GONE : View.VISIBLE);
        btnChangeDomicile.setVisibility(isEdit ? View.VISIBLE : View.GONE);
    }

    /* --------------------------------------------------- */
    /* > OnClicks */
    /* --------------------------------------------------- */

    private boolean isEdit() {
        return getArgs().getBoolean(ARG_IS_EDIT, false);
    }

    private boolean isValid() {
        if (inpKtp.getText().length() != NIK_LENGTH) {
            inpKtp.setError("NIK harus 16 digit angka");
            inpKtp.requestFocus();
            return false;
        }
        return true;
    }

    @OnClick(R.id.auth_confirm_btn_change_domicile)
    void onChangeDomicileClick() {
        if (!isValid()) return;

        DomicileStatus status = getDomicileStatus();
        if (status == DomicileStatus.NON_PURWAKARTA) {
            presenter.editDomicile(status, inpKtp.getText().toString());
        } else {
            presenter.verifyNik(inpKtp.getText().toString());
        }
    }

    @OnClick(R.id.auth_confirm_btn_next)
    void onNextClick() {
        DomicileStatus status = getDomicileStatus();
        if (status == DomicileStatus.NON_PURWAKARTA) {
            goToRegister();
        } else {
            if (isValid()) {
                presenter.verifyNik(inpKtp.getText().toString());
            }
        }
    }

    private void goToRegister() {
        RegisterController.goTo(getRouter(), new RegisterProps(
                getDomicileStatus(),
                inpKtp.getText().toString()
        ));
    }

    private DomicileStatus getDomicileStatus() {
        return (DomicileStatus) getArgs().getSerializable(ARG_DOMICILE);
    }

    /* --------------------------------------------------- */
    /* > View Mehtods */
    /* --------------------------------------------------- */

    @Override
    public void showVerificationComplete(String message, String nik) {
        DomicileStatus status = getDomicileStatus();
        if (status == DomicileStatus.NON_PURWAKARTA || !isEdit()) {
            goToRegister();
        } else {
            presenter.editDomicile(getDomicileStatus(), nik);
        }
    }

    @Override
    public void showEditSuccess(String message) {
        Toasts.show(message);
        getRouter().popToRoot();
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
