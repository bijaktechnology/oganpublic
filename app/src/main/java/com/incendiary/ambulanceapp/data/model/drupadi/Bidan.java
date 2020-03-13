package com.incendiary.ambulanceapp.data.model.drupadi;

import com.google.gson.annotations.SerializedName;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.map.MapMarker;
import com.incendiary.ambulanceapp.data.model.map.MarkerType;

public class Bidan extends MedPerson implements MapMarker {

    /**
     * bidan_id : 1
     * nama_bidan : Sulis
     * alamat : PURWAKARTA JAWA BARAT
     * lat : -6.43443
     * lng : 107.479
     * base_location : KLINIK HASANAH
     * status : A
     */

    @SerializedName("bidan_id") private String bidanId;
    @SerializedName("nama_bidan") private String namaBidan;
    @SerializedName("alamat") private String alamat;
    @SerializedName("lat") private String lat;
    @SerializedName("lng") private String lng;
    @SerializedName("base_location") private String baseLocation;
    @SerializedName("status") private String status;

    @Override
    public int getMarkerType() {
        return MarkerType.BIDAN;
    }

    @Override
    public int getIcon() {
        return R.drawable.ic_bidan;
    }

    public String getBidanId() {
        return bidanId;
    }

    public void setBidanId(String bidanId) {
        this.bidanId = bidanId;
    }

    public String getNamaBidan() {
        return namaBidan;
    }

    public void setNamaBidan(String namaBidan) {
        this.namaBidan = namaBidan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLat() {
        return lat;
    }

    @Override
    public String getLang() {
        return getLng();
    }

    @Override
    public String getTitle() {
        return getNamaBidan();
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

    public String getBaseLocation() {
        return baseLocation;
    }

    public void setBaseLocation(String baseLocation) {
        this.baseLocation = baseLocation;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
