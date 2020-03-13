package com.incendiary.ambulanceapp.features.report.bookmark;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailActivity;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailProps;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class BookmarkListController extends AbsController implements BookmarkListView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.txt_no_data) TextView txtNoData;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeContainer;

    @Inject BookmarkListAdapter adapter;
    @Inject BookmarkListPresenter presenter;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_list;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(R.string.bookmark);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
        bindPresenter(this, presenter);

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(pos ->
                ReportDetailActivity.start(getActivity(), new ReportDetailProps(adapter.getItem(pos))));

        swipeContainer.post(() -> {
            adapter.notifyDataSetChanged();
        });

        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(() -> presenter.loadBookmarkReports(true));
        swipeContainer.post(() -> presenter.loadBookmarkReports(true));
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int i) {
        swipeContainer.post(() -> swipeContainer.setRefreshing(isLoading));
    }

    @Override
    public void showContent(List<Report> reports, PageHelper pageHelper) {
        adapter.pushData(reports, pageHelper.isFirstPage());
        adapter.notifyDataSetChanged();
    }
}
