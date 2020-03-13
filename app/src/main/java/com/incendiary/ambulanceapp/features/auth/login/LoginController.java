package com.incendiary.ambulanceapp.features.auth.login;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.incendiary.ambulanceapp.BuildConfig;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.Profile;
import com.incendiary.ambulanceapp.features.auth.forgotpassword.ForgotPasswordAct;
import com.incendiary.ambulanceapp.features.authconfirm.DomicileConfirmationController;
import com.incendiary.ambulanceapp.features.landing.MainMenuAct;
import com.incendiary.ambulanceapp.utils.KeyboardUtils;
import com.incendiary.ambulanceapp.utils.Launcher;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.ValidateUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginController extends AbsController implements LoginView {

    @BindView(R.id.inpEmail) EditText inpEmail;
    @BindView(R.id.inpPassword) EditText inpPassword;

    @Inject LoginPresenter presenter;
    @Inject ProgressManager progressManager;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_login;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);
        if (BuildConfig.DEBUG) {
            inpEmail.setText("usertest");
            inpPassword.setText("password");
        }

        addLifecycleListener(new LifecycleListener() {
            @Override
            public void preAttach(@NonNull Controller controller, @NonNull View view) {
                KeyboardUtils.changeKeyboardConfig(getActivity(), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            }

            @Override
            public void preDetach(@NonNull Controller controller, @NonNull View view) {
                KeyboardUtils.changeKeyboardConfig(getActivity(), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
        });
    }

    /* --------------------------------------------------- */
    /* > OnClick */
    /* --------------------------------------------------- */

    private boolean isValid() {
        return ValidateUtils.runningValidationWithViews(ContextProvider.getString(R.string.error_empty),
                inpEmail, inpPassword);
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick() {
        if (isValid()) {
            presenter.login(
                    inpEmail.getText().toString(),
                    inpPassword.getText().toString()
            );
        }
    }

    @OnClick(R.id.btnDone)
    public void onRegisterClick() {
        getRouter().pushController(Routes.simpleTransaction(
                new DomicileConfirmationController(false),
                new VerticalChangeHandler()
        ));
    }

    @OnClick(R.id.btnExpand)
    public void onForgotPasswordClick() {
        Launcher.launch(getActivity(), ForgotPasswordAct.class);
    }

  /* --------------------------------------------------- */
  /* > View Methods */
  /* --------------------------------------------------- */

    @Override
    public void showLoginSuccess(Profile profile) {
        Launcher.launchThenFinish(getActivity(), MainMenuAct.class);
    }

    @Override
    public void showLoading(boolean isLoading, int type) {
        progressManager.show(isLoading);
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }
}
