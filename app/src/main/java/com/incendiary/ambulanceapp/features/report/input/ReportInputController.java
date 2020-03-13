package com.incendiary.ambulanceapp.features.report.input;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bumptech.glide.Glide;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.report.ReportCategory;
import com.incendiary.ambulanceapp.features.report.inputmap.ReportInputMapController;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.ValidateUtils;
import com.incendiary.androidcommon.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;

public class ReportInputController extends AbsController implements ReportInputView {

    private static final String ARG_CATEGORY = "ReportInput.Category";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.report_input_img_report) ImageView imgContent;
    @BindView(R.id.report_input_img_category) ImageView imgCategory;
    @BindView(R.id.report_input_inp_title) EditText inpTitle;
    @BindView(R.id.report_input_inp_desc) EditText inpDesc;
    @BindView(R.id.report_input_txt_category) TextView txtCategory;

    @Inject ReportInputPresenter presenter;
    @Inject ProgressManager progressManager;

    public ReportInputController(ReportCategory reportCategory) {
        this(new BundleBuilder()
                .putParcelable(ARG_CATEGORY, reportCategory)
                .build());
    }

    public ReportInputController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_input;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.report);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finishActivity());
        toolbar.inflateMenu(R.menu.menu_report_input);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_send) {
                sendReport();
            }
            return true;
        });

        final ReportCategory reportCategory = getArgs().getParcelable(ARG_CATEGORY);
        Preconditions.checkNotNull(reportCategory);
        presenter.init(reportCategory);

        txtCategory.setText(reportCategory.getNamaKategori());
    }

    /* --------------------------------------------------- */
    /* > OnClicks */
    /* --------------------------------------------------- */

    private boolean isFormValid() {
        return ValidateUtils.runningValidationWithViews(ContextProvider.getString(R.string.error_no_data), inpTitle);
    }

    void sendReport() {
        if (!isFormValid()) return;

        final ReportCategory reportCategory = getArgs().getParcelable(ARG_CATEGORY);
        Preconditions.checkNotNull(reportCategory);

        goToReportInputMap(
                reportCategory.getKategoriId(),
                inpTitle.getText().toString(),
                inpDesc.getText().toString()
        );
    }

    private void goToReportInputMap(String categoryId, String title, String desc) {
        getRouter().pushController(Routes.simpleTransaction(
                new ReportInputMapController(categoryId, title, desc),
                new HorizontalChangeHandler()
        ));
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showImage(String path) {
        Glide.with(getActivity())
                .load(path)
                .into(imgContent);
    }

    @Override
    public void showCategoryImage(String path) {
        Glide.with(getActivity())
                .load(path)
                .into(imgCategory);
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int i) {
        progressManager.show(isLoading);
    }

    @Override
    public void showSuccess(String message) {
        Toasts.show(message);
        finishActivity();
    }
}
