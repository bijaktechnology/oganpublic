package com.incendiary.ambulanceapp.data.model.nik;

import com.google.gson.annotations.SerializedName;

public class Nik {

    /**
     * nik : 3214126704720001
     * no_kk : 3214123005070570
     * nama_lengkap : YASNON APRINENTY, S.PD
     */

    @SerializedName("nik") private String nik;
    @SerializedName("no_kk") private String noKk;
    @SerializedName("nama_lengkap") private String namaLengkap;

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNoKk() {
        return noKk;
    }

    public void setNoKk(String noKk) {
        this.noKk = noKk;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
