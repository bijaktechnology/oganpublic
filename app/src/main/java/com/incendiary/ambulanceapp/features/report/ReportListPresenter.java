package com.incendiary.ambulanceapp.features.report;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.model.common.ApiResponse;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;
import com.incendiary.ambulanceapp.utils.recyclerviews.Paginate;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

import rx.Observable;

public class ReportListPresenter extends BasePresenter<ReportListView> implements Paginate.Callback {

    private final PageHelper pageHelper;

    @Inject
    public ReportListPresenter(PageHelper pageHelper) {
        this.pageHelper = pageHelper;
    }

    public void loadPopularReports() {
        api.getPopularReports()
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(reports -> {
                    getView().showPopularReports(reports);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void loadReport(boolean isRefresh) {
        if (isRefresh) {
            pageHelper.reset();
        }

        pageHelper.hold();

        api.getReports(pageHelper.getCurrentPage())
                .compose(Transformers.applyScheduler())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(res -> {
                    if (res.isSuccess()) {
                        getView().showReports(res.getData(), pageHelper);
                        pageHelper.nextPage();
                        pageHelper.setLastPage(res.getData().size() < pageHelper.getItemPerPage());
                    } else {
                        showError(new IllegalStateException(res.getMessage()));
                    }

                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    /* --------------------------------------------------- */
    /* > Like */
    /* --------------------------------------------------- */

    public void likeReport(final Report report) {
        final boolean isLike = !report.isStatusLike();
        getLikeObservable(isLike, report.getLaporanId())
                .compose(Transformers.applyApiCallBase())
                .subscribe(apiResponse -> {
                    getView().showLikeUpdate(report, isLike);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    private Observable<ApiResponse> getLikeObservable(boolean isLike, String reportId) {
        return isLike
                ? api.likeReport(reportId)
                : api.unlikeReport(reportId);
    }

    /* --------------------------------------------------- */
    /* > Pageinate */
    /* --------------------------------------------------- */

    @Override
    public void onLoadMore() {
        loadReport(false);
    }

    @Override
    public boolean isLoading() {
        return pageHelper.isOnHold();
    }

    @Override
    public boolean hasLoadedAllItems() {
        return pageHelper.isOnLastPage();
    }
}
