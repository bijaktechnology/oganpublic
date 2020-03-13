package com.incendiary.ambulanceapp.features.booking;

import android.support.annotation.StringRes;

import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;

public class BookingUtils {

    @StringRes
    public static int getCompleteCaptionText(int mapType) {
        switch (mapType) {
            default:
                return R.string.complete_caption;
            case MarkerType.DOCTOR:
                return R.string.complete_caption_doctor;
            case MarkerType.BIDAN:
                return R.string.complete_caption_bidan;
        }
    }

    @StringRes
    public static int getCallButtonText(int mapType) {
        switch (mapType) {
            default:
                return R.string.call_ambulance;
            case MarkerType.DOCTOR:
                return R.string.call_doctor;
            case MarkerType.BIDAN:
                return R.string.call_bidan;
        }
    }

    @StringRes
    public static int getButtonText(int mapType) {
        switch (mapType) {
            default:
                return R.string.call_ambulance;
            case MarkerType.DOCTOR:
                return R.string.call_doctor;
            case MarkerType.BIDAN:
                return R.string.call_bidan;
        }
    }

    @StringRes
    public static int getTitle(int mapType) {
        switch (mapType) {
            default:
                return R.string.menu_semar;
            case MarkerType.DOCTOR:
                return R.string.menu_dokter;
            case MarkerType.BIDAN:
                return R.string.menu_bidan;
            case MarkerType.POI:
                return R.string.menu_lokasi;
        }
    }
}
