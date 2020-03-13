package com.incendiary.ambulanceapp.features.choosecategory;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.report.ReportCategory;

import java.util.List;

public interface ChooseCategoryView extends MvpLoadingView, MvpErrorView {
    void showContent(List<ReportCategory> categories);
}
