package com.incendiary.ambulanceapp.features.report;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;

import java.util.List;

public interface ReportListView extends MvpLoadingView, MvpErrorView {
    void showPopularReports(List<Report> reports);
    void showReports(List<Report> reports, PageHelper pageHelper);
    void showLikeUpdate(Report report, boolean isLike);
}
