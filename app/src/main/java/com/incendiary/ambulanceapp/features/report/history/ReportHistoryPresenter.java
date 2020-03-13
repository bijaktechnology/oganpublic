package com.incendiary.ambulanceapp.features.report.history;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.model.report.ReportStatus;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;
import com.incendiary.ambulanceapp.utils.recyclerviews.Paginate;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ReportHistoryPresenter extends BasePresenter<ReportHistoryView> {

    private Map<ReportStatus, PageHelper> pageHelperMap = new HashMap<>();

    private final Provider<PageHelper> pageHelperProvider;

    @Inject
    public ReportHistoryPresenter(Provider<PageHelper> pageHelperProvider) {
        this.pageHelperProvider = pageHelperProvider;
    }

    public void loadReportHistories(final ReportStatus reportStatus, final boolean isRefresh) {
        final PageHelper pageHelper = getPageHelper(reportStatus);
        if (isRefresh) {
            pageHelper.reset();
        }
        pageHelper.hold();

        api.getHistoryReports(pageHelper.getCurrentPage(), getStatusForReportStatus(reportStatus))
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView(), getLoadingType(reportStatus)))
                .subscribe(reports -> {
                    getView().showReportHistories(reports, reportStatus, pageHelper);

                    pageHelper.nextPage();
                    pageHelper.setLastPage(reports.size() >= pageHelper.getItemPerPage());

                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    private int getLoadingType(ReportStatus reportStatus) {
        switch (reportStatus) {
            case COMPLETE:
                return ReportHistoryView.LOADING_COMPLETE;
            case PROCESSING:
                return ReportHistoryView.LOADING_PROCESS;
            default:
                return ReportHistoryView.LOADING_WAIT;
        }
    }

    private String getStatusForReportStatus(ReportStatus status) {
        switch (status) {
            case COMPLETE:
                return "C";
            case WAITING:
                return "W";
            default:
                return "P";
        }
    }

    private PageHelper getPageHelper(ReportStatus reportStatus) {
        PageHelper pageHelper = pageHelperMap.get(reportStatus);
        if (pageHelper == null) {
            pageHelper = pageHelperProvider.get();
            pageHelperMap.put(reportStatus, pageHelper);
        }
        return pageHelper;
    }

    Paginate.Callback makePaginateCallback(ReportStatus reportStatus) {
        return new Paginate.Callback() {
            @Override
            public void onLoadMore() {
                loadReportHistories(reportStatus, false);
            }

            @Override
            public boolean isLoading() {
                return getPageHelper(reportStatus).isOnHold();
            }

            @Override
            public boolean hasLoadedAllItems() {
                return getPageHelper(reportStatus).isOnLastPage();
            }
        };
    }
}
