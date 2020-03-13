package com.incendiary.ambulanceapp.features.comments.mycomment;

import com.incendiary.ambulanceapp.common.presenter.BasePresenter;
import com.incendiary.ambulanceapp.features.errors.ErrorHandler;
import com.incendiary.ambulanceapp.utils.rx.Transformers;

import javax.inject.Inject;

public class MyCommentPresenter extends BasePresenter<MyCommentView> {

    @Inject
    public MyCommentPresenter() {
    }

    public void loadMyComments() {
        api.getMyComments()
                .compose(Transformers.applyApiCall())
                .compose(Transformers.notifyProgress(getView()))
                .subscribe(comments -> {
                    getView().showComments(comments);
                }, throwable -> ErrorHandler.handleError(throwable, getView()));
    }
}
