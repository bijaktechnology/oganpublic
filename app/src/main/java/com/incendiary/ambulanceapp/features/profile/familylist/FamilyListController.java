package com.incendiary.ambulanceapp.features.profile.familylist;

import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.esafirm.emvipi.view.LoadingType;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.common.dialog.ProgressManager;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.features.profile.addfamily.AddFamilyController;
import com.incendiary.ambulanceapp.utils.conductor.Routes;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyListController extends AbsController implements FamilyListView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.family_list_swipe_refresh) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.family_list_recycler) RecyclerView recyclerView;

    @Inject FamilyListPresenter presenter;
    @Inject FamilyListAdapter adapter;
    @Inject ProgressManager progressManager;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_family_list;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        toolbar.setTitle(R.string.profile_family_list);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnEdit(pos -> goToAddFamily(adapter.getItem(pos)));
        adapter.setOnDelete(position -> presenter.deleteFamily(adapter.getItem(position)));

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this::loadFamilies);
        swipeRefreshLayout.post(this::loadFamilies);
    }

    private void loadFamilies() {
        presenter.loadFamilies();
    }

    private void goToAddFamily(@Nullable Family family) {
        getRouter().pushController(Routes.simpleTransaction(
                family != null
                        ? new AddFamilyController(family)
                        : new AddFamilyController(),
                new HorizontalChangeHandler()
        ));
    }

    /* --------------------------------------------------- */
    /* > OnClicks */
    /* --------------------------------------------------- */

    @OnClick(R.id.family_list_btn_add)
    void onAddClick() {
        goToAddFamily(null);
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showDeleteSuccess(Family family) {
        adapter.remove(family);
        Toasts.show("Anggota Keluarga Behasil Terhapus");
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isShow, int i) {
        if (i == LoadingType.REFRESH || i == LoadingType.ANY) {
            swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(isShow));
        } else {
            progressManager.show(isShow);
        }
    }

    @Override
    public void showContent(List<Family> families) {
        adapter.pushData(families);
        adapter.notifyDataSetChanged();
    }
}
