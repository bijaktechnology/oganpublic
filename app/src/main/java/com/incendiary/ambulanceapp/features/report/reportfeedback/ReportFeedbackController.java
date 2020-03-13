package com.incendiary.ambulanceapp.features.report.reportfeedback;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.data.model.report.ReportFeedback;
import com.incendiary.ambulanceapp.data.model.report.ReportFeedbacks;
import com.incendiary.ambulanceapp.data.model.report.ReportStatus;
import com.incendiary.ambulanceapp.data.model.report.Reports;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.Animates;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportFeedbackController extends AbsController implements ReportFeedbackView {

    private static final String ARG_REPORT = "ReportFeedback.Report";

    @BindView(R.id.report_feedback_img_content) ImageView imgContent;
    @BindView(R.id.report_feedback_txt_status) TextView txtStatus;
    @BindView(R.id.report_feedback_txt_location) TextView txtLocation;
    @BindView(R.id.report_feedback_txt_caption) TextView txtCaption;
    @BindView(R.id.report_feedback_txt_resolver) TextView txtResolver;
    @BindView(R.id.report_feedback_txt_time) TextView txtTime;
    @BindView(R.id.report_feedback_progress) View progress;

    @Inject ReportFeedbackPresenter presenter;

    public ReportFeedbackController(Report report) {
        this(new BundleBuilder()
                .putParcelable(ARG_REPORT, report)
                .build());
    }

    public ReportFeedbackController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_feedback;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        final Report report = getArgs().getParcelable(ARG_REPORT);
        Preconditions.checkNotNull(report);

        Glide.with(getActivity())
                .load(report.getFotoLaporan())
                .into(imgContent);

        txtStatus.setText(Reports.getStatusText(report));
        txtStatus.setBackgroundResource(Reports.getStatusBackground(report));
        txtLocation.setText(Reports.getReportLocation(report));

        if (report.getStatusLaporan() == ReportStatus.WAITING) {
            txtCaption.setText("Belum ada tindak lanjut untuk laporan ini");
            return;
        }

        presenter.loadFeedback(report);
    }

    @OnClick(R.id.report_feedback_container)
    void onContainerClick() {
        popCurrentController();
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showReportFeedback(ReportFeedback feedback) {
        txtLocation.setVisibility(View.VISIBLE);

        if (feedback.getStatusLaporan() == ReportStatus.PROCESSING) {
            txtCaption.setText("Laporan sedang diproses oleh petugas dari dinas");
            return;
        }

        if (feedback.getStatusLaporan() == ReportStatus.COMPLETE) {
            txtCaption.setText("Perbaikan telah selesai");

            txtResolver.setVisibility(View.VISIBLE);
            txtResolver.setText(feedback.getNamaDinas());

            txtTime.setVisibility(View.VISIBLE);
            txtTime.setText(ReportFeedbacks.getRelativeTimeWihInfo(feedback));

            Glide.with(getActivity())
                    .load(feedback.getFotoPenyelesaian())
                    .into(imgContent);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isVisible, int i) {
        Animates.visibility(progress, isVisible);
    }
}
