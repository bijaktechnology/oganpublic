package com.incendiary.ambulanceapp.features.tours.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.events.EventLocationReady;
import com.incendiary.ambulanceapp.data.events.EventLocationResolveRequest;
import com.incendiary.ambulanceapp.data.model.exceptions.LocationResolutionException;
import com.incendiary.ambulanceapp.data.model.tours.TourPlace;
import com.incendiary.ambulanceapp.data.model.tours.TourPlaceCategory;
import com.incendiary.ambulanceapp.features.tours.TourDetailController;
import com.incendiary.ambulanceapp.utils.BundleBuilder;
import com.incendiary.ambulanceapp.utils.EBus;
import com.incendiary.ambulanceapp.utils.SwipeRefreshUtils;
import com.incendiary.ambulanceapp.utils.conductor.Controllers;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.utils.Preconditions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TourListController extends AbsController implements TourListView {

    private static final String ARG_CATEGORY = "TourList.Category";

    @BindView(R.id.tours_list_swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tours_list_recyclerview) RecyclerView recyclerView;

    @Inject TourListAdapter adapter;
    @Inject TourListPresenter presenter;

    public TourListController(TourPlaceCategory category) {
        this(new BundleBuilder()
                .putSerializable(ARG_CATEGORY, category)
                .build());
    }

    public TourListController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controllers_tours_list;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);
        Controllers.bindEventBus(this);

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(position -> {
            Controller controller = getParentController();
            assert controller != null;

            controller.getRouter()
                    .pushController(Routes.simpleTransaction(
                            new TourDetailController(adapter.getItem(position)),
                            new HorizontalChangeHandler()
                    ));
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this::loadData);
        swipeRefreshLayout.post(this::loadData);
    }

    private void loadData() {
        TourPlaceCategory category = (TourPlaceCategory) getArgs().getSerializable(ARG_CATEGORY);
        Preconditions.checkNotNull(category);
        presenter.loadTour(category);
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationReady(EventLocationReady locationReady) {
        loadData();
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof LocationResolutionException) {
            LocationResolutionException exception = (LocationResolutionException) throwable;
            EBus.post(new EventLocationResolveRequest(exception.getStatus()));
        }

        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isLoading, int i) {
        SwipeRefreshUtils.showLoading(swipeRefreshLayout, isLoading);
    }

    @Override
    public void showContent(List<TourPlace> tours) {
        adapter.pushData(tours);
        adapter.notifyDataSetChanged();
    }
}
