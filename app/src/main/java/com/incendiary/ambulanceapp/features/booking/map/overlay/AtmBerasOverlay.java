package com.incendiary.ambulanceapp.features.booking.map.overlay;

import android.view.View;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.poi.Poi;
import com.incendiary.ambulanceapp.data.model.poi.PoiCategories;
import com.incendiary.androidcommon.android.Toasts;
import com.incendiary.androidcommon.android.text.Strings;
import com.incendiary.androidcommon.android.views.Animates;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AtmBerasOverlay extends AbsMapOverlay {

    @BindView(R.id.atm_beras_txt_address) TextView txtAddress;
    @BindView(R.id.atm_beras_txt_percentage) TextView txtStockPercentage;
    @BindView(R.id.atm_beras_txt_total_stock) TextView txtTotalStock;
    @BindView(R.id.atm_beras_txt_unit_code) TextView txtUnitCode;
    @BindView(R.id.atm_beras_progress) View progressView;

    @Override
    public boolean isForType(MapMarker mapMarker) {
        if (mapMarker instanceof Poi) {
            Poi poi = (Poi) mapMarker;
            return poi.getKategoriId().equalsIgnoreCase(PoiCategories.ATM_BERAS);
        }
        return false;
    }

    @Override
    int getLayoutResId() {
        return R.layout.view_marker_overlay_poi_atm_beras;
    }

    @Override
    void bindView(View view, MapMarker mapMarker) {
        ButterKnife.bind(this, view);

        final Poi poi = (Poi) mapMarker;
        final AtmBerasOverlayPresenter presenter = new AtmBerasOverlayPresenter();

        txtAddress.setText(poi.getAlamat());

        presenter.loadAtmBerasInfo(poi, info -> {

            txtUnitCode.setText(Strings.format("%s", info.getUnitId()));
            txtTotalStock.setText(Strings.format("%s kg", info.getStock()));
            txtStockPercentage.setText(Strings.format("%d %%", info.getPctStock()));

            Animates.visibility(progressView, false);
        }, throwable -> {
            Toasts.show(throwable.getMessage());
            Animates.visibility(progressView, false);
        });
    }

    @OnClick(R.id.atm_beras_btn_close)
    void onCloseClick(){
        hideOverlay();
    }
}
