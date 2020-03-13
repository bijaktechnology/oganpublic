package com.incendiary.ambulanceapp.features.report.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.bumptech.glide.Glide;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.comment.Comment;
import com.incendiary.ambulanceapp.data.model.comment.Comments;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.data.model.report.Reports;
import com.incendiary.ambulanceapp.features.comments.reportcomment.ReportCommentController;
import com.incendiary.ambulanceapp.features.map.ReportMapDetailController;
import com.incendiary.ambulanceapp.features.report.reportfeedback.ReportFeedbackController;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.text.Strings;
import com.incendiary.androidcommon.android.views.Animates;
import com.incendiary.androidcommon.utils.Preconditions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class ReportDetailController extends AbsController implements ReportDetailView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.report_detail_txt_desc) TextView txtDesc;
    @BindView(R.id.report_detail_txt_user) TextView txtUserName;
    @BindView(R.id.report_detail_img_user) ImageView imgAvatar;
    @BindView(R.id.report_detail_img_report) ImageView imgContent;
    @BindView(R.id.report_detail_txt_title) TextView txtTitle;

    /* Top */
    @BindView(R.id.report_detail_txt_category) TextView txtCategory;


    @BindView(R.id.report_detail_txt_location) TextView txtLocation;
    @BindView(R.id.report_detail_txt_time) TextView txtTime;
    @BindView(R.id.report_detail_img_category) ImageView imgCategory;
    @BindView(R.id.report_txt_status) TextView txtStatus;
    @BindView(R.id.report_detail_img_bookmark) ImageView btnBookmark;

    /* Buttons */
    @BindView(R.id.report_detail_txt_love) TextView txtLike;
    @BindView(R.id.report_detail_txt_comment) TextView txtComment;
    @BindView(R.id.report_detail_img_map) ImageView btnMap;

    @BindView(R.id.report_detial_progress) View progressBar;
    @BindView(R.id.report_detail_view_comment) LinearLayout commentContainer;
    @BindView(R.id.report_detail_inp_comment) EditText inpComment;
    @BindView(R.id.report_detail_btn_send) View btnSendComment;

    @BindView(R.id.report_detail_child_container) ViewGroup childContainer;

    @Inject ReportDetailPresenter presenter;
    @Inject ProgressManager progressManager;

    public ReportDetailController(ReportDetailProps props) {
        this(new BundleBuilder()
                .putParcelable(ReportDetailExtras.ARG_PROPS, props)
                .build());
    }

    public ReportDetailController(Bundle args) {
        super(args);
    }

    @Override
    protected void onInit() {
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_report_detail;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.report_detail);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finishActivity());

        final ReportDetailProps props = getArgs().getParcelable(ReportDetailExtras.ARG_PROPS);
        Preconditions.checkNotNull(props);

        presenter.loadReport(props);
        presenter.loadComments(props.getReport().getLaporanId());
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showBookmarkUpdate(boolean isBookmark, String message) {
        Toasts.show(message);
        btnBookmark.setTag(isBookmark ? true : null);
        btnBookmark.setImageResource(isBookmark ? R.drawable.ic_bookmarked : R.drawable.ic_bookmark);
    }

    @Override
    public void showLikeUpdate(boolean isLike, String message) {
        Toasts.show(message);
        final int likeCount = Integer.valueOf(txtLike.getText().toString());
        txtLike.setTag(isLike ? true : null);
        txtLike.setText(String.valueOf(isLike ? likeCount + 1 : likeCount - 1));
        txtLike.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                txtLike.getTag() != null ? R.drawable.ic_love_red : R.drawable.ic_love, 0);
    }

    @Override
    public void showContent(Report report) {
        txtUserName.setText(report.getNamaLengkap());
        txtTitle.setText(report.getJudul());
        txtDesc.setText(report.getKeterangan());

        txtLike.setTag(report.isStatusLike() ? true : null);
        btnBookmark.setTag(report.isStatusBookmark() ? true : null);

        txtLike.setText(report.getJumlahLike());
        txtLike.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                txtLike.getTag() != null ? R.drawable.ic_love_red : R.drawable.ic_love, 0);
        txtLike.setOnClickListener(v -> presenter.likeReport(txtLike.getTag() == null, report.getLaporanId()));

        txtComment.setText(report.getJumlahComment());
        txtComment.setOnClickListener(v -> goToCommentPage());

        btnBookmark.setOnClickListener(v -> presenter.bookmarkReport(btnBookmark.getTag() == null, report.getLaporanId()));
        btnMap.setOnClickListener(v -> goToMapDetail(report));

        txtLocation.setText(Reports.getReportLocation(report));
        txtCategory.setText(report.getNamaKategori());
        txtTime.setText(Reports.getRelativeTime(report));

        txtStatus.setText(Reports.getStatusText(report));
        txtStatus.setBackgroundResource(Reports.getStatusBackground(report));
        txtStatus.setOnClickListener(v -> goToReportFeedback(report));

        btnSendComment.setOnClickListener(v -> {
            if (!Strings.isEmpty(inpComment.getText().toString())) {
                presenter.insertComment(report.getLaporanId(), inpComment.getText().toString());
            }
        });

        Glide.with(getActivity())
                .load(report.getIcon())
                .into(imgCategory);

        Glide.with(getActivity())
                .load(report.getAvatar())
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(imgAvatar);

        Glide.with(getActivity())
                .load(report.getFotoLaporan())
                .into(imgContent);

        toolbar.inflateMenu(R.menu.menu_report_detail);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_save_image:
                    presenter.saveImage(getActivity(), report);
                    break;
            }
            return true;
        });
    }

    private void goToMapDetail(Report report) {
        getRouter().pushController(Routes.simpleTransaction(
                new ReportMapDetailController(report),
                new HorizontalChangeHandler()
        ));
    }

    private void goToReportFeedback(Report report) {
        getChildRouter(childContainer).setPopsLastView(true)
                .setRoot(Routes.simpleTransaction(
                        new ReportFeedbackController(report),
                        new FadeChangeHandler()
                ));
    }

    private void goToCommentPage() {
        final ReportDetailProps props = getArgs().getParcelable(ReportDetailExtras.ARG_PROPS);
        Preconditions.checkNotNull(props);

        getRouter().pushController(Routes.simpleTransaction(
                new ReportCommentController(props.getReport().getLaporanId()),
                new VerticalChangeHandler()
        ));
    }


    @Override
    public void showComments(List<Comment> comments) {
        commentContainer.removeAllViews();
        for (int i = 0; i < Math.min(5, comments.size()); i++) {

            final Comment comment = comments.get(i);

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_report_comment, commentContainer, false);
            ImageView imgAvatar = (ImageView) view.findViewById(R.id.item_report_comment_img_user);
            TextView txtName = (TextView) view.findViewById(R.id.item_report_comment_txt_user);
            TextView txtComment = (TextView) view.findViewById(R.id.item_report_comment_txt_comment);
            TextView txtTime = (TextView) view.findViewById(R.id.item_report_comment_txt_time);

            txtName.setText(comment.getNamaLengkap());
            txtComment.setText(comment.getComment());
            txtTime.setText(Comments.getRelativeTime(comment));

            Glide.with(getActivity())
                    .load(comment.getAvatar())
                    .bitmapTransform(new CropCircleTransformation(getActivity()))
                    .into(imgAvatar);

            view.setOnClickListener(v -> goToCommentPage());

            commentContainer.addView(view);
        }
    }

    @Override
    public void showCommentSubmitSuccess() {
        inpComment.getEditableText().clear();
    }

    @Override
    public void showSaveImageSuccess() {
        Toasts.show("Gambar tersimpan");
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int type) {
        if (type == ReportDetailView.COMMENT_LOADING) {
            Animates.visibility(progressBar, isLoading);
        } else {
            progressManager.show(isLoading);
        }
    }
}
