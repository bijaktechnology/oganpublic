package com.incendiary.ambulanceapp.features.comments.reportcomment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.emvipi.view.LoadingType;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.comment.Comment;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.text.Strings;
import com.incendiary.androidcommon.android.views.Animates;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ReportCommentController extends AbsController implements ReportCommentView {

    private static final String ARG_REPORT_ID = "ReportComment.ReportId";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.comment_recycler_view) RecyclerView commentRecyclerView;
    @BindView(R.id.comment_swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.comment_txt_no_data) TextView txtNoData;
    @BindView(R.id.comment_inp) EditText inpComment;

    @Inject ReportCommentAdapter adapter;
    @Inject ReportCommentPresenter presenter;
    @Inject ProgressManager progressManager;

    public ReportCommentController(String reportId) {
        this(new BundleBuilder()
                .putString(ARG_REPORT_ID, reportId)
                .build());
    }

    public ReportCommentController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_comment;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.chat);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        txtNoData.setText("Belum Ada Komentar");

        RecyclerViewAttacher.with(commentRecyclerView)
                .adapter(adapter)
                .attach();

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this::loadData);
        swipeRefresh.post(this::loadData);
    }

    private void loadData() {
        final String reportId = getArgs().getString(ARG_REPORT_ID);
        presenter.loadComments(reportId);
    }

    /* --------------------------------------------------- */
    /* > OnClicks */
    /* --------------------------------------------------- */

    @OnClick(R.id.comment_btn_send)
    void onSendClick() {
        if (Strings.isEmpty(inpComment.getText())) {
            return;
        }

        final String reportId = getArgs().getString(ARG_REPORT_ID);
        presenter.insertComment(reportId, inpComment.getText().toString());
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showComments(List<Comment> comments) {
        adapter.pushData(comments);
        adapter.notifyDataSetChanged();
        invalidateNoData();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
        invalidateNoData();
    }

    @Override
    public void showLoading(boolean refreshing, int loadingType) {
        if (loadingType == LoadingType.REFRESH) {
            swipeRefresh.post(() -> swipeRefresh.setRefreshing(refreshing));
        } else {
            progressManager.show(refreshing);
        }
    }

    @Override
    public void showCommentSuccess() {
        inpComment.getEditableText().clear();
        showSuccessToast(inpComment.getContext());
    }

    @SuppressLint("InflateParams")
    private void showSuccessToast(Context context) {
        ViewGroup toastView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.toast_success, null);
        TextView textView = (TextView) toastView.findViewById(R.id.text);
        textView.setText("Komen berhasil diberikan");
        Toast toast = new Toast(context);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void invalidateNoData() {
        Animates.visibility(txtNoData, adapter.isEmpty());
    }
}
