package com.incendiary.ambulanceapp.features.booking.patientdata;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.data.model.patient.Layanan;
import com.incendiary.ambulanceapp.features.booking.patientdata.familypicker.FamilyPickerController;
import com.incendiary.ambulanceapp.features.booking.patientdata.familypicker.FamilyPickerListener;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.ValidateUtils;
import com.incendiary.androidcommon.utils.Preconditions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class PatientDataController extends AbsController implements PatientDataView, FamilyPickerListener {

    private static final String ARG_PROPS = "PatientData.Props";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.patient_data_inp_name) EditText inpName;
    @BindView(R.id.patient_data_inp_nik) EditText inpNik;
    @BindView(R.id.patient_data_spinner_services) Spinner spinnerService;
    @BindView(R.id.patient_data_child_container) ViewGroup childContainer;

    @Inject PatientDataPresenter presenter;
    @Inject ProgressManager progressManager;
    @Inject ServiceSpinnerAdapter spinnerAdapter;

    public PatientDataController(PatientDataProps props) {
        this(new BundleBuilder()
                .putParcelable(ARG_PROPS, props)
                .build());
    }

    public PatientDataController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_patient_data_input;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.patient_data_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
        toolbar.setVisibility(View.GONE);

        presenter.loadServices();
    }

    /* --------------------------------------------------- */
    /* > OnClick */
    /* --------------------------------------------------- */

    private boolean isFormValid() {
        return ValidateUtils.runningValidationWithViews(ContextProvider.getString(R.string.error_no_data),
                inpNik, inpName);
    }

    @OnClick(R.id.patient_data_btn_picker)
    void onPickerClick() {
        getChildRouter(childContainer).setPopsLastView(true)
                .setRoot(Routes.simpleTransaction(
                        new FamilyPickerController(this),
                        new FadeChangeHandler()
                ));
    }

    @OnClick(R.id.patient_data_btn_next)
    void onNextClick() {
        if (!isFormValid()) return;

        PatientDataProps props = getArgs().getParcelable(ARG_PROPS);
        Preconditions.checkNotNull(props);

        Layanan layanan = spinnerAdapter.getItem(spinnerService.getSelectedItemPosition());
        if (layanan == null) {
            Toasts.show("Silahkan pilih jenis layanan");
            return;
        }

        presenter.submitData(
                props.getMarkerType(),
                props.getBookingId(),
                inpNik.getText().toString(),
                inpName.getText().toString(),
                layanan.getLayananId()
        );
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
    public void showServices(List<Layanan> services) {
        if (spinnerService.getAdapter() == null) {
            spinnerService.setAdapter(spinnerAdapter);
        }
        spinnerAdapter.addAll(services);
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isShow, int i) {
        progressManager.show(isShow);
    }

    @Override
    public void onFamilyPicker(Family family) {
        inpName.setText(family.getNamaLengkap());
        inpNik.setText(family.getNik());
    }
}
