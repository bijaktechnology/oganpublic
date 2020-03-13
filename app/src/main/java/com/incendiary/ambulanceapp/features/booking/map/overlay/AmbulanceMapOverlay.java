package com.incendiary.ambulanceapp.features.booking.map.overlay;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.Ambulance;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;

public class AmbulanceMapOverlay extends AbsMapOverlay {

    @BindView(R.id.txtDriverName) TextView txtDriverName;
    @BindView(R.id.txtPlat) TextView txtPlat;
    @BindView(R.id.txtPhone) TextView txtPhone;

    @Override
    public boolean isForType(MapMarker mapMarker) {
        return mapMarker.getMarkerType() == MarkerType.AMBULANCE;
    }

    @Override
    int getLayoutResId() {
        return R.layout.view_ambulance_marker_info;
    }

    @Override
    void bindView(View view, MapMarker mapMarker) {
        ButterKnife.bind(this, view);
        Ambulance ambulance = (Ambulance) mapMarker;
        txtPlat.setText(ambulance.getNoPlat());
    }

    @OnClick(R.id.btnClear)
    public void onMarkerCloseClick() {
        hideOverlay();
    }
}
