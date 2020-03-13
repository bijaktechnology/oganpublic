package com.incendiary.ambulanceapp.features.choosecategory;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class ChooseCategoryPresenter extends BasePresenter<ChooseCategoryView> {

    @Inject
    public ChooseCategoryPresenter() {
    }

    public void loadReportCategories() {
        api.getReportCategories()
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(categories -> {
                    getView().showContent(categories);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
