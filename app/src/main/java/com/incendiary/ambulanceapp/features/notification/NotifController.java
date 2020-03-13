package com.incendiary.ambulanceapp.features.notification;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.fcm.NotificationTypes;
import com.incendiary.ambulanceapp.data.model.notification.SemarNotification;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailActivity;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailProps;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NotifController extends AbsController implements NotifView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.txt_no_data) TextView txtNoData;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeContainer;

    @Inject NotifAdapter adapter;
    @Inject NotifPresenter presenter;

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
        toolbar.setTitle(R.string.notif);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
        bindPresenter(this, presenter);

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(position -> {
            final SemarNotification notification = adapter.getItem(position);
            final ReportDetailProps props = new ReportDetailProps(adapter.getItem(position).getLaporanId());

            if (notification.getNotificationType() == NotificationTypes.COMMENT) {
                startActivity(ReportDetailActivity.getCommentIntent(getActivity(), props));
            } else {
                ReportDetailActivity.start(getActivity(), props);
            }
        });

        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(() -> presenter.loadNotif());
        swipeContainer.post(() -> presenter.loadNotif());
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showContent(List<SemarNotification> notifications) {
        adapter.pushData(notifications);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int i) {
        swipeContainer.post(() -> swipeContainer.setRefreshing(isLoading));
    }
}
