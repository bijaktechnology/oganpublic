package com.incendiary.ambulanceapp.data.model.poi;

import com.google.gson.annotations.SerializedName;

public class PoiCategory {

    /**
     * kategori_id : 1
     * nama_kategori : Rumah Sakit
     */

    @SerializedName("kategori_id") private String kategoriId;
    @SerializedName("nama_kategori") private String namaKategori;

    public String getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(String kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    @Override
    public String toString() {
        return getNamaKategori();
    }
}
