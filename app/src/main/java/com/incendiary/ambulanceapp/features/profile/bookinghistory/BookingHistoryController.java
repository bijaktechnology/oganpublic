package com.incendiary.ambulanceapp.features.profile.bookinghistory;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.HistoryBooking;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.etc.Values;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class BookingHistoryController extends AbsController implements HistoryBookingView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.txt_no_data) TextView txtNoData;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeContainer;

    @Inject HistoryBookingPresenter presenter;
    @Inject BookingHistoryAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_list;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.booking_history_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        swipeContainer.setOnRefreshListener(() -> presenter.loadHistory(true));
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.post(() -> presenter.loadHistory(false));
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

  /* --------------------------------------------------- */
  /* > View Methods */
  /* --------------------------------------------------- */

    @Override
    public void showLoading(boolean isLoading, int type) {
        if (isLoading) {
            showNoData(false);
        }
        swipeContainer.setRefreshing(isLoading);
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

  /* --------------------------------------------------- */


    @Override
    public void showHistoryBooking(List<HistoryBooking> historyBookingList) {
        swipeContainer.setRefreshing(false);

        if (Values.isEmpty(historyBookingList)) {
            showNoData(true);
        } else {
            adapter.pushData(historyBookingList);
        }

        adapter.notifyDataSetChanged();
    }

    private void showNoData(boolean isShow) {
        txtNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
