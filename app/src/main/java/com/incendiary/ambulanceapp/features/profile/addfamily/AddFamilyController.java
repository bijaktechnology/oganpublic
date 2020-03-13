package com.incendiary.ambulanceapp.features.profile.addfamily;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.rx.RxEditText;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.ValidateUtils;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFamilyController extends AbsController implements AddFamilyView {

    private static final String ARG_FAMILY = "AddFamily.Family";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.add_family_inp_name) EditText inpName;
    @BindView(R.id.add_family_inp_status) TextInputEditText inpStatus;
    @BindView(R.id.add_family_inp_ktp) TextInputEditText inpKtp;
    @BindView(R.id.add_family_inp_dob) TextInputEditText inpDob;
    @BindView(R.id.add_family_inp_mother_name) TextInputEditText inpMotherName;

    @Inject AddFamilyPresenter presenter;
    @Inject ProgressManager progressManager;

    public AddFamilyController() {
    }

    public AddFamilyController(Family family) {
        this(new BundleBuilder()
                .putParcelable(ARG_FAMILY, family)
                .build());
    }

    public AddFamilyController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_add_family;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.profile_add_family);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        RxEditText.bindDob(getActivity(), inpDob)
                .compose(bindToLifecycle())
                .forEach(s -> inpDob.setText(s));

        setupForm();
    }

    private void setupForm() {
        if (getArgs().containsKey(ARG_FAMILY)) {
            Family family = getArgs().getParcelable(ARG_FAMILY);
            Preconditions.checkNotNull(family);

            inpName.setText(family.getNamaLengkap());
            inpMotherName.setText(family.getNamaIbuKandung());
            inpDob.setText(family.getTglLahir());
            inpKtp.setText(family.getNik());
            inpStatus.setText(family.getStatusKeluarga());
        }
    }

    private boolean isFormValid() {
        return ValidateUtils.runningValidationWithViews(ContextProvider.getString(R.string.error_no_data,
                inpMotherName, inpName, inpKtp, inpStatus));
    }

    @OnClick(R.id.add_family_btn_save)
    void onSaveClick() {
        if (!isFormValid()) return;

        if (getArgs().containsKey(ARG_FAMILY)) {

            Family family = getArgs().getParcelable(ARG_FAMILY);
            Preconditions.checkNotNull(family);

            presenter.editFamily(new Family(
                    family.getAnggotaId(),
                    inpKtp.getText().toString(),
                    inpName.getText().toString(),
                    inpDob.getText().toString(),
                    inpMotherName.getText().toString(),
                    inpStatus.getText().toString()
            ));
        } else {
            presenter.addFamily(new Family(
                    inpKtp.getText().toString(),
                    inpName.getText().toString(),
                    inpDob.getText().toString(),
                    inpMotherName.getText().toString(),
                    inpStatus.getText().toString()
            ));
        }

    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showSuccess(String message) {
        Toasts.show(message);
        popCurrentController();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean showLoading, int i) {
        progressManager.show(showLoading);
    }
}
