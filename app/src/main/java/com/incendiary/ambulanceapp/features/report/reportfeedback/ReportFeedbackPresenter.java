package com.incendiary.ambulanceapp.features.report.reportfeedback;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class ReportFeedbackPresenter extends BasePresenter<ReportFeedbackView> {

    @Inject
    public ReportFeedbackPresenter() {
    }

    public void loadFeedback(Report report) {
        api.getStatusLaporan(report.getLaporanId())
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(reportFeedback -> {
                    getView().showReportFeedback(reportFeedback);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
