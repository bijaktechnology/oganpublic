package com.incendiary.ambulanceapp.features.report.detail;

import android.content.Context;
import android.content.Intent;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.incendiary.ambulanceapp.common.activity.ControllerActivity;
import com.incendiary.ambulanceapp.features.comments.reportcomment.ReportCommentController;
import com.incendiary.androidcommon.utils.Preconditions;

import java.util.Arrays;

import rx.functions.Action1;

public class ReportDetailActivity extends ControllerActivity {

    private static final String ARG_COMMENT_STACK = "ReportDetail.CommentStack";

    @Override
    protected Controller getController() {
        return null;
    }

    @Override
    protected Action1<Router> getRouterHandler() {
        return router -> {
            if (!router.hasRootController()) {
                final boolean isCommentStack = getIntent().hasExtra(ARG_COMMENT_STACK);
                final ReportDetailProps props = getIntent().getExtras().getParcelable(ReportDetailExtras.ARG_PROPS);
                Preconditions.checkNotNull(props);

                if (isCommentStack) {
                    router.setBackstack(Arrays.asList(
                            RouterTransaction.with(new ReportDetailController(props)),
                            RouterTransaction.with(new ReportCommentController(props.getReport().getLaporanId()))
                    ), null);
                } else {
                    router.setRoot(RouterTransaction.with(new ReportDetailController(props)));
                }
            }
        };
    }

    /* --------------------------------------------------- */
    /* > Stater */
    /* --------------------------------------------------- */

    public static Intent getCommentIntent(Context context, ReportDetailProps props) {
        return new Intent(context, ReportDetailActivity.class)
                .putExtra(ReportDetailActivity.ARG_COMMENT_STACK, true)
                .putExtra(ReportDetailExtras.ARG_PROPS, props);
    }

    public static Intent getIntent(Context context, ReportDetailProps props) {
        return new Intent(context, ReportDetailActivity.class)
                .putExtra(ReportDetailExtras.ARG_PROPS, props);
    }

    public static void start(Context context, ReportDetailProps props) {
        context.startActivity(getIntent(context, props));
    }
}
