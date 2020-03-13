package com.incendiary.ambulanceapp.features.comments.reportcomment;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.comment.Comment;

import java.util.List;

public interface ReportCommentView extends MvpLoadingView, MvpErrorView {
    void showComments(List<Comment> comments);
    void showCommentSuccess();
}
