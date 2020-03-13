package com.incendiary.ambulanceapp.features.report.etc;

import android.view.View;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;

import butterknife.OnClick;

public class ReportSuccessController extends AbsController {

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_success;
    }

    @Override
    protected void onViewBound(View view) {
    }

    @OnClick(R.id.report_success_btn_home)
    void onHomeClick() {
        finishActivity();
    }

    @Override
    public boolean handleBack() {
        finishActivity();
        return true;
    }
}
