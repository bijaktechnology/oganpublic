package com.incendiary.ambulanceapp.features.comments.mycomment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.comment.Comment;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailActivity;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailProps;
import com.incendiary.ambulanceapp.utils.SwipeRefreshUtils;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MyCommentController extends AbsController implements MyCommentView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txt_no_data) TextView txtNoData;

    @Inject MyCommentPresenter presenter;
    @Inject MyCommentAdapter adapter;

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
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.comment);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(position -> {
            final Comment comment = adapter.getItem(position);
            ReportDetailActivity.start(getActivity(), new ReportDetailProps(comment.getReportId()));
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadMyComments());
        swipeRefreshLayout.post(() -> presenter.loadMyComments());
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showComments(List<Comment> comments) {
        adapter.pushData(comments);
        adapter.notifyDataSetChanged();
        invalidateNoDataView();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
        invalidateNoDataView();
    }

    private void invalidateNoDataView() {
        txtNoData.setVisibility(adapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showLoading(boolean refreshing, int i) {
        SwipeRefreshUtils.showLoading(swipeRefreshLayout, refreshing);
    }
}
