package com.incendiary.ambulanceapp.features.booking.patientdata.familypicker;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.family.Family;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.views.Animates;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FamilyPickerController extends AbsController implements FamilyPickerView {

    @BindView(R.id.family_picker_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.family_picker_progress) View progresBar;

    @Inject FamilyPickerPresenter presenter;
    @Inject FamilyPickerAdapter adapter;

    public <T extends Controller & FamilyPickerListener> FamilyPickerController(T controller) {
        setTargetController(controller);
    }

    public FamilyPickerController(Bundle args) {
        super(args);
    }

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_family_picker;
    }

    @Override
    protected void onViewBound(View view) {
        bindPresenter(this, presenter);

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(position -> {
            final FamilyPickerListener listener = (FamilyPickerListener) getTargetController();
            if (listener != null) {
                listener.onFamilyPicker(adapter.getItem(position));
            }
            popCurrentController();
        });

        presenter.loadFamilies();
    }

    /* --------------------------------------------------- */
    /* > View Methods */
    /* --------------------------------------------------- */

    @Override
    public void showContents(List<Family> families) {
        adapter.pushData(families);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable) {
        Toasts.show(throwable.getMessage());
    }

    @Override
    public void showLoading(boolean isVisible, int i) {
        Animates.visibility(progresBar, isVisible);
    }
}
