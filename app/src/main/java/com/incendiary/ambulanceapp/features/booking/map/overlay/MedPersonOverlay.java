package com.incendiary.ambulanceapp.features.booking.map.overlay;

import android.view.View;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.drupadi.Bidan;
import com.incendiary.ambulanceapp.data.model.drupadi.Doctor;
import com.incendiary.ambulanceapp.data.model.drupadi.MedPersonExtension;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedPersonOverlay extends AbsMapOverlay {

    @BindView(R.id.txtLabel) TextView txtLabel;
    @BindView(R.id.txtName) TextView txtName;

    @BindView(R.id.poi_txt_status) TextView txtStatus;
    @BindView(R.id.poi_txt_status_label) TextView txtStatusLabel;
    @BindView(R.id.poi_txt_puskesmas) TextView txtPuskesmas;

    @Override
    public boolean isForType(MapMarker mapMarker) {
        return mapMarker instanceof Doctor || mapMarker instanceof Bidan;
    }

    @Override
    int getLayoutResId() {
        return R.layout.view_marker_overlay_doctor;
    }

    @Override
    void bindView(View view, MapMarker mapMarker) {
        ButterKnife.bind(this, view);

        txtName.setText(mapMarker.getTitle());
        txtLabel.setText(mapMarker instanceof Doctor ?
                R.string.doctor_name
                : R.string.bidan_name);

        txtStatusLabel.setText(mapMarker instanceof Doctor
                ? R.string.poi_overlay_status_doctor
                : R.string.poi_overlay_status_bidan);

        if (mapMarker instanceof Doctor) {
            Doctor doctor = (Doctor) mapMarker;
            txtStatus.setText(MedPersonExtension.getActualStatus(doctor));
            txtPuskesmas.setText(doctor.getBaseLocation());
        }

        if (mapMarker instanceof Bidan) {
            Bidan bidan = (Bidan) mapMarker;
            txtStatus.setText(MedPersonExtension.getActualStatus(bidan));
            txtPuskesmas.setText(bidan.getBaseLocation());
        }
    }

    @OnClick(R.id.btnClear)
    public void onMarkerCloseClick() {
        hideOverlay();
    }
}
