package com.incendiary.ambulanceapp.features.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.esafirm.imagepicker.features.camera.DefaultCameraModule;
import com.esafirm.imagepicker.model.Image;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.features.comments.reportcomment.ReportCommentController;
import com.incendiary.ambulanceapp.features.notification.NotifController;
import com.incendiary.ambulanceapp.features.report.etc.ReportConfirmationListener;
import com.incendiary.ambulanceapp.features.report.etc.ReportImageConfirmationController;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailActivity;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailProps;
import com.incendiary.ambulanceapp.features.report.reportfeedback.ReportFeedbackController;
import com.incendiary.ambulanceapp.utils.SwipeRefreshUtils;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.recyclerviews.PageHelper;
import com.incendiary.ambulanceapp.utils.recyclerviews.Paginate;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.etc.Values;
import com.incendiary.androidcommon.utils.Preconditions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportListController extends AbsController implements ReportListView, ReportConfirmationListener {

    private static final int RC_CAMERA = 0x11;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.report_recycler_view_top) RecyclerView recyclerViewTop;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.txt_no_data) TextView txtNoData;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.report_list_child_container) ViewGroup childContainer;

    @Inject ReportPopularListAdapter adapterTop;
    @Inject ReportListAdapter adapter;
    @Inject ReportListPresenter presenter;
    @Inject DefaultCameraModule cameraModule;

    @Override
    protected void onInit() {
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.citizen_report);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finishActivity());
        toolbar.inflateMenu(R.menu.menu_report);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_notif:
                    goToNotificationPage();
                    break;
                case R.id.menu_search:
                    break;
            }
            return true;
        });

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewTop, false);

        LinearLayoutManager horizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalLayout.setAutoMeasureEnabled(true);

        RecyclerViewAttacher.with(recyclerViewTop)
                .adapter(adapterTop)
                .layoutManager(horizontalLayout)
                .attach();

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        Paginate.attach(recyclerView)
                .withCallback(presenter)
                .install();

        adapterTop.setOnItemClickListener(position -> goToReportDetail(adapterTop.getItem(position)));
        adapter.setOnItemClickListener(position -> goToReportDetail(adapter.getItem(position)));
        adapter.setOnCommentClick(this::goToComment);
        adapter.setOnStatusClick(this::goToReportFeedback);
        adapter.setOnLikeClick(report -> presenter.likeReport(report));

        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(this::refresh);
        swipeContainer.post(this::refresh);
    }

    private void goToReportFeedback(Report report) {
        getChildRouter(childContainer).setPopsLastView(true)
                .setRoot(Routes.simpleTransaction(
                        new ReportFeedbackController(report),
                        new FadeChangeHandler()
                ));
    }

    private void goToComment(Report report) {
        getRouter().pushController(Routes.simpleTransaction(
                new ReportCommentController(report.getLaporanId()),
                new VerticalChangeHandler()
        ));
    }

    private void goToReportDetail(Report report) {
        ReportDetailActivity.start(getActivity(), new ReportDetailProps(report));
    }

    private void goToNotificationPage() {
        getRouter().pushController(Routes.simpleTransaction(
                new NotifController(),
                new VerticalChangeHandler()
        ));
    }

    private void refresh() {
        presenter.loadReport(true);
        presenter.loadPopularReports();
    }

    /* --------------------------------------------------- */
    /* > Camera */
    /* --------------------------------------------------- */

    @OnClick(R.id.report_list_fab)
    public void onViewClicked() {
        startCamera();
    }

    private void startCamera() {
        Activity activity = getActivity();
        Preconditions.checkNotNull(activity);
        startActivityForResult(cameraModule.getCameraIntent(activity), RC_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == RC_CAMERA) {
            cameraModule.getImage(getActivity(), data, list -> {
                Image image = Values.first(list);
                if (image != null) {
                    new Handler(Looper.getMainLooper()).post(() -> goToReportConfirm(image));
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void goToReportConfirm(Image image) {
        getRouter().pushController(Routes.simpleTransaction(
                new ReportImageConfirmationController(image, this),
                new VerticalChangeHandler()
        ));
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showLikeUpdate(Report report, boolean isLike) {
        adapter.updateLikeStatus(report, isLike);
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int i) {
        SwipeRefreshUtils.showLoading(swipeContainer, isLoading);
    }

    @Override
    public void showPopularReports(List<Report> reports) {
        adapterTop.pushData(reports);
        adapterTop.notifyDataSetChanged();
    }

    @Override
    public void showReports(List<Report> reports, PageHelper pageHelper) {
        adapter.pushData(reports, pageHelper.isFirstPage());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancel() {
        startCamera();
    }
}
