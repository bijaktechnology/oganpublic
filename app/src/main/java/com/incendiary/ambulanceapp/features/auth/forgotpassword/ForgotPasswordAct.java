package com.incendiary.ambulanceapp.features.auth.forgotpassword;

import android.os.Bundle;
import android.widget.EditText;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.activity.BaseActivity;
import com.incendiary.ambulanceapp.common.dialog.ProgressDialog;
import com.incendiary.ambulanceapp.data.model.BaseResponse;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.ValidateUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordAct extends BaseActivity implements ForgotPasswordView {

    @BindView(R.id.inpEmail) EditText mInpEmail;

    @Inject ForgotPasswordPresenter mPresenter;
    @Inject ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        bindPresenter(mPresenter, this);
    }

    private void setupView() {
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnClear)
    public void onCancelClick() {
        finish();
    }

    @OnClick(R.id.btnSubmit)
    public void onResetClick() {
        if (ValidateUtils.isNotEmpty(mInpEmail, getString(R.string.error_empty))) {
            mPresenter.resetPassword(mInpEmail.getText().toString());
        }
    }

  /* --------------------------------------------------- */
  /* > Views Method */
  /* --------------------------------------------------- */

    @Override
    public void showLoading(boolean isLoading, int type) {
        if (isLoading) {
            mProgressDialog
                    .uncancelable()
                    .show(getBaseFragmentManager());
        } else {
            mProgressDialog.dismissAllowingStateLoss();
        }
    }

    @Override
    public void showSuccessResetPassword(BaseResponse response) {
        Toasts.show(response.getMessage());
        finish();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }
}
