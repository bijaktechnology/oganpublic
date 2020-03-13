package com.incendiary.ambulanceapp.features.report.bookmark;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;
import com.incendiary.ambulanceapp.utils.recyclerviews.Paginate;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class BookmarkListPresenter extends BasePresenter<BookmarkListView> implements Paginate.Callback {

    private final PageHelper pageHelper;

    @Inject
    public BookmarkListPresenter(PageHelper pageHelper) {
        this.pageHelper = pageHelper;
    }

    public void loadBookmarkReports(boolean isRefresh) {
        if (isRefresh) {
            pageHelper.reset();
        }
        pageHelper.hold();

        api.getBookmarkedReports(pageHelper.getCurrentPage())
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(reports -> {

                    getView().showContent(reports, pageHelper);
                    pageHelper.nextPage();
                    pageHelper.setLastPage(reports.size() < pageHelper.getItemPerPage());

                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    /* --------------------------------------------------- */
    /* > Paginate */
    /* --------------------------------------------------- */

    @Override
    public void onLoadMore() {
        loadBookmarkReports(false);
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
