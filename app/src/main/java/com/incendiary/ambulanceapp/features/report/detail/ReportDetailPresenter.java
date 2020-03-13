package com.incendiary.ambulanceapp.features.report.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.data.model.common.ApiResponse;
import com.incendiary.ambulanceapp.data.model.report.Report;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.inject.Inject;

import rx.Observable;

public class ReportDetailPresenter extends BasePresenter<ReportDetailView> {

    @Inject
    public ReportDetailPresenter() {
    }

    public void saveImage(Context context, Report report) {
        showLoading(true);

        Context appContext = context.getApplicationContext();

        Glide.with(appContext)
                .load(report.getFotoLaporan())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        showLoading(false);
                        ErrorHandler.handleError(e, getView());
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        File dir = Environment.getExternalStorageDirectory();
                        File oganDir = new File(dir, "ogan/");

                        if (!oganDir.exists()) {
                            oganDir.mkdirs();
                        }
                        File file = new File(oganDir, System.currentTimeMillis() + ".jpg");

                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (FileNotFoundException e) {
                            showLoading(false);
                            ErrorHandler.handleError(e, getView());
                            return;
                        }

                        try {
                            MediaStore.Images.Media.insertImage(
                                    context.getContentResolver(),
                                    file.getPath(),
                                    file.getName(),
                                    report.getJudul()
                            );
                        } catch (FileNotFoundException e) {
                            ErrorHandler.handleError(e);
                        }

                        showLoading(false);
                        getView().showSaveImageSuccess();
                    }
                });
    }

    public void loadReport(ReportDetailProps props) {
        if (props.haveFullReport()) {
            getView().showContent(props.getReport());
        } else {
            loadReportDetail(props.getReport().getLaporanId());
        }
    }

    private void loadReportDetail(String reportId) {
        api.getReportById(reportId)
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(report -> {
                    getView().showContent(report);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void loadComments(String reportId) {
        showLoading(true, ReportDetailView.COMMENT_LOADING);

        api.getComments(reportId)
                .compose(Transformers.applyApiCall())
                .doAfterTerminate(() -> showLoading(false, ReportDetailView.COMMENT_LOADING))
                .subscribe(comments -> {
                    getView().showComments(comments);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void likeReport(final boolean isLike, final String reportId) {
        getLikeObservable(isLike, reportId)
                .compose(Transformers.applyApiCallBase())
                .subscribe(apiResponse -> {
                    getView().showLikeUpdate(isLike, apiResponse.getMessage());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    private Observable<ApiResponse> getLikeObservable(boolean isLike, String reportId) {
        return isLike
                ? api.likeReport(reportId)
                : api.unlikeReport(reportId);
    }

    public void bookmarkReport(final boolean isBookmark, String reportId) {
        getBookmarkObservable(isBookmark, reportId)
                .compose(Transformers.applyApiCallBase())
                .subscribe(res -> {
                    getView().showBookmarkUpdate(isBookmark, res.getMessage());
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    private Observable<ApiResponse> getBookmarkObservable(boolean isBookmark, String reportId) {
        return isBookmark
                ? api.bookmarkReport(reportId)
                : api.unbookmarkReport(reportId);
    }

    public void insertComment(String reportId, String comment) {
        DataStore.getProfile()
                .switchMap(profile -> api.postComment(profile.getUserId(), DataStore.getKeyCode(), reportId, comment))
                .compose(Transformers.applyApiCallBase())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(apiResponse -> {
                    getView().showCommentSubmitSuccess();
                    loadComments(reportId);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
