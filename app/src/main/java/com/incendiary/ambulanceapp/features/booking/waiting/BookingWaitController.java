package com.incendiary.ambulanceapp.features.booking.waiting;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.BaseResponse;
import com.incendiary.ambulanceapp.data.model.detail.AmbulanceDetail;
import com.incendiary.ambulanceapp.data.model.detail.BidanDetail;
import com.incendiary.ambulanceapp.data.model.detail.BookingDetail;
import com.incendiary.ambulanceapp.data.model.detail.DoctorDetail;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;
import com.incendiary.ambulanceapp.features.booking.BookingUtils;
import com.incendiary.ambulanceapp.features.booking.patientdata.PatientDataController;
import com.incendiary.ambulanceapp.features.booking.patientdata.PatientDataProps;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class BookingWaitController extends AbsController implements BookingWaitView {

    private static final String ARG_PROPS = "BookingWait.MapType";

    @BindView(R.id.booking_wait_txt_caption) TextView txtCaption;
    @BindView(R.id.booking_wait_child_container) ViewGroup childContainer;
    @BindView(R.id.booking_wait_plat_container) View platContainer;

    @BindView(R.id.booking_wait_txt_name) TextView txtName;
    @BindView(R.id.booking_wait_txt_phone) TextView txtPhone;
    @BindView(R.id.booking_wait_txt_plat) TextView txtPlat;

    @Inject BookingWaitPresenter presenter;
    @Inject ProgressManager progressManager;

    /* --------------------------------------------------- */
    /* > Constructor */
    /* --------------------------------------------------- */

    public BookingWaitController(BookingWaitProps props) {
        this(new BundleBuilder()
                .putParcelable(ARG_PROPS, props)
                .build());
    }

    public BookingWaitController(Bundle args) {
        super(args);
    }

    /* --------------------------------------------------- */
    /* > Setup */
    /* --------------------------------------------------- */

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_booking_wait;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter.setProps(getProps()));
        setupView();

        final String placeholder = "Menunggu data…";
        txtName.setText(placeholder);
        txtPlat.setText(placeholder);
        txtPhone.setText(placeholder);
    }

    private void setupView() {
        final int mapType = getMapType();
        txtCaption.setText(BookingUtils.getCompleteCaptionText(mapType));

        if (mapType != MarkerType.AMBULANCE) {
            platContainer.setVisibility(View.GONE);
        }
    }

    private BookingWaitProps getProps() {
        return getArgs().getParcelable(ARG_PROPS);
    }

    private Router getChildRouter() {
        return getChildRouter(childContainer);
    }

    /* --------------------------------------------------- */
    /* > OnClicks */
    /* --------------------------------------------------- */

    private int getMapType() {
        BookingWaitProps props = getProps();
        Preconditions.checkNotNull(props);
        return props.getType();
    }

    @OnClick(R.id.booking_wait_btn_complete)
    public void onCompleteClick() {
        BookingWaitProps props = getProps();
        presenter.completeBooking(props.getType(), props.getBookingId());
    }

    @OnClick(R.id.booking_wait_btn_cancel)
    public void onCancelClick() {
        BookingWaitProps props = getProps();
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.cancel_prompt)
                .setPositiveButton(R.string.yes, (dialog, which) ->
                        presenter.cancelBooking(props.getType(), props.getBookingId()))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @OnClick(R.id.booking_wait_btn_insert)
    void onInsertDataClick() {
        BookingWaitProps props = getArgs().getParcelable(ARG_PROPS);
        Preconditions.checkNotNull(props);

        getChildRouter().setPopsLastView(true)
                .pushController(Routes.simpleTransaction(
                        new PatientDataController(new PatientDataProps(props.getBookingId(), props.getType())),
                        new VerticalChangeHandler()
                ));
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showBookingComplete(BaseResponse response, boolean isCanceled) {
        Toasts.show(response.getMessage());
        finishActivity();
    }

    @Override
    public void showBookingDetail(BookingDetail bookingDetail) {
        if (bookingDetail instanceof AmbulanceDetail) {
            AmbulanceDetail ambulanceDetail = (AmbulanceDetail) bookingDetail;
            txtName.setText(ambulanceDetail.getNamaDriver());
            txtPlat.setText(ambulanceDetail.getNoPlat());
            txtPhone.setText(ambulanceDetail.getNoTelp());
            return;
        }

        if (bookingDetail instanceof DoctorDetail) {
            DoctorDetail doctorDetail = (DoctorDetail) bookingDetail;
            txtName.setText(doctorDetail.getNamaDokter());
            txtPhone.setText(doctorDetail.getNoTelp());
        }

        if (bookingDetail instanceof BidanDetail) {
            BidanDetail bidanDetail = (BidanDetail) bookingDetail;
            txtName.setText(bidanDetail.getNamaBidan());
            txtPhone.setText(bidanDetail.getNoTelp());
        }
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
