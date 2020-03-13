package com.incendiary.ambulanceapp.features.comments.mycomment;

import com.esafirm.emvipi.view.MvpErrorView;
import com.esafirm.emvipi.view.MvpLoadingView;
import com.incendiary.ambulanceapp.data.model.comment.Comment;

import java.util.List;

public interface MyCommentView extends MvpLoadingView, MvpErrorView {
    void showComments(List<Comment> comments);
}
