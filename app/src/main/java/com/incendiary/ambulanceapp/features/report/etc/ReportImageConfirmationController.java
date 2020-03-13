package com.incendiary.ambulanceapp.features.report.etc;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bluelinelabs.conductor.Controller;
import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.model.Image;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.choosecategory.ChooseCategoryActivity;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.androidcommon.utils.Preconditions;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportImageConfirmationController extends AbsController {

    private static final String ARG_IMAGE = "ReportImageConfirmation.Image";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.report_confirm_img) ImageView imageView;

    public <T extends Controller & ReportConfirmationListener>
    ReportImageConfirmationController(Image image, T listener) {
        this(new BundleBuilder()
                .putParcelable(ARG_IMAGE, image)
                .build());
        setTargetController(listener);
    }

    public ReportImageConfirmationController(Bundle args) {
        super(args);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_image_confirmation;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(R.string.photo);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());


        Glide.with(getActivity())
                .load(getImage().getPath())
                .into(imageView);
    }

    private Image getImage(){
        return Preconditions.checkNotNull(getArgs().getParcelable(ARG_IMAGE));
    }

    @OnClick(R.id.report_confirm_btn_accept)
    void onAcceptClick() {
        DataStore.saveReportImage(getImage());
        popCurrentController();
        ChooseCategoryActivity.start(getActivity());
    }

    @OnClick(R.id.report_confirm_btn_cancel)
    void onCancelClick() {
        ReportConfirmationListener listener = (ReportConfirmationListener) getTargetController();
        if (listener != null) {
            listener.onCancel();
        }
        popCurrentController();
    }
}
