package com.incendiary.ambulanceapp.features.tours.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;
import com.incendiary.ambulanceapp.features.tours.TourDetailController;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.ambulanceapp.utils.rx.RxEditText;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.Animates;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TourSearchController extends AbsController implements TourSearchView {

    @BindView(R.id.tour_search_inp_search) EditText inpSearch;
    @BindView(R.id.tour_search_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.tour_search_progress) View progress;

    @Inject TourSearchPresenter presenter;
    @Inject TourSearchAdapter adapter;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_tour_search;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(position -> {
            getRouter().pushController(Routes.simpleTransaction(
                    new TourDetailController(adapter.getItem(position)),
                    new HorizontalChangeHandler()
            ));
        });

        RxEditText.bindDebounce(inpSearch)
                .forEach(s -> presenter.searchPlace(s));
    }

    @OnClick(R.id.tour_search_btn_back)
    void onBackClick() {
        popCurrentController();
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showResult(List<TourPlace> places) {
        adapter.pushData(places);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isVisible, int i) {
        Animates.visibility(progress, isVisible);
    }
}
