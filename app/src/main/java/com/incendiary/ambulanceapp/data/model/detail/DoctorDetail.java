package com.incendiary.ambulanceapp.data.model.detail;

import com.google.gson.annotations.SerializedName;

public class DoctorDetail extends BookingDetail{
    /**
     * dokter_id : 1
     * no_plat : -
     * nama_dokter : dr. Anton
     * no_telp : -
     */

    @SerializedName("dokter_id") private String dokterId;
    @SerializedName("no_plat") private String noPlat;
    @SerializedName("nama_dokter") private String namaDokter;
    @SerializedName("no_telp") private String noTelp;

    public String getDokterId() {
        return dokterId;
    }

    public void setDokterId(String dokterId) {
        this.dokterId = dokterId;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
