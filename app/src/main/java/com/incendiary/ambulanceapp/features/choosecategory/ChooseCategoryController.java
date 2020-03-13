package com.incendiary.ambulanceapp.features.choosecategory;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.report.ReportCategory;
import com.incendiary.ambulanceapp.features.report.input.ReportInputController;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ChooseCategoryController extends AbsController implements ChooseCategoryView {

    private static final int SPAN_COUNT = 3;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.txt_no_data) TextView txtNoData;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeContainer;

    @Inject ChooseCategoryAdapter adapter;
    @Inject ChooseCategoryPresenter presenter;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_choose_category;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.choose_category);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finishActivity());

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .layoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT))
                .attach();

        adapter.setOnItemClickListener(position -> goToReportInput(adapter.getItem(position)));

        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(() -> presenter.loadReportCategories());
        swipeContainer.post(() -> presenter.loadReportCategories());
    }

    private void goToReportInput(ReportCategory reportCategory) {
        getRouter().pushController(Routes.simpleTransaction(
                new ReportInputController(reportCategory),
                new HorizontalChangeHandler()
        ));
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int i) {
        swipeContainer.post(() -> swipeContainer.setRefreshing(isLoading));
    }

    @Override
    public void showContent(List<ReportCategory> categories) {
        adapter.pushData(categories);
        adapter.notifyDataSetChanged();
    }
}
