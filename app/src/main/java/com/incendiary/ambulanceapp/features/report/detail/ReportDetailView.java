package com.incendiary.ambulanceapp.features.report.detail;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.comment.Comment;
import com.incendiary.ambulanceapp.data.model.report.Report;

import java.util.List;

public interface ReportDetailView extends MvpLoadingView, MvpErrorView {

    int COMMENT_LOADING = 0x123;

    void showContent(Report report);
    void showLikeUpdate(boolean isLike, String message);
    void showBookmarkUpdate(boolean isBookmark, String message);
    void showComments(List<Comment> comments);
    void showCommentSubmitSuccess();
    void showSaveImageSuccess();
}
