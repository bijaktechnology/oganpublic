package com.incendiary.ambulanceapp.features.booking.map.overlay;

import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.poi.Poi;
import com.incendiary.ambulanceapp.data.model.poi.PoiCategories;
import com.incendiary.ambulanceapp.data.model.poi.hospital.AvailableRoom;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PoiOverlay extends AbsMapOverlay {

    @BindView(R.id.txtName) TextView txtName;
    @BindView(R.id.poi_overlay_img_icon) ImageView imgIcon;
    @BindView(R.id.txtKecamatan) TextView txtKecamantan;
    @BindView(R.id.txtAddress) TextView txtAddress;
    @BindView(R.id.btnSubmit) View btnSubmit;

    @BindView(R.id.poi_overlay_pager) View pager;
    @BindView(R.id.poi_overlay_btn_information) View btnInformation;
    @BindView(R.id.poi_overlay_btn_rooms) View btnRooms;

    @BindView(R.id.poi_overlay_progressbar) View progressBar;
    @BindView(R.id.poi_overlay_information_container) ViewGroup containerInformation;
    @BindView(R.id.poi_overlay_room_container) ViewGroup containerRoom;

    private PoiOverlayListener listener;
    private PoiOverlayPresenter presenter;

    public PoiOverlay(PoiOverlayListener listener) {
        this.listener = listener;
        this.presenter = new PoiOverlayPresenter();
    }

    @Override
    int getLayoutResId() {
        return R.layout.view_marker_overlay_poi;
    }

    @Override
    void bindView(View view, MapMarker mapMarker) {
        ButterKnife.bind(this, view);

        final Poi poi = (Poi) mapMarker;
//        if (poi.getKategoriId().equalsIgnoreCase(PoiCategories.HOSPITAL)) {
//
//            View.OnClickListener onClickListener = v -> {
//                final boolean isInformation = v == btnInformation;
//                containerInformation.setVisibility(isInformation ? View.VISIBLE : View.GONE);
//                containerRoom.setVisibility(isInformation ? View.GONE : View.VISIBLE);
//
//                btnInformation.setBackgroundResource(isInformation
//                        ? R.drawable.bg_pager_poi_overlay
//                        : R.drawable.bg_pager_poi_overlay_unselected);
//
//                btnRooms.setBackgroundResource(isInformation
//                        ? R.drawable.bg_pager_poi_overlay_unselected
//                        : R.drawable.bg_pager_poi_overlay);
//            };
//
//            btnInformation.setOnClickListener(onClickListener);
//            btnRooms.setOnClickListener(onClickListener);
//
//            presenter.loadAvailableRooms(poi, (isLoading, i) ->
//                    Animates.visibility(progressBar, isLoading), this::showAvailableRoom);
//
//        } else {
        pager.setVisibility(View.GONE);
//        }

        txtName.setText(poi.getNamaLokasi());
        txtAddress.setText(poi.getAlamat());
        imgIcon.setImageResource(poi.getIcon());
        btnSubmit.setOnClickListener(v -> listener.onDirectionClick((Poi) mapMarker));
    }

    private void showAvailableRoom(AvailableRoom availableRoom) {
        List<Pair<String, String>> listOfPair = Arrays.asList(
                Pair.create("Kelas 1", availableRoom.getJumlahKamarKelas1()),
                Pair.create("Kelas 2", availableRoom.getJumlahKamarKelas2()),
                Pair.create("Kelas 3", availableRoom.getJumlahKamarKelas3()),
                Pair.create("Dokter Siaga", availableRoom.getJumlahDokterSiaga())
        );

        int currentCursor = 0;

        for (int i = 0; i < containerRoom.getChildCount(); i++) {
            View view = containerRoom.getChildAt(i);
            if (view instanceof LinearLayout) {

                final Pair<String, String> stringPair = listOfPair.get(currentCursor);

                LinearLayout layout = (LinearLayout) view;
                ((TextView) layout.getChildAt(0)).setText(stringPair.first);
                ((TextView) layout.getChildAt(1)).setText(stringPair.second == null
                        ? "Data tidak tersedia"
                        : stringPair.second);

                currentCursor++;
            }
        }
    }

    @Override
    public boolean isForType(MapMarker mapMarker) {
        if (mapMarker instanceof Poi) {
            Poi poi = (Poi) mapMarker;
            return !poi.getKategoriId().equalsIgnoreCase(PoiCategories.ATM_BERAS);
        }
        return false;
    }

    @OnClick(R.id.btnClear)
    public void onMarkerCloseClick() {
        hideOverlay();
    }

    public interface PoiOverlayListener {
        void onDirectionClick(Poi poi);
    }
}
