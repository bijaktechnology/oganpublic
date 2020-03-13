package com.incendiary.ambulanceapp.features.report.input;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;

public interface ReportInputView extends MvpLoadingView, MvpErrorView {
    void showImage(String path);
    void showCategoryImage(String path);
    void showSuccess(String message);
}
