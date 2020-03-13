package com.incendiary.ambulanceapp.features.report.reportfeedback;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.report.ReportFeedback;

public interface ReportFeedbackView extends MvpLoadingView, MvpErrorView {
    void showReportFeedback(ReportFeedback reportFeedback);
}
