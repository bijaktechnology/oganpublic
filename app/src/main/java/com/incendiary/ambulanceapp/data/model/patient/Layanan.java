package com.incendiary.ambulanceapp.data.model.patient;

import com.google.gson.annotations.SerializedName;

public class Layanan {

    /**
     * layanan_id : 1
     * nama_layanan : BPJS
     */

    @SerializedName("layanan_id") private String layananId;
    @SerializedName("nama_layanan") private String namaLayanan;

    public String getLayananId() {
        return layananId;
    }

    public void setLayananId(String layananId) {
        this.layananId = layananId;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    @Override
    public String toString() {
        return getNamaLayanan();
    }
}
