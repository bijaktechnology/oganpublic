package com.incendiary.ambulanceapp.features.report.bookmark;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;

import java.util.List;

public interface BookmarkListView extends MvpLoadingView, MvpErrorView {
    void showContent(List<Report> tours, PageHelper pageHelper);
}
