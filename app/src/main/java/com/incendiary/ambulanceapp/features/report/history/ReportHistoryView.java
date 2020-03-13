package com.incendiary.ambulanceapp.features.report.history;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.data.model.report.ReportStatus;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;

import java.util.List;

public interface ReportHistoryView extends MvpLoadingView, MvpErrorView {

    int LOADING_WAIT = 0;
    int LOADING_PROCESS = 1;
    int LOADING_COMPLETE = 2;

    void showReportHistories(List<Report> reports, ReportStatus reportStatus, PageHelper pageHelper);
}
