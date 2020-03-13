package com.incendiary.ambulanceapp.data.model.detail;

import com.google.gson.annotations.SerializedName;

public class AmbulanceDetail extends BookingDetail{

    /**
     * unit_id : 1
     * no_plat : T 1234 AB
     * nama_driver : abc
     * no_telp : -
     */

    @SerializedName("unit_id") private String unitId;
    @SerializedName("no_plat") private String noPlat;
    @SerializedName("nama_driver") private String namaDriver;
    @SerializedName("no_telp") private String noTelp;

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getNamaDriver() {
        return namaDriver;
    }

    public void setNamaDriver(String namaDriver) {
        this.namaDriver = namaDriver;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
