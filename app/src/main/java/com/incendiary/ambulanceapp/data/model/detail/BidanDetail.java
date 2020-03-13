package com.incendiary.ambulanceapp.data.model.detail;

import com.google.gson.annotations.SerializedName;

public class BidanDetail extends BookingDetail{

    /**
     * bidan_id : 1
     * no_plat : -
     * nama_bidan : Sulistyani
     * no_telp : -
     */

    @SerializedName("bidan_id") private String bidanId;
    @SerializedName("no_plat") private String noPlat;
    @SerializedName("nama_bidan") private String namaBidan;
    @SerializedName("no_telp") private String noTelp;

    public String getBidanId() {
        return bidanId;
    }

    public void setBidanId(String bidanId) {
        this.bidanId = bidanId;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public void setNoPlat(String noPlat) {
        this.noPlat = noPlat;
    }

    public String getNamaBidan() {
        return namaBidan;
    }

    public void setNamaBidan(String namaBidan) {
        this.namaBidan = namaBidan;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
}
