package com.incendiary.ambulanceapp.features.comments.reportcomment;

import com.esafirm.emvipi.view.LoadingType;
import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.data.DataStore;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class ReportCommentPresenter extends BasePresenter<ReportCommentView> {

    @Inject
    public ReportCommentPresenter() {
    }

    public void loadComments(String reportId) {
        showLoading(true, LoadingType.REFRESH);

        api.getComments(reportId)
                .compose(Transformers.applyApiCall())
                .doAfterTerminate(() -> showLoading(false, LoadingType.REFRESH))
                .subscribe(comments -> {
                    getView().showComments(comments);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }

    public void insertComment(String reportId, String comment) {
        DataStore.getProfile()
                .switchMap(profile -> api.postComment(profile.getUserId(), DataStore.getKeyCode(), reportId, comment))
                .compose(Transformers.applyApiCallBase())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(apiResponse -> {
                    getView().showCommentSuccess();
                    loadComments(reportId);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
