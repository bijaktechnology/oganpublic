package com.incendiary.ambulanceapp.features.report.history;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.data.model.report.ReportStatus;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailActivity;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailProps;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;
import com.incendiary.ambulanceapp.utils.recyclerviews.Paginate;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.Animates;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ReportHistoryController extends AbsController implements ReportHistoryView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.report_history_rv_wait) RecyclerView rvWait;
    @BindView(R.id.report_history_rv_process) RecyclerView rvProcess;
    @BindView(R.id.report_history_rv_done) RecyclerView rvDone;

    /* ProgressView */
    @BindView(R.id.report_history_progress_done) View progressDone;
    @BindView(R.id.report_history_progress_process) View progressProcess;
    @BindView(R.id.report_history_progress_wait) View progressWait;

    /* ErrorView */
    @BindView(R.id.report_history_txt_no_data_done) View noDataDone;
    @BindView(R.id.report_history_txt_no_data_process) View noDataProcess;
    @BindView(R.id.report_history_txt_no_data_wait) View noDataWait;

    @Inject ReportHistoryPresenter presenter;
    @Inject ReportHistoryListAdapter adapterWait;
    @Inject ReportHistoryListAdapter adapterProcess;
    @Inject ReportHistoryListAdapter adapterDone;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_history;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.report_history_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        setupRecyclerView(ReportStatus.WAITING, rvWait, adapterWait);
        setupRecyclerView(ReportStatus.PROCESSING, rvProcess, adapterProcess);
        setupRecyclerView(ReportStatus.COMPLETE, rvDone, adapterDone);

        loadAll();
    }

    private void loadAll() {
        presenter.loadReportHistories(ReportStatus.WAITING, true);
        presenter.loadReportHistories(ReportStatus.PROCESSING, true);
        presenter.loadReportHistories(ReportStatus.COMPLETE, true);
    }

    private void setupRecyclerView(ReportStatus status, RecyclerView recyclerView, ReportHistoryListAdapter adapter) {
        RecyclerViewAttacher.with(recyclerView)
                .layoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false))
                .adapter(adapter)
                .attach();

        Paginate.attach(recyclerView)
                .withCallback(presenter.makePaginateCallback(status))
                .install();

        adapter.setOnItemClickListener(position ->
                ReportDetailActivity.start(getActivity(), new ReportDetailProps(adapter.getItem(position))));

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showReportHistories(List<Report> reports, ReportStatus reportStatus, PageHelper pageHelper) {
        final ReportHistoryListAdapter adapter = getAdapterForStatus(reportStatus);
        adapter.pushData(reports, pageHelper.isFirstPage());
        adapter.notifyDataSetChanged();

        getEmptyView(reportStatus).setVisibility(adapter.isEmpty()
                ? View.VISIBLE
                : View.GONE);
    }

    private ReportHistoryListAdapter getAdapterForStatus(ReportStatus status) {
        switch (status) {
            case WAITING:
                return adapterWait;
            case PROCESSING:
                return adapterProcess;
            default:
                return adapterDone;
        }
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isShow, int loadingType) {
        Animates.visibility(getProgressView(loadingType), isShow);
    }

    private View getEmptyView(ReportStatus status) {
        switch (status) {
            case COMPLETE:
                return noDataDone;
            case PROCESSING:
                return noDataProcess;
            default:
                return noDataWait;
        }
    }

    private View getProgressView(int loadingType) {
        switch (loadingType) {
            case ReportHistoryView.LOADING_COMPLETE:
                return progressDone;
            case ReportHistoryView.LOADING_PROCESS:
                return progressProcess;
            default:
                return progressWait;
        }
    }
}
