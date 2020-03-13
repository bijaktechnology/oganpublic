package com.incendiary.ambulanceapp.features.report.input;

import com.esafirm.imagepicker.model.Image;
import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.report.ReportCategory;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class ReportInputPresenter extends BasePresenter<ReportInputView> {

    @Inject
    public ReportInputPresenter() {
    }

    public void init(ReportCategory reportCategory) {
        DataStore.getReportImage()
                .map(Image::getPath)
                .compose(Transformers.applyScheduler())
                .subscribe(s -> getView().showImage(s), throwable -> ErrorHandler.handleError(throwable, getView()));

        getView().showCategoryImage(reportCategory.getIcon());
    }

}
