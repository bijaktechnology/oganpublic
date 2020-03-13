package com.incendiary.ambulanceapp.features.profile.medicalhistory;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.common.controllers.AbsController;
import com.incendiary.ambulanceapp.data.model.RekamMedis;
import com.incendiary.ambulanceapp.utils.recyclerviews.RecyclerViewAttacher;
import com.incendiary.androidcommon.android.Toasts;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MedicalHistoryController extends AbsController implements MedicalHistoryView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.txt_no_data) TextView txtNoData;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeContainer;

    @Inject MedicalHistoryPresenter presenter;
    @Inject MedicalHistoryAdapter adapter;

    @Override
    protected void onSetupComponent() {
        getComponent().inject(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.controller_list;
    }

    @Override
    protected void onViewBound(View view) {
        toolbar.setTitle(R.string.rekam_medis);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> popCurrentController());

        bindPresenter(this, presenter);

        RecyclerViewAttacher.with(recyclerView)
                .adapter(adapter)
                .attach();

        adapter.setOnItemClickListener(this::showDetailDialog);

        swipeContainer.setOnRefreshListener(() -> presenter.loadRekamMedis());
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.post(() -> presenter.loadRekamMedis());
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

    private void showDetailDialog(int position) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rekam_medis);
        dialog.setCancelable(true);

        TextView txtJenisPenyakit = (TextView) dialog.findViewById(R.id.txtJenisPenyakit);
        TextView txtNameRs = (TextView) dialog.findViewById(R.id.txtNameRs);
        TextView txtDate = (TextView) dialog.findViewById(R.id.txtDate);
        TextView txtTindakan = (TextView) dialog.findViewById(R.id.txtTindakan);
        TextView txtNameDoctor = (TextView) dialog.findViewById(R.id.txtNameDoctor);

        RekamMedis rekamMedis = adapter.getItem(position);
        txtJenisPenyakit.setText(rekamMedis.getDiagnosa());
        txtNameRs.setText(rekamMedis.getRsPuskesmas());
        txtDate.setText(rekamMedis.getTanggal());
        txtTindakan.setText(rekamMedis.getTindakan());
        txtNameDoctor.setText(rekamMedis.getNamaDokter());

        dialog.show();
    }

    @Override
    public void showMedicalHistories(List<RekamMedis> rekamMedisList) {
        adapter.pushData(rekamMedisList);
        adapter.notifyDataSetChanged();

        if (adapter.isEmpty()) {
            showNoData(true);
        }
    }

    private void showNoData(boolean isShow) {
        txtNoData.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
