package com.incendiary.ambulanceapp.features.auth.register;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.digits.sdk.android.Digits;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.BaseResponse;
import com.incendiary.ambulanceapp.data.model.presentermodel.RegisterParam;
import com.incendiary.ambulanceapp.features.auth.MessageEmailController;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.PrefUtils;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.rx.RxEditText;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.ValidateUtils;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.incendiary.androidcommon.android.ContextProvider.getString;

public class RegisterController extends AbsController implements RegisterView {

    private static final String ARG_PROPS = "RegisterController.Props";

    @BindView(R.id.register_inp_name) EditText inpName;
    @BindView(R.id.register_inp_email) EditText inpEmail;
    @BindView(R.id.register_inp_password) EditText inpPassword;
    @BindView(R.id.register_inp_phone) EditText inpPhone;
    @BindView(R.id.register_inp_username) TextInputEditText inpUsername;
    @BindView(R.id.register_inp_dob) TextInputEditText inpDob;
    @BindView(R.id.register_inp_mother_name) TextInputEditText inpMotherName;

    @Inject RegisterPresenter presenter;
    @Inject ProgressManager progressManager;

    public RegisterController(RegisterProps props) {
        this(new BundleBuilder()
                .putParcelable(ARG_PROPS, props)
                .build());
    }

    public RegisterController(Bundle args) {
        super(args);
    }

    @Override
    protected void onInit() {
        Digits.logout();
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_register;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);
        setupViews();
    }

    private void setupViews() {
        RegisterProps props = Preconditions.checkNotNull(getArgs().getParcelable(ARG_PROPS));
        ((View) inpMotherName.getParent()).setVisibility(props.getDomicileStatus() == DomicileStatus.NON_PURWAKARTA
                ? View.GONE
                : View.VISIBLE);

        bindBod();
    }

    private void bindBod() {
        RxEditText.bindDob(getActivity(), inpDob)
                .compose(bindToLifecycle())
                .forEach(s -> {
                    inpDob.setText(s);
                });
    }

    private boolean isValid() {
        boolean isNotEmpty = ValidateUtils
                .runningValidationWithViews(getString(R.string.error_empty), inpUsername,
                        inpEmail, inpPassword, inpName, inpPhone);

        boolean isEmailValid = Patterns.EMAIL_ADDRESS.matcher(inpEmail.getText().toString()).matches();

        if (!isEmailValid) {
            inpEmail.setError("Email must be well-formed");
        }
        return isEmailValid && isNotEmpty;
    }

    @OnClick(R.id.register_btn_submit)
    public void onSubmitClick() {
        if (!isValid()) return;
        register();
    }

    private void register() {
        RegisterProps props = getArgs().getParcelable(ARG_PROPS);
        Preconditions.checkNotNull(props);

        presenter.register(new RegisterParam(
                inpUsername.getText().toString(),
                inpEmail.getText().toString(),
                inpName.getText().toString(),
                inpPassword.getText().toString(),
                props.getNik(),
                inpPhone.getText().toString(),
                inpMotherName.getText().toString(),
                inpDob.getText().toString(),
                props.getDomicileStatus().getValue()
        ));
    }

    @Override
    public void showLoading(boolean isLoading, int type) {
        progressManager.show(isLoading);
    }

    @Override
    public void showRegisterSuccess(BaseResponse baseResponse) {
        Toasts.show(baseResponse.getMessage());
        PrefUtils.saveStatusFirstRun(getActivity(), true);
        goToMessageEmail();
    }

    private void goToMessageEmail() {
        getRouter().replaceTopController(Routes.simpleTransaction(
                new MessageEmailController(),
                new FadeChangeHandler()
        ));
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    /* --------------------------------------------------- */
    /* > Stater */
    /* --------------------------------------------------- */

    public static void goTo(Router router, RegisterProps props) {
        router.pushController(Routes.simpleTransaction(
                new RegisterController(props),
                new VerticalChangeHandler()
        ));
    }
}
