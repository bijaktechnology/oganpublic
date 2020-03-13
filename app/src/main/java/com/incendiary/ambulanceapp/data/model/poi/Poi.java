package com.incendiary.ambulanceapp.data.model.poi;

import com.google.gson.annotations.SerializedName;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;

public class Poi implements MapMarker {
    /**
     * lokasi_id : 1
     * kategori_id : 3
     * nama_kategori : Pos Polisi
     * nama_lokasi : POS POLISI CIGANEA
     * lat : -6.56611
     * lng : 107.432
     * alamat : Jalan Pemuda No.43, RT.3/RW.2, Mekargalih, Jatiluhur, Kabupaten Purwakarta, Jawa Barat 41152
     * deskripsi :
     */

    @SerializedName("lokasi_id") private String lokasiId;
    @SerializedName("kategori_id") private String kategoriId;
    @SerializedName("nama_kategori") private String namaKategori;
    @SerializedName("nama_lokasi") private String namaLokasi;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("alamat") private String alamat;
    @SerializedName("deskripsi") private String deskripsi;

    public String getLokasiId() {
        return lokasiId;
    }

    public void setLokasiId(String lokasiId) {
        this.lokasiId = lokasiId;
    }

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

    public String getNamaLokasi() {
        return namaLokasi;
    }

    public void setNamaLokasi(String namaLokasi) {
        this.namaLokasi = namaLokasi;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    /* --------------------------------------------------- */
    /* > Map Marker */
    /* --------------------------------------------------- */

    @Override
    public int getMarkerType() {
        return MarkerType.POI;
    }

    @Override
    public String getLat() {
        return lat;
    }

    @Override
    public String getLang() {
        return lng;
    }

    @Override
    public String getTitle() {
        return namaLokasi;
    }

    @Override
    public int getIcon() {
        if (kategoriId.equals(PoiCategories.POLICE)) {
            return R.drawable.mark_police;
        }
        if (kategoriId.equals(PoiCategories.ATM_BERAS)) {
            return R.drawable.mark_atm_beras;
        }
        if (kategoriId.equals(PoiCategories.PUSKESMAS)) {
            return R.drawable.mark_puskes;
        }
        return R.drawable.mark_rs;
    }
}
