package com.incendiary.ambulanceapp.data.model.poi.hospital;

import com.google.gson.annotations.SerializedName;

public class AvailableRoom {

    /**
     * lokasi_id : 24
     * jumlah_dokter_siaga : 9
     * jumlah_kamar_kelas1 : 11
     * jumlah_kamar_kelas2 : 22
     * jumlah_kamar_kelas3 : 33
     */

    @SerializedName("lokasi_id") private String lokasiId;
    @SerializedName("jumlah_dokter_siaga") private String jumlahDokterSiaga;
    @SerializedName("jumlah_kamar_kelas1") private String jumlahKamarKelas1;
    @SerializedName("jumlah_kamar_kelas2") private String jumlahKamarKelas2;
    @SerializedName("jumlah_kamar_kelas3") private String jumlahKamarKelas3;

    public String getLokasiId() {
        return lokasiId;
    }

    public void setLokasiId(String lokasiId) {
        this.lokasiId = lokasiId;
    }

    public String getJumlahDokterSiaga() {
        return jumlahDokterSiaga;
    }

    public void setJumlahDokterSiaga(String jumlahDokterSiaga) {
        this.jumlahDokterSiaga = jumlahDokterSiaga;
    }

    public String getJumlahKamarKelas1() {
        return jumlahKamarKelas1;
    }

    public void setJumlahKamarKelas1(String jumlahKamarKelas1) {
        this.jumlahKamarKelas1 = jumlahKamarKelas1;
    }

    public String getJumlahKamarKelas2() {
        return jumlahKamarKelas2;
    }

    public void setJumlahKamarKelas2(String jumlahKamarKelas2) {
        this.jumlahKamarKelas2 = jumlahKamarKelas2;
    }

    public String getJumlahKamarKelas3() {
        return jumlahKamarKelas3;
    }

    public void setJumlahKamarKelas3(String jumlahKamarKelas3) {
        this.jumlahKamarKelas3 = jumlahKamarKelas3;
    }
}
