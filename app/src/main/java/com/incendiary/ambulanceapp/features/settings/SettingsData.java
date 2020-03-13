package com.incendiary.ambulanceapp.features.settings;

import com.incendiary.ambulanceapp.R;

import java.util.Arrays;
import java.util.List;

import static com.incendiary.androidcommon.android.ContextProvider.getString;

public class SettingsData {

    public static List<String> getSettings() {
        return Arrays.asList(
                getString(R.string.profile),
                "Notifikasi",
                "Bookmark",
                "Riwayat Laporan",
                "Riwayat Panggilan",
                "Komentar",
                "Rekam Medis",
                "Pengaturan"
        );
    }

    public static List<String> getOtherSettings() {
        return Arrays.asList(
                "Notifikasi",
                "Tutorial",
                "About",
                "Disclaimer",
                "Cek Update"
        );
    }
}
